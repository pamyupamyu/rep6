import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import BackwardChain.RuleBaseSystem;

/**
 * 基本GUIフレーム
 */
public class Sample extends JFrame implements ActionListener{
	JTextArea qtext;
	JTextArea atext;
	JPanel querySystem;
	JPanel query;
	JPanel ans;
	JLabel qlabel;
	JLabel alabel;
	JButton button1;
	JButton button2;
	JButton button3;
	RuleBaseSystem rbs;
	File file;
	ArrayList<String> wm;
	boolean fileflag = false;
	boolean fileflagWm = false;

	public static void main(String arg[]){
		JFrame frame = new Sample("Sample System");
		frame.setVisible(true);
	}

	Sample(String title){
		rbs= new RuleBaseSystem();
		rbs.GUISetUp();
		wm = new ArrayList<String>();
		
		//ウィンドウの生成
		setTitle(title);
		setBounds(100,100,260,210);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		//メニューバーの作成
		JMenuBar menubar = new JMenuBar();
		menubar.setBorderPainted(false);

		JMenu menu1 = new JMenu("File");
		menubar.add(menu1);

		JMenuItem read = new JMenuItem("file読み込み");
		JMenuItem readWm = new JMenuItem("Wmfile読み込み");
		JMenuItem edit = new JMenuItem("編集");

		menu1.add(read);
		read.addActionListener(this);
		menu1.add(readWm);
		readWm.addActionListener(this);
		menu1.add(edit);
		edit.addActionListener(this);

		setJMenuBar(menubar);


		//query用のpanel作成
		query = new JPanel();
		query.setBackground(Color.white);
		query.setPreferredSize(new Dimension(120,107));		
		qlabel = new JLabel("query");
		qtext = new JTextArea(5,10);
		qtext.setText("推論に使用するファイル\nを読み込んでください");
		JScrollPane qscroll = new JScrollPane(qtext);
		query.add(qlabel);
		query.add(qscroll);

		//answer用のpanel作成
		ans = new JPanel();
		ans.setBackground(Color.white);
		ans.setPreferredSize(new Dimension(120,107));	
		alabel = new JLabel("answer");
		atext = new JTextArea(5,10);
		JScrollPane ascroll = new JScrollPane(atext);
		ans.add(alabel);
		ans.add(ascroll);

		//button作成
		JPanel bpanel = new JPanel();
		bpanel.setBackground(Color.white);
		bpanel.setPreferredSize(new Dimension(245,35));
		button1 = new JButton("実行");
		button1.addActionListener(this);
		button2 = new JButton("推論過程");
		button2.addActionListener(this);
		button3 = new JButton("未実装");
		button3.addActionListener(this);
		bpanel.add(button1);
		bpanel.add(button2);
		bpanel.add(button3);

		//応答システム用パネルに配置
		add(query,BorderLayout.WEST);
		add(ans,BorderLayout.EAST);
		add(bpanel,BorderLayout.SOUTH);
	}
	

	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand()=="実行"){
			try{
				atext.setText("ここに答えがでる\nまだ未実装");
				StringBuilder sb = new StringBuilder(qtext.getText());
				for(int i=0;i<sb.length();++i){
					if(sb.substring(i,i+1).equals("\n")){
						sb.setCharAt(i,',');
					}
				}
				String str = sb.toString();
				if(fileflag && fileflagWm){ 
					rbs.exe(str,file,wm);
				}else{
					qtext.setText("ファイル読み込みを\nしてください");
				}
			}catch(IndexOutOfBoundsException ie){
				System.out.println("ERROR");
			}
		}else if(e.getActionCommand()=="推論過程"){
			Chain chain = new Chain("Chain result");
			chain.setText("推移が表示されます\nまだ未実装");
			chain.setVisible(true);
		}else if(e.getActionCommand()=="file読み込み"){
			JFileChooser fc = new JFileChooser();
			int selected = fc.showOpenDialog(this);
			if(selected == JFileChooser.APPROVE_OPTION){
				file = fc.getSelectedFile();
				if(checkBeforeReadfile(file)){
					fileflag = true;
				}else{
					System.out.println("ERROR");
				}
			}
		}else if(e.getActionCommand()=="Wmfile読み込み"){
			JFileChooser fc = new JFileChooser();
			int selected = fc.showOpenDialog(this);
			if(selected == JFileChooser.APPROVE_OPTION){
				File filewm = fc.getSelectedFile();
				if(checkBeforeReadfile(filewm)){
					wm = rbs.readfileWm(filewm);
					fileflagWm = true;
				}else{
					System.out.println("ERROR");
				}
			}
		}else if(e.getActionCommand()=="編集"){
			System.out.println("edit");
		}
	}

	private static boolean checkBeforeReadfile(File file){
		if (file.exists()){
			if (file.isFile() && file.canRead()){
				return true;
			}
		}
		return false;
	}

}

/**
 * 推移表示GUIフレーム
 */
class Chain extends JFrame{
	JPanel panel;
	JTextArea area1;
	JScrollPane scroll1;

	Chain(String title){
		//ウィンドウの生成
		setTitle(title);
		setBounds(200,200,230,180);
		setResizable(false);
		setLayout(new FlowLayout());

		panel = new JPanel();
		area1 = new JTextArea(8,18);
		scroll1 = new JScrollPane(area1);

		panel.add(scroll1);
		add(panel, BorderLayout.CENTER);
	}

	void setText(String text){
		area1.setText(text);
	}
}
