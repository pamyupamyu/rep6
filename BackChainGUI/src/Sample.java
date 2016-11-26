import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import BackwardChain.RuleBaseSystem;

/**
 * 基本GUIフレーム
 */
public class Sample extends JFrame implements ActionListener{
	JTextArea qtext;
	JTextArea atext;
	JPanel query;
	JPanel ans;
	JLabel qlabel;
	JLabel alabel;
	JButton button1;
	JButton button2;
	JButton button3;
	RuleBaseSystem rbs = new RuleBaseSystem();

	public static void main(String arg[]){
		JFrame frame = new Sample("Sample System");
		frame.setVisible(true);
	}

	Sample(String title){
		//ウィンドウの生成
		setTitle(title);
		setBounds(100,100,260,185);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new FlowLayout());

		//queryのテキスト欄
		query = new JPanel();
		query.setBackground(Color.white);
		query.setPreferredSize(new Dimension(120,100));		
		qlabel = new JLabel("query");
		qtext = new JTextArea(4,10);
		JScrollPane qscroll = new JScrollPane(qtext);

		query.add(qlabel);
		query.add(qscroll);

		//answerのテキスト欄
		ans = new JPanel();
		ans.setBackground(Color.white);
		ans.setPreferredSize(new Dimension(120,100));	
		alabel = new JLabel("answer");
		atext = new JTextArea(4,10);
		JScrollPane ascroll = new JScrollPane(atext);
		//atext.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		//atext.setBorder(new EtchedBorder(EtchedBorder.RAISED));
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

		//パネルの配置
		add(query,BorderLayout.WEST);
		add(ans,BorderLayout.EAST);
		add(bpanel,BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource()==button1){
			try{
				atext.setText("ここに答えがでる\nまだ未実装");
				StringBuilder sb = new StringBuilder(qtext.getText());
				for(int i=0;i<sb.length();++i){
					if(sb.substring(i,i+1).equals("\n")){
						sb.setCharAt(i,',');
					}
				}
				String[] str = {sb.toString()};
				rbs.main(str);
			}catch(IndexOutOfBoundsException ie){
				System.out.println("ERROR");
			}
		}else if(e.getSource()==button2){
			Chain chain = new Chain("Chain result");
			chain.setText("推移が表示されます\nまだ未実装");
			chain.setVisible(true);
		}
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

