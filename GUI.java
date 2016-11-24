package GUI_p;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Label;




class PrefFrame extends JFrame implements ActionListener{

    String sample1 = "サンプル1";
    String sample2 = "サンプル2";
    String sample3 = "";
    JPanel p = new JPanel();
    
    Label text;
    String show="aaa";
    JLabel label1 = new JLabel(sample1);
    JLabel label2 = new JLabel(sample2);
    JLabel label3 = new JLabel(sample3);
    Button b1 = new Button("button1");
    Button b2 = new Button("button2");
    Button b3 = new Button("未実装");
    
    public PrefFrame(String title) {
      //フレームのタイトル
      setTitle(title);
      System.out.println("set");
      //追加
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      p.setLayout(null);
      
      label1.setForeground(Color.BLUE);
      label1.setBackground(Color.WHITE);
      label1.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
      label1.setOpaque(true);
      label1.setBounds(0, 0, 250, 50);
      
      label2.setFont(new Font("Century", Font.ITALIC, 24));
      label2.setBounds(0, 40, 250, 100);

      label3.setFont(new Font("Century", Font.ITALIC, 24));
      label3.setBounds(0, 90, 250, 100);
      
      b1.addActionListener(this);
      b1.setBounds(0, 190, 70, 40);
      b2.addActionListener(this);
      b2.setBounds(80, 190, 70, 40);
      b3.setBounds(160, 190, 70, 40);
      
      p.add(label1);
      p.add(label2);
      p.add(label3);
      p.add(b1);
      p.add(b2);
      p.add(b3);
      
      Container contentPane = getContentPane();
      contentPane.add(p, BorderLayout.CENTER);
      

     
      
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
           System.out.println("Button1がクリックされました");
        sample2 = "文字が"; 
        label2.setText(sample2);
        sample3 = "変化します";
        label3.setText(sample3);
        }else if (ae.getSource() == b2) {
            System.out.println("Button2がクリックされました");
            sample2 = "サンプル2"; 
            label2.setText(sample2);
            sample3 = "戻ります";
            label3.setText(sample3);
        }
     }
    
 
}

public class GUI{

  public static void main(String args[]) {
    PrefFrame frm = new PrefFrame("ルールベースシステム");
    frm.setLocation(300, 200); //表示位置
    frm.setSize(250, 260); //表示サイズ
    //frm.setBackground(new Color(0,0,0)); //機能していない
    frm.setVisible(true);
    //////////////////////////////////////////追加////////////////////////

    
  }

}