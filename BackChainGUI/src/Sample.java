import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
	Wmadd wmadd;
	Chain chain;
	boolean fileflag = false;
	boolean fileflagWm = false;

	public static void main(String arg[]){
		JFrame frame = new Sample("Sample System");
		frame.setVisible(true);
	}

	Sample(String title){
		rbs= new RuleBaseSystem();
		rbs.GUISetUp();
		wmadd = new Wmadd("wm追加");
		chain = new Chain("Chain result");

		//ウィンドウの生成
		setTitle(title);
		setBounds(100,100,260,217);
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
		//JMenuItem edit = new JMenuItem("rule編集");

		menu1.add(read);
		read.addActionListener(this);
		menu1.add(readWm);
		readWm.addActionListener(this);
		//menu1.add(edit);
		//edit.addActionListener(this);

		setJMenuBar(menubar);

		//query用のpanel作成
		query = new JPanel();
		query.setBackground(Color.white);
		query.setPreferredSize(new Dimension(120,115));		
		qlabel = new JLabel("query");
		qtext = new JTextArea(5,10);
		qtext.setText("推論に使用する\nファイルを\n読み込んでください");
		JScrollPane qscroll = new JScrollPane(qtext);
		query.add(qlabel);
		query.add(qscroll);

		//answer用のpanel作成
		ans = new JPanel();
		ans.setBackground(Color.white);
		ans.setPreferredSize(new Dimension(120,115));	
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
		button3 = new JButton("wm編集");
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
					rbs.exe(str,file,wmadd.getWm());
					atext.setText(rbs.getAnswer());
					chain.setText(rbs.getChainPath());
				}else{
					qtext.setText("ファイル読み込みを\nしてください");
				}
			}catch(IndexOutOfBoundsException ie){
				System.out.println("ERROR");
			}
		}else if(e.getActionCommand()=="推論過程"){
			chain.setVisible(true);
		}else if(e.getActionCommand()=="wm編集"){
			wmadd.showWm();
			wmadd.setVisible(true);
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
					ArrayList<String> list = rbs.readfileWm(filewm);
					wmadd.setText(list);
					fileflagWm = true;
				}else{
					System.out.println("ERROR");
				}
			}
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
 * 推移表示ウィンドウ
 */
class Chain extends JFrame{
	JPanel panel;
	JTextArea area1;
	JScrollPane scroll1;

	Chain(String title){
		//ウィンドウの生成
		setTitle(title);
		setBounds(200,200,230,180);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

		panel = new JPanel();
		panel.setLayout(layout);
		area1 = new JTextArea(8,18);
		scroll1 = new JScrollPane(area1);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1.0d;
		gbc.weighty = 1.0d;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		layout.setConstraints(scroll1, gbc);

		panel.add(scroll1);
		add(panel, BorderLayout.CENTER);
	}

	void setText(String text){
		area1.setText(text);
	}
}

/**
 * wm編集用ウィンドウ
 */
class Wmadd extends JFrame implements ActionListener{
	JPanel panel;
	JTextArea area1;
	JScrollPane scroll1;
	ArrayList<String> wm;
	JButton addtion;

	Wmadd(String title){
		//ウィンドウの生成
		setTitle(title);
		setBounds(200,200,230,190);
		//setResizable(false);

		wm = new ArrayList<String>();

		GridBagLayout layout = new GridBagLayout();
		panel = new JPanel();
		panel.setLayout(layout);

		GridBagConstraints gbc = new GridBagConstraints();
		area1 = new JTextArea(7,18);
		scroll1 = new JScrollPane(area1);
		addtion = new JButton("完了");
		addtion.addActionListener(this);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1.0d;
		gbc.weighty = 1.0d;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(3, 3, 3, 3);
		layout.setConstraints(scroll1, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0d;
		gbc.weighty = 0d;
		//gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 3, 3, 3);
		layout.setConstraints(addtion, gbc);

		panel.add(scroll1);
		panel.add(addtion);
		add(panel, BorderLayout.CENTER);
	}

	void showWm(){
		String str="";
		for(int i=0;i<wm.size();++i){
			str = str + wm.get(i)+"\n";
		}
		area1.setText(str);
	}

	void setText(ArrayList<String> text){
		wm = text;
	}

	ArrayList<String> getWm(){
		return wm;
	}

	public void actionPerformed(ActionEvent e){
		String[] str = area1.getText().split("\n",0);
		wm = new ArrayList(Arrays.asList(str));
		setVisible(false);
	}

}

