import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

///RuleBaseSystem
///////////////////////前向き推論/////////////////////
//////////////////////GUI作成用///////////////////////
/////////ここからmain文までshinkaiが変更しました//////

//過程用フレームの作成
class LaterFrame extends JFrame{

	static String sample5 ="";
    JPanel p = new JPanel();
    JTextArea text1 = new JTextArea(sample5);
    JScrollPane s_text1 = new JScrollPane(text1);
    JLabel label5 = new JLabel(sample5);
    public LaterFrame(String title) {
      //フレームのタイトル
      setTitle(title);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      p.setLayout(null);

      label5.setForeground(Color.BLACK);
      label5.setBackground(Color.WHITE);
      label5.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
      label5.setOpaque(true);
      label5.setBounds(0, 0, 500,700 );
      s_text1.setBounds(0,0,280,480);


      //p.add(label5);
      label5.setText(sample5);
      p.add(s_text1);
      label5.setText(sample5);
      
      Container contentPane = getContentPane();
      contentPane.add(p, BorderLayout.CENTER);
    }
}
//実行用フレーム
class DoFrame extends JFrame implements ActionListener{

	Button b1 = new Button("決定");
	JLabel label1 = new JLabel("データ名を入力");
    static String enter1 = "";
    static String enter2 = "";
    static String[] enter_list2 = new String[10];
    static int enter_list_n2;
    static String enter3 = "";
    static String[] enter_list3 = new String[10];
    static int enter_list_n3;
    static String sample6 = "";
    JLabel label2 = new JLabel(sample6);
    JTextField text1 = new JTextField("CarShop.data");
    JTextArea text2 = new JTextArea("追加したいアサーションを以下から入力\n");
    JTextArea text3 = new JTextArea("追加したい質問文を以下から入力\n");
    JScrollPane s_text2 = new JScrollPane(text2);
    JScrollPane s_text3 = new JScrollPane(text3);

    JPanel p = new JPanel();
    public DoFrame(String title) {
      //フレームのタイトル
      setTitle(title);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      p.setLayout(null);
      label1.setForeground(Color.BLACK);
      label1.setBackground(Color.WHITE);
      label1.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
      label1.setOpaque(true);
      label1.setBounds(0, 0, 200, 40);
      label2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 7));
      label2.setBackground(Color.WHITE);
      label2.setBounds(0, 300, 200, 40);
      text1.setBounds(0,40,200,30);
      s_text2.setBounds(0,80,180,80);
      s_text3.setBounds(0,170,180,80);
      b1.addActionListener(this);
      b1.setBounds(73, 260 , 40, 30);

      p.add(label1);
      p.add(label2);
      p.add(b1);
      p.add(text1);
      p.add(s_text2);
      p.add(s_text3);


      Container contentPane = getContentPane();
      contentPane.add(p, BorderLayout.CENTER);
    }

      public void actionPerformed(ActionEvent ae) {
        	enter1 =text1.getText();
        	enter2 =text2.getText();
   		 	enter_list2 = enter2.split("\n",0);
   		 	enter_list_n2 = enter_list2.length;
        	enter3 =text3.getText();
   		 	enter_list3 = enter3.split("\n",0);
   		 	enter_list_n3 = enter_list3.length;
            if (ae.getSource() == b1) {
              	RuleBaseSystem.delete_text("test.txt");
            	RuleBaseSystem.do_main();
            	label2.setText(sample6);
            }
         }


}
//ルール削除用フレーム
class DeleteFrame extends JFrame implements ActionListener{

	String explain1 ="削除するルール名を入力";
	Button b1 = new Button("決定");
	JLabel label6 = new JLabel(explain1);
    static String delete_enter = "";
    JTextField text = new JTextField("\"CarRule\"");

    JPanel p = new JPanel();
    public DeleteFrame(String title) {
      //フレームのタイトル
      setTitle(title);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      p.setLayout(null);
      label6.setForeground(Color.RED);
      label6.setBackground(Color.WHITE);
      label6.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
      label6.setOpaque(true);
      label6.setBounds(0, 0, 200, 40);
      text.setBounds(0,40,200,30);
      b1.addActionListener(this);
      b1.setBounds(73, 80 , 40, 30);

      p.add(label6);
      p.add(b1);
      p.add(text);

      Container contentPane = getContentPane();
      contentPane.add(p, BorderLayout.CENTER);
    }

      public void actionPerformed(ActionEvent ae) {
        	delete_enter =text.getText();
            if (ae.getSource() == b1) {
                String data[] = RuleBaseSystem.read_text("CarShop.data");
                RuleBaseSystem.delete_rule(data);
            }
         }


}
//ルール追加用フレーム
class AddFrame extends JFrame implements ActionListener{

	Button b1 = new Button("決定");
	JLabel label0 = new JLabel("ルールの追加");
	JLabel label1 = new JLabel("ルール名");
	JLabel label2 = new JLabel("if 文");
	JLabel label3 = new JLabel("then 文");
    static String add_enter1 = "";
    static String add_enter2 = "";
    static String[] add_list = new String[10];
    static int add_list_n = 0;
    static String add_enter3 = "";
    JTextField text1 = new JTextField("\"CarRule\"");
    JTextArea text2 = new JTextArea("\"ここに入力\"");
    JScrollPane s_text2 = new JScrollPane(text2);
    JTextField text3 = new JTextField("\"ここに入力\"");

    JPanel p = new JPanel();
    public AddFrame(String title) {
      //フレームのタイトル
      setTitle(title);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      p.setLayout(null);
      label0.setForeground(Color.BLUE);
      label0.setBackground(Color.WHITE);
      label0.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
      label0.setOpaque(true);
      label0.setBounds(0, 0, 200, 40);
      text1.setBounds(0,40,200,30);
      s_text2.setBounds(0,90,180,100);
      text3.setBounds(0,200,200,30);
      b1.addActionListener(this);
      b1.setBounds(73, 250 , 40, 30);

      p.add(label0);
      p.add(b1);
      p.add(text1);
      p.add(s_text2);
      p.add(text3);

      Container contentPane = getContentPane();
      contentPane.add(p, BorderLayout.CENTER);
    }

      public void actionPerformed(ActionEvent ae) {
        	add_enter1 =text1.getText();
        	add_enter2 =text2.getText();
   		 	add_list = add_enter2.split("\n",0);
   		 	add_list_n = add_list.length;
        	add_enter3 =text3.getText();
            if (ae.getSource() == b1) {
                String data[] = RuleBaseSystem.read_text("CarShop.data");
                RuleBaseSystem.add_rule(data);
            }
         }


}
//メインのフレームの作成
class PrefFrame extends JFrame implements ActionListener{

    static String sample1 = "here is Answer";
    static String sample2 = "here is Assersions";
    static String sample3 = "here is Rules";
    static String sample4 = "here is Mode";

    static String enter = "";
    static boolean cont = false;

    JPanel p = new JPanel();

    JLabel label1 = new JLabel(sample1);
    JLabel label4 = new JLabel(sample4);
    Button b1 = new Button("do");
    Button b2 = new Button("print");
    Button b3 = new Button("delete");
    Button b4 = new Button("add");

    JTextArea text2 = new JTextArea(sample2);
    JTextArea text3 = new JTextArea(sample3);
    JScrollPane s_text2 = new JScrollPane(text2);
    JScrollPane s_text3 = new JScrollPane(text3);

    public PrefFrame(String title) {
      //フレームのタイトル
      setTitle(title);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      p.setLayout(null);

      label1.setForeground(Color.BLUE);
      label1.setBackground(Color.WHITE);
      label1.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 26));
      label1.setOpaque(true);
      label1.setBounds(0, 0, 400, 50);

      label4.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
      label4.setBackground(Color.WHITE);
      label4.setBounds(0, 330, 400, 50);

      b1.addActionListener(this);
      b1.setBounds(0, 250, 70, 40);
      b2.addActionListener(this);
      b2.setBounds(80, 250, 70, 40);
      b3.addActionListener(this);
      b3.setBounds(160, 250, 70, 40);
      b4.addActionListener(this);
      b4.setBounds(240, 250, 70, 40);

      s_text2.setBounds(0,50,300,100);
      s_text3.setBounds(0,150,300,100);

      p.add(label1);
      p.add(label4);
      p.add(b1);
      p.add(b2);
      p.add(b3);
      p.add(b4);
      p.add(s_text2);
      p.add(s_text3);

      Container contentPane = getContentPane();
      contentPane.add(p, BorderLayout.CENTER);
    }

    //ボタンを押したときの実装
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
           	DoFrame frm1 = new DoFrame("実行");
            frm1.setLocation(500,100 ); //表示位置
            frm1.setSize(200, 400); //表示サイズ
            frm1.setVisible(true);
            sample4 ="実行モード";
            label4.setText(sample4);
        }else if (ae.getSource() == b2) {
        	sample4 ="表示モード";
            label4.setText(sample4);
        	RuleBaseSystem.printGUI();
        	label1.setText(sample1);
            text2.setText(sample2);
            text3.setText(sample3);
            RuleBaseSystem.printPath();
            LaterFrame frm2 = new LaterFrame("過程表示");
            frm2.setLocation(890, 0); //表示位置
            frm2.setSize(300, 500); //表示サイズ
            frm2.setVisible(true);
        }else if (ae.getSource() == b3) {
        	sample4 ="削除モード";
            label4.setText(sample4);
        	DeleteFrame frm3 = new DeleteFrame("ルールの削除");
            frm3.setLocation(200, 520); //表示位置
            frm3.setSize(200, 200); //表示サイズ
            frm3.setVisible(true);
        }else if (ae.getSource() == b4) {
        	sample4 ="追加モード";
            label4.setText(sample4);
        	AddFrame frm4 = new AddFrame("ルールの追加");
            frm4.setLocation(700, 100); //表示位置
            frm4.setSize(200, 400); //表示サイズ
            frm4.setVisible(true);
        }
     }
}
//////////////////////////////以下ルールベースシステム/////////////////////////////////////


public class RuleBaseSystem {

    static RuleBase rb;
//ファイルにSystem.out.print文を書き込む用
    public static void write_text(String args,String fn){
		 try{
		      File file = new File(fn);

		      if (checkBeforeWritefile(file)){
		        FileWriter filewriter = new FileWriter(file, true);

		        filewriter.write(args);
		        filewriter.write("\r\n");
		        filewriter.close();
		      }else{
		        System.out.println("ファイルに書き込めません");
		      }
		    }catch(IOException e){
		      System.out.println(e);
		    }
		  }
//リセットのためにファイルの中身を削除
    public static void delete_text(String fn){
   	 try{
   	      File file = new File(fn);

   	      if (checkBeforeWritefile(file)){
   	        FileWriter filewriter = new FileWriter(file);

   	        filewriter.close();
   	      }else{
   	        System.out.println("ファイルに書き込めません");
   	      }
   	    }catch(IOException e){
   	      System.out.println(e);
   	    }
   }
//////////////////////////////////////
    private static boolean checkBeforeWritefile(File file){
		    if (file.exists()){
		      if (file.isFile() && file.canWrite()){
		        return true;
		      }
		    }
		    return false;
		  }
    //テキストの行数を確認
	public static int  text_n(String fn)
	{
		// カウントした行数を格納する整数型の変数を定義し、0で初期化する。
        int lineCount = 0;
		// 例外処理の始まり
        try
        {
			// ファイルを読み込みモードでオープンする。ファイルが存在しなかったりする場合に FileNotFoundException がスローされる。
            FileReader fr = new FileReader(fn);
			// ファイルを読むための便利なクラス BufferedReader のオブジェクトを作る。
            BufferedReader br = new BufferedReader(fr);
			// 読み込んだ1行の文字列を格納するための変数を定義する。
            String line;
			// 1行目を読んでみる。もし、空のファイルなら、line には null がセットされる。
			line = br.readLine();
			// ファイルの最後まで来て null が返ってくるまで、処理を繰り返す。
            while( line != null )
            {// 1行読み込むに成功するたびに、行数のカウントを1増やす。
                lineCount++;
				// readLine メソッドを使ってもう1行読み込む。
                line = br.readLine();
            }
			// ストリームを閉じて、BufferedReader のリソースを開放する。
			// このとき、同時にFileReader のcloseも行われるので、fr.close() は必要ない。なので、ファイルもここで閉じられます。
            br.close();
        }
		catch( FileNotFoundException e )
		{// 15行目でエラーが発生するとここに来る。
            System.out.println(e);
		}
        catch( IOException e )
        {// 18、24、38行目でエラーが発生するとここに来る。
            System.out.println(e);
        }
		// カウントした行数を返す。
        return lineCount;
	}
    //配列の中身の数を確認
    public static int String_n(String data[]){
    	int n=1000;
    	boolean c =true;

    	for(int i=0;i<1000&&c;i++){
    		if(data[i]==null||data[i].length()==0){
    			n=i; c=false;
    		}
    	}
    	return n;
    }
//ファイルの文字を列ごとにString型配列へ変換
    public static String[] read_text(String fn){
    	int n = text_n(fn);
		String[] str2 = new String[n]; //現状拡張する幅がわからない
		try{
			  File file = new File(fn);
			  BufferedReader br = new BufferedReader(new FileReader(file));
			  int k =0;
			  String str;

			  while((str = br.readLine()) != null){
				  str2[k]=str;
				  k++;
			  }
			  br.close();
			}catch(FileNotFoundException e){
			  System.out.println(e);
			}catch(IOException e){
			  System.out.println(e);
			}
		return(str2);
	}
//最初の3文字とあうものをリストアップ
//Example matchList(data,abc)>>data配列の中でabcから始まる文をすべてListに格納する
    public static String[] matchList(String[] data,String three){
    	int n = text_n("test.txt");
		String[] str = new String[n]; //現状拡張する幅がわからない
		int j=0;
		for(int i=0;i<n;i++){
			String check = data[i].substring(0, 3);
			if(three.equals(check)){
				str[j]=data[i];// 文字比較時に用いる
				j++;
			}
		}//for
		return str;
    }
//答えを出す専用 Working Memoryの最後を出す
    public static String answer(String[] data,int n){
    	String ans = "";
    	int m = data[n-2].length();
    	boolean c =true;
    	int def = 0;

		for(int j=m;j>0&&c;j--){
			String check = data[n-2].substring(j-1, j);
			if(",".equals(check)){
				def = j;
				c=false;
			}
		}
		ans = data[n-2].substring(def+1, m-1);
		return ans;
    }
    //ADDやルール一覧をGUIに表示する
    public static void printGUI(){
        String archive[] = read_text("test.txt");
        int arc_n = text_n("test.txt");
        //現状ここがCarRuleでしか使えない
        //要変更ポイント///////////////////////////////////
        String ADDList[] = matchList(archive,"ADD");
        
     if(DoFrame.enter1.equals("CarShop.data")){
        String Ruleist[] = matchList(archive,"Car");
        int CRL_n = String_n(Ruleist);
        int ADDL_n = String_n(ADDList);
        PrefFrame.sample3 = "ルール一覧\n";
       for(int i=0;i<CRL_n;i++)
        PrefFrame.sample3 = PrefFrame.sample3 +Ruleist[i] +  "\n";
       PrefFrame.sample2 = "アサーション一覧\n";
       for(int i=0;i<ADDL_n;i++)
        PrefFrame.sample2 = PrefFrame.sample2 +ADDList[i] +  "\n";
       String a =answer(archive,arc_n);
       PrefFrame.sample1 = "A."+a;
     }else if(DoFrame.enter1.equals("sotuken.data")){
     	String Ruleist[] = matchList(archive,"Lab");
     	int CRL_n = String_n(Ruleist);
       int ADDL_n = String_n(ADDList);
       PrefFrame.sample3 = "ルール一覧\n";
       for(int i=0;i<CRL_n;i++)
       PrefFrame.sample3 = PrefFrame.sample3 +Ruleist[i] +  "\n";
       PrefFrame.sample2 = "アサーション一覧\n";
       for(int i=0;i<ADDL_n;i++)
        PrefFrame.sample2 = PrefFrame.sample2 +ADDList[i] +  "\n";
       String a =answer(archive,arc_n);
       PrefFrame.sample1 = "A."+a;
      }
    }
//過程をGUIに表示する
    public static void printPath(){
    String archive[] = read_text("test.txt");
    int arc_n = text_n("test.txt");
    boolean search =false;
    LaterFrame.sample5 ="";
    for(int i=0;i<arc_n;i++){
    	String check = archive[i].substring(0, 3);
    	if(check.equals("ADD")){
    	LaterFrame.sample5 =LaterFrame.sample5+archive[i]+"\n";
    	}else if(check.equals("Suc")){
    		LaterFrame.sample5 =LaterFrame.sample5+archive[i-1]+"\n";
    		LaterFrame.sample5 =LaterFrame.sample5+"上記のルールが適応された！！\n";
    	}else if(check.equals("Wor")){
    		LaterFrame.sample5 =LaterFrame.sample5+"探索が一巡した\n";
    	}else if(check.equals("No ")){
    	 LaterFrame.sample5 = LaterFrame.sample5+"探索終了\n";
    	}else if(check.equals("app")){
        	String before = archive[i-1].substring(0, 3);
        	if(before.equals("app")||before.equals("ADD")){}else{
        		LaterFrame.sample5 = LaterFrame.sample5+"探索開始\n";
        	}
    	}
    }
}
//もともとあったmain文と同じもの
    public static void do_main(){
        rb = new RuleBase();
        rb.forwardChain();
    }

//未実装 data内チェック
/*
    public static boolean dataCheck(String fn){
    	boolean c =true;
        String data[] = read_text(fn);
        int data_n = text_n(fn);
        for(int i=0;i<data_n;i++){

        }
    	return c;
    }
*/
    ////////////////////以下課題6.4用メソッド/////////////////////////
//ルールの削除
    public static void delete_rule(String[] data){

        System.out.println("削除するルールを決定してください");
        System.out.println("Example  \"CarRule15\" ");
       // Scanner scan = new Scanner(System.in);
       // String rule = scan.next();
        PrefFrame.sample4 = DeleteFrame.delete_enter;
        System.out.println("以下のルールを検索します:"+ PrefFrame.sample4);

       //int data_n = text_n(DoFrame.enter1);
    	int data_n = text_n("CarShop.data");
		int check_s=0;
		int check_n=0;
		boolean check=true;
		for(int i=0;i<data_n&&check;i++){
			if(data[i].endsWith(DeleteFrame.delete_enter)){
				System.out.println("ルールを発見しました");
				check_s=i;
				for(int j=check_s;j<data_n&&check;j++){
					if(data[j].startsWith("then")){
					check_n=j-check_s+2;
					check=false;
					}
				}
			}
		}//for
		String[] temp = new String[data_n-check_n+1];
		for(int i=0;i<data_n-check_n;i++){
			temp[i]="";
		}
		for(int i=0,i2=0;i<data_n-check_n;i++,i2++){
			if(i==check_s){
				i2 +=check_n;
			}
			temp[i]=data[i2];
		}
		int temp_n = data_n-check_n;

	    //delete_text(DoFrame.enter1);
	    delete_text("CarShop.data");
	    for (int i=0;i<temp_n;i++){
	        write_text(temp[i],"CarShop.data"); //ファイル書き込み
	        //write_text(temp[i],DoFrame.enter1); //ファイル書き込み
	    }
    }
//ルールの追加
    public static void add_rule(String[] data){
        System.out.println("加えるルール名を決定してください");
        System.out.println("Example  \"CarRule16\" ");
        String rule1 = AddFrame.add_enter1;
        System.out.println("以下のルールを追加します: "+ AddFrame.add_enter1);

       //int data_n = text_n(DoFrame.enter1);
       int data_n = text_n("CarShop.data");
		boolean check=true;
		for(int i=0;i<data_n&&check;i++){
			if(data[i].endsWith(rule1)){
				System.out.println("同一名のルールが存在します");
				System.out.println("追加することはできません");
				check=false;
			}
		}//for

		if(check){
			////////////////////if内の追加/////////////////////////
		       System.out.println("ifに続く文字を入力してください");
				String[] rule2 = new String[10]; //ifの制約は10個までにする
		        for(int i=0;i<10;i++){
		        	rule2[i]="";
		        }
		       int if_n=AddFrame.add_list_n;
		       boolean cont =true;
		       for(int i=0;i<if_n&&cont;i++){
				        rule2[i] = AddFrame.add_list[i];
				        System.out.println("以下の制約を追加します: "+ rule2[i]);
		       }//for
		  ////////////////////////then内の追加//////////////////////////////
		       System.out.println("thenに続く文字を入力してください");
		       String rule3 = AddFrame.add_enter3;
		       System.out.println("以下の結果を追加します: "+ rule3);

		       String[] temp = new String[data_n+3+if_n];
				for(int i=0;i<data_n;i++){
					temp[i]=data[i];
				}
		       for(int i=data_n;i<data_n+2+if_n;i++){
		    	   temp[i]="";
		       }
		       temp[data_n+1]="rule 	"+rule1;
		       temp[data_n+2]="if 	"+rule2[0];
		       for(int i=1;i<if_n;i++){
			       temp[data_n+2+i]="	"+rule2[i];
		       }
		       temp[data_n+2+if_n]="then	"+rule3;
		       int temp_n = data_n+3+if_n;

		       //delete_text(DoFrame.enter1);
		       delete_text("CarShop.data");
		        for (int i=0;i<temp_n;i++){
		            write_text(temp[i],"CarShop.data"); //ファイル書き込み
		        }
		}//checkが通るときのみ
    }
/////////////////////ここまで6.4用/////////////////////////////////

//main    少し削っています
    public static void main(String args[]){
        PrefFrame frm0 = new PrefFrame("前向き推論  ルールベースシステム");
        frm0.setLocation(100, 100); //表示位置
        frm0.setSize(400, 400); //表示サイズ
        frm0.setResizable(false); //リサイズの禁止
        frm0.setVisible(true);
    }//main
}//class

//////////////ここまでshinkaiが変更しました//////////////////////////

/////////////////////////////ワ－キングメモリ/////////////////////////////////

class WorkingMemory {
    ArrayList<String> assertions;

    WorkingMemory(){
        assertions = new ArrayList<String>();
    }
    /**
     * マッチするアサーションに対するバインディング情報を返す
     * （再帰的）
     *
     * @param     前件を示す ArrayList
     * @return    バインディング情報が入っている ArrayList
     */
  //flag = 0の時は追加されるアサーションの探索、flag = 1の時は質問文への対応、
    public ArrayList matchingAssertions(ArrayList<String> theAntecedents,int flag){
        ArrayList bindings = new ArrayList();
        return matchable(theAntecedents,0,bindings,flag);
    }
    private ArrayList matchable(ArrayList<String> theAntecedents,int n,ArrayList bindings,int flag){
        if(n == theAntecedents.size()){
            return bindings;
        } else if (n == 0){
            boolean success = false;
            for(int i = 0 ; i < assertions.size() ; i++){
                HashMap<String,String> binding = new HashMap<String,String>();
                if((new Matcher()).matching(
                    (String)theAntecedents.get(n),
                    (String)assertions.get(i),
                    binding,flag)){
                    bindings.add(binding);
                    success = true;
                }
            }
            if(success){
                return matchable(theAntecedents, n+1, bindings,flag);
            } else {
                return null;
            }
        } else {
            boolean success = false;
            ArrayList newBindings = new ArrayList();
            for(int i = 0 ; i < bindings.size() ; i++){
                for(int j = 0 ; j < assertions.size() ; j++){
                    if((new Matcher()).matching(
                        (String)theAntecedents.get(n),
                        (String)assertions.get(j),
                        (HashMap)bindings.get(i),flag)){
                        newBindings.add(bindings.get(i));
                        success = true;
                    }
                }
            }
            if(success){
                return matchable(theAntecedents,n+1,newBindings,flag);
            } else {
                return null;
            }
        }
    }

    /**
     * アサーションをワーキングメモリに加える．
     *
     * @param     アサーションを表す String
     */
    public void addAssertion(String theAssertion){
        System.out.println("ADD:"+theAssertion);
        RuleBaseSystem.write_text("ADD:"+theAssertion,"test.txt"); //ファイル書き込み
        assertions.add(theAssertion);
    }

    /**
     * 指定されたアサーションがすでに含まれているかどうかを調べる．
     *
     * @param     アサーションを表す String
     * @return    含まれていれば true，含まれていなければ false
     */
    public boolean contains(String theAssertion){
        return assertions.contains(theAssertion);
    }

    /**
     * ワーキングメモリの情報をストリングとして返す．
     *
     * @return    ワーキングメモリの情報を表す String
     */
    public String toString(){
        return assertions.toString();
    }

}

////////////////////////////////ルールベース///////////////////
class RuleBase {
    String fileName;
    FileReader f;
    StreamTokenizer st;
    WorkingMemory wm;
    ArrayList<Rule> rules;

    RuleBase(){
    	/*
		System.out.println("ルールファイル名を入力してください。");
			System.out.println("例:CarShop.data");
			System.out.print("->");
			*/
    	fileName = DoFrame.enter1;
    	//fileName = "CarShop.data";
        wm = new WorkingMemory();
        //追加
        if(fileName.equals("CarShop.data")){
        	wm.addAssertion("my-car is inexpensive");
        	wm.addAssertion("my-car has a VTEC engine");
        	wm.addAssertion("my-car is stylish");
        	wm.addAssertion("my-car has several color models");
        	wm.addAssertion("my-car has several seats");
        	wm.addAssertion("my-car is a wagon");
        }else if(fileName.equals("sotuken.data")){
           wm.addAssertion("Tomomichi wants to study AI");
           wm.addAssertion("Ryota wants to study AI");
           wm.addAssertion("Kodai wants to study AI");
           wm.addAssertion("Daisuke wants to study AI");
           wm.addAssertion("Kenta wants to study AI");
        }
        	/*
    		System.out.println("追加するアサーションを入力してください。");
    		System.out.println("例:my-car is inexpensive");
    		System.out.println("終了するならexitを入力してください。");
    		System.out.print("->");
    		*/
        for(int i=1;i<DoFrame.enter_list_n2;i++){
			String s = DoFrame.enter_list2[i];
			wm.addAssertion(s);
		}
        //初期条件としてこれらをaddする
        rules = new ArrayList<Rule>();
        loadRules(fileName);
    }
  //flag = 0ならルールファイル名入力、flag = 1ならアサーション追加、flag = 2なら質問文入力

    //ここまで
    /**
     * 前向き推論を行うためのメソッド
     *
     */
    public void forwardChain(){
        boolean newAssertionCreated;
        // 新しいアサーションが生成されなくなるまで続ける．
        do {
            newAssertionCreated = false;
            for(int i = 0 ; i < rules.size(); i++){
                Rule aRule = (Rule)rules.get(i);
                System.out.println("apply rule:"+aRule.getName());
                RuleBaseSystem.write_text("apply rule:"+aRule.getName(),"test.txt"); //ファイル書き込み
                ArrayList<String> antecedents = aRule.getAntecedents();
                String consequent  = aRule.getConsequent();
                //HashMap bindings = wm.matchingAssertions(antecedents);
                ArrayList bindings = wm.matchingAssertions(antecedents,0);
                if(bindings != null){
                    for(int j = 0 ; j < bindings.size() ; j++){
                        //後件をインスタンシエーション
                        String newAssertion =
                            instantiate((String)consequent,
                                        (HashMap)bindings.get(j));
                        //ワーキングメモリーになければ成功
                        if(!wm.contains(newAssertion)){
                            System.out.println("Success: "+newAssertion);
                            RuleBaseSystem.write_text("Success: "+newAssertion,"test.txt");
                            wm.addAssertion(newAssertion);
                            newAssertionCreated = true;
                        }
                    }
                }
            }
            System.out.println("Working Memory"+wm);
            RuleBaseSystem.write_text("Working Memory"+wm,"test.txt");
        } while(newAssertionCreated);
        System.out.println("No rule produces a new assertion");
        RuleBaseSystem.write_text("No rule produces a new assertion","test.txt");
      //追加
        ArrayList<String> Q = new ArrayList<String>();

/*
		System.out.println("質問文を1行づつ入力してください。");
		System.out.println("例:?x is a ?y");
		System.out.println("すべての質問文を入力したらexitを入力してください。");
		System.out.print("->");
*/
		 for(int i=1;i<DoFrame.enter_list_n3;i++){
				String s = DoFrame.enter_list3[i];
				Q.add(s);
			}

        System.out.println("Query" + Q);
        System.out.println("Answer" + wm.matchingAssertions(Q,1));
        DoFrame.sample6 = "A." + wm.matchingAssertions(Q,1);
        //ここまで

    }

    private String instantiate(String thePattern, HashMap theBindings){
        String result = new String();
        StringTokenizer st = new StringTokenizer(thePattern);
        for(int i = 0 ; i < st.countTokens();){
            String tmp = st.nextToken();
            if(var(tmp)){
            	//追加
            	if(!theBindings.containsKey(tmp)){
            		result = result + " " + tmp;
            	}else{
            		result = result + " " + (String)theBindings.get(tmp);
            	}
            	//ここまで
            } else {
                result = result + " " + tmp;
            }
        }
        return result.trim();
    }

    private boolean var(String str1){
        // 先頭が ? なら変数
        return str1.startsWith("?");
    }
    private void loadRules(String theFileName){
        String line;
        try{
            int token;
            f = new FileReader(theFileName);
            st = new StreamTokenizer(f);
            while((token = st.nextToken())!= StreamTokenizer.TT_EOF){
                switch(token){
                    case StreamTokenizer.TT_WORD:
                        String name = null;
                        ArrayList<String> antecedents = null;
                        String consequent = null;
                        if("rule".equals(st.sval)){
			    st.nextToken();
//                            if(st.nextToken() == '"'){
                                name = st.sval;
                                st.nextToken();
                                if("if".equals(st.sval)){
                                    antecedents = new ArrayList<String>();
                                    st.nextToken();
                                    while(!"then".equals(st.sval)){
                                        antecedents.add(st.sval);
                                        st.nextToken();
                                    }
                                    if("then".equals(st.sval)){
                                        st.nextToken();
                                        consequent = st.sval;
                                    }
                                }
//                            }
                                //追加
                                else{
                                	System.out.println("ERROR");
                                	break;
                                }
                                //ここまで
                        }
			// ルールの生成
                        rules.add(new Rule(name,antecedents,consequent));
                        break;
                    default:
                        System.out.println(token);
                        break;
                }
            }
        } catch(Exception e){
            System.out.println(e);
        }
        for(int i = 0 ; i < rules.size() ; i++){
            System.out.println(((Rule)rules.get(i)).toString());
            RuleBaseSystem.write_text(((Rule)rules.get(i)).toString(),"test.txt");
        }
    }
}
//////////////////////////ルール////////////////////////////
class Rule {
    String name;
    ArrayList<String> antecedents;
    String consequent;

    Rule(String theName,ArrayList<String> theAntecedents,String theConsequent){
        this.name = theName;
        this.antecedents = theAntecedents;
        this.consequent = theConsequent;
    }

    /**
     * ルールの名前を返す．
     *
     * @return    名前を表す String
     */
    public String getName(){
        return name;
    }
    /**
     * ルールをString形式で返す
     *
     * @return    ルールを整形したString
     */
    public String toString(){
        return name+" "+antecedents.toString()+"->"+consequent;
    }
    /**
     * ルールの前件を返す．
     *
     * @return    前件を表す ArrayList
     */
    public ArrayList<String> getAntecedents(){
        return antecedents;
    }
    /**
     * ルールの後件を返す．
     *
     * @return    後件を表す String
     */
    public String getConsequent(){
        return consequent;
    }

}

class Matcher {
    StringTokenizer st1;
    StringTokenizer st2;
    HashMap<String,String> vars;

    Matcher(){
        vars = new HashMap<String,String>();
    }

    public boolean matching(String string1,String string2,HashMap<String,String> bindings, int flag){
        this.vars = bindings;
        return matching(string1,string2,flag);
    }

    public boolean matching(String string1,String string2,int flag){
        //System.out.println(string1);
        //System.out.println(string2);
        // 同じなら成功
        if(string1.equals(string2)) return true;
        // 各々トークンに分ける
        st1 = new StringTokenizer(string1);
        st2 = new StringTokenizer(string2);
        // 数が異なったら失敗
        if(st1.countTokens() != st2.countTokens()) return false;

        // 定数同士
        for(int i = 0 ; i < st1.countTokens();){
            if(!tokenMatching(st1.nextToken(),st2.nextToken(),flag)){
                // トークンが一つでもマッチングに失敗したら失敗
                return false;
            }
        }
        // 最後まで O.K. なら成功
        return true;
    }



    boolean tokenMatching(String token1,String token2,int flag){
        //System.out.println(token1+"<->"+token2);
        if(token1.equals(token2)) return true;
        if( var(token1) && !var(token2)) return varMatching(token1,token2,flag);
        if(!var(token1) &&  var(token2)) return varMatching(token2,token1,flag);
        return false;
    }

    boolean varMatching(String vartoken,String token,int flag){
        if(vars.containsKey(vartoken)){
            if(token.equals(vars.get(vartoken))){
                return true;
            } else {
             	//追加
            	if(flag == 1){
            		vars.remove(vartoken);
            		vars.put(vartoken,token);
            		//System.out.println(vars);
            	}
            	//ここまで
                return false;
            }
        } else {
            vars.put(vartoken,token);
        }
        return true;
    }

    boolean var(String str1){
        // 先頭が ? なら変数
        return str1.startsWith("?");
    }
}
