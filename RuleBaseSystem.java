package ForePacd;
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
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

///RuleBaseSystem
///////////////////////前向き推論/////////////////////
//////////////////////GUI作成用///////////////////////
/////////ここからmain文までshinkaiが変更しました//////

//過程用フレームの作成
class LaterFrame extends JFrame{

	static String sample5 ="";
    JPanel p = new JPanel();

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



      p.add(label5);
      label5.setText(sample5);

      Container contentPane = getContentPane();
      contentPane.add(p, BorderLayout.CENTER);
    }
}
//メインのフレームの作成
class PrefFrame extends JFrame implements ActionListener{

    static String sample1 = "サンプル1";
    static String sample2 = "サンプル2";
    static String sample3 = "サンプル3";
    static String sample4 = "サンプル4";
    JPanel p = new JPanel();

    JLabel label1 = new JLabel(sample1);
    JLabel label2 = new JLabel(sample2);
    JLabel label3 = new JLabel(sample3);
    JLabel label4 = new JLabel(sample4);
    Button b1 = new Button("押さないで");
    Button b2 = new Button("do");
    Button b3 = new Button("delete");
    Button b4 = new Button("add");

    public PrefFrame(String title) {
      //フレームのタイトル
      setTitle(title);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      p.setLayout(null);

      label1.setForeground(Color.BLUE);
      label1.setBackground(Color.WHITE);
      label1.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 26));
      label1.setOpaque(true);
      label1.setBounds(0, 0, 900, 50);

      label2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 10));
      label2.setBounds(0, 40, 900, 160);

      label3.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 10));
      label3.setBounds(0, 200, 900, 300);

      label4.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
      label4.setBackground(Color.WHITE);
      label4.setBounds(0, 550, 900, 50);

      b1.addActionListener(this);
      b1.setBounds(0, 500, 70, 40);
      b2.addActionListener(this);
      b2.setBounds(80, 500, 70, 40);
      b3.addActionListener(this);
      b3.setBounds(160, 500, 70, 40);
      b4.addActionListener(this);
      b4.setBounds(240, 500, 70, 40);

      p.add(label1);
      p.add(label2);
      p.add(label3);
      p.add(label4);
      p.add(b1);
      p.add(b2);
      p.add(b3);
      p.add(b4);

      Container contentPane = getContentPane();
      contentPane.add(p, BorderLayout.CENTER);
    }

    //ボタンを押したときの実装
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
        RuleBaseSystem.write_text("","CarShop.data");
        RuleBaseSystem.write_text("rule	\"CarRule16\"","CarShop.data");
        RuleBaseSystem.write_text("if	\"?x is a charging go\"","CarShop.data");
        RuleBaseSystem.write_text("then	\"?x is KICHIGAI\"","CarShop.data");
        sample2 = "押すとテキストに追加します";
        label2.setText(sample2);
        sample3 = "<html>rule \"CarRule16\"<br>if \"?x is a charging go\"<br>then\"?x is KICHIGAI\"<html>";
        label3.setText(sample3);
        }else if (ae.getSource() == b2) {
        	RuleBaseSystem.delete_text("test.txt");
        	RuleBaseSystem.printGUI();
        	label1.setText(sample1);
            label2.setText(sample2);
            label3.setText(sample3);
            RuleBaseSystem.printPath();
            LaterFrame frm2 = new LaterFrame("過程表示");
            frm2.setLocation(890, 0); //表示位置
            frm2.setSize(480, 700); //表示サイズ
            frm2.setVisible(true);
        }else if (ae.getSource() == b3) {
            String data[] = RuleBaseSystem.read_text("CarShop.data");
            RuleBaseSystem.delete_rule(data);
            label4.setText("Delete Rule : " +sample4);
        }else if (ae.getSource() == b4) {
            String data[] = RuleBaseSystem.read_text("CarShop.data");
            RuleBaseSystem.add_rule(data);
            label4.setText("Add Rule : "+sample4);
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
		//System.out.println(ans);
		return ans;
    }
//ADDやルール一覧をGUIに表示する
    public static void printGUI(){
        rb = new RuleBase();
        rb.forwardChain();
        String archive[] = read_text("test.txt");
        int arc_n = text_n("test.txt");
        String CRList[] = matchList(archive,"Car");
        String ADDList[] = matchList(archive,"ADD");

        int CRL_n = String_n(CRList);
        int ADDL_n = String_n(ADDList);
        PrefFrame.sample3 = "<html>ルール一覧<br>";
       for(int i=0;i<CRL_n;i++)
        PrefFrame.sample3 = PrefFrame.sample3 +CRList[i] +  "<br>";
       PrefFrame.sample3 = PrefFrame.sample3 + "<html>";

       PrefFrame.sample2 = "<html>追加された一覧<br>";
       for(int i=0;i<ADDL_n;i++)
        PrefFrame.sample2 = PrefFrame.sample2 +ADDList[i] +  "<br>";
       PrefFrame.sample2 = PrefFrame.sample2 + "<html>";

       String a =answer(archive,arc_n);
       PrefFrame.sample1 = "A."+a;
    }
//過程をGUIに表示する
    public static void printPath(){
   // rb = new RuleBase();
   // rb.forwardChain();
    String archive[] = read_text("test.txt");
    int arc_n = text_n("test.txt");

    System.out.println(arc_n);
    System.out.println(archive[20]);
    boolean search =false;

    LaterFrame.sample5 = "<html>";
    for(int i=0;i<arc_n;i++){
    	String check = archive[i].substring(0, 3);
    	if(check.equals("ADD")){
    	LaterFrame.sample5 =LaterFrame.sample5+archive[i]+"<br>";
    	}else if(check.equals("Suc")){
    		LaterFrame.sample5 =LaterFrame.sample5+archive[i-1]+"<br>";
    		LaterFrame.sample5 =LaterFrame.sample5+"上記のルールが適応された！！<br>";
    	}else if(check.equals("Wor")){
    		LaterFrame.sample5 =LaterFrame.sample5+"探索が一巡した<br>";
    	}else if(check.equals("No ")){
    	 LaterFrame.sample5 = LaterFrame.sample5+"探索終了<html>";
    	}else if(check.equals("app")){
        	String before = archive[i-1].substring(0, 3);
        	if(before.equals("app")||before.equals("ADD")){}else{
        		LaterFrame.sample5 = LaterFrame.sample5+"探索開始<br>";
        	}
    	}


    }


}

////////////////////以下課題6.4用メソッド/////////////////////////
//ルールの削除
    public static void delete_rule(String[] data){

        System.out.println("削除するルールを決定してください");
        System.out.println("Example  \"CarRule15\" ");
        Scanner scan = new Scanner(System.in);
        String rule = scan.next();
        PrefFrame.sample4 = rule;
        System.out.println("以下のルールを検索します:"+ PrefFrame.sample4);

    	int data_n = text_n("CarShop.data");
		int check_s=0;
		int check_n=0;
		boolean check=true;
		for(int i=0;i<data_n&&check;i++){
			if(data[i].endsWith(rule)){
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

        delete_text("CarShop.data");
        for (int i=0;i<temp_n;i++){
            write_text(temp[i],"CarShop.data"); //ファイル書き込み
        }
    }
//ルールの追加
    public static void add_rule(String[] data){
        System.out.println("加えるルール名を決定してください");
        System.out.println("Example  \"CarRule16\" ");
        Scanner scan = new Scanner(System.in);
        String rule1 = scan.nextLine();

        PrefFrame.sample4 = rule1;
        System.out.println("以下のルールを追加します: "+ PrefFrame.sample4);

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
		        rule2[0] = scan.nextLine();
		       System.out.println("以下の制約を追加します: "+ rule2[0]);

		       boolean cont =true;
		       int if_n=0;
		       for(int i=1;i<10&&cont;i++){
		    	   System.out.println("続けてif文を入力しますか？  yes or no");
		    	   String choice = scan.nextLine();
		    	   if(choice.equals("yes")){
				       System.out.println("ifに続く文字を入力してください");
				       rule2[i] = scan.nextLine();
				       System.out.println("以下の制約を追加します: "+ rule2[i]);
		    	   }else{
		    		   cont =false;
		    		   if_n=i;
		    	   }
		       }//for
		  ////////////////////////then内の追加//////////////////////////////
		       System.out.println("thenに続く文字を入力してください");
		       String rule3 = scan.nextLine();
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

		       delete_text("CarShop.data");
		        for (int i=0;i<temp_n;i++){
		            write_text(temp[i],"CarShop.data"); //ファイル書き込み
		        }
		}//checkが通るときのみ

    }
/*ルールの編集
    public static void edit_rule(String[] data){
    	delete_rule(data);
    	add_rule(data);
    }
*/
/////////////////////ここまで6.4用/////////////////////////////////

//main    少し削っています
    public static void main(String args[]){
        PrefFrame frm1 = new PrefFrame("前向き推論  ルールベースシステム");
        frm1.setLocation(0, 0); //表示位置
        frm1.setSize(900, 700); //表示サイズ
        frm1.setVisible(true);

    }//main
}//class

//////////////ここまでshinkaiが変更しました//////////////////////////
//////////////以下はほとんど変更していません///////////////////

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
    public ArrayList matchingAssertions(ArrayList<String> theAntecedents){
        ArrayList bindings = new ArrayList();
        return matchable(theAntecedents,0,bindings);
    }
    private ArrayList matchable(ArrayList<String> theAntecedents,int n,ArrayList bindings){
        if(n == theAntecedents.size()){
            return bindings;
        } else if (n == 0){
            boolean success = false;
            for(int i = 0 ; i < assertions.size() ; i++){
                HashMap<String,String> binding = new HashMap<String,String>();
                if((new Matcher()).matching(
                    (String)theAntecedents.get(n),
                    (String)assertions.get(i),
                    binding)){
                    bindings.add(binding);
                    success = true;
                }
            }
            if(success){
                return matchable(theAntecedents, n+1, bindings);
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
                        (HashMap)bindings.get(i))){
                        newBindings.add(bindings.get(i));
                        success = true;
                    }
                }
            }
            if(success){
                return matchable(theAntecedents,n+1,newBindings);
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
        fileName = "CarShop.data";
        wm = new WorkingMemory();
        wm.addAssertion("my-car is inexpensive");
        wm.addAssertion("my-car has a VTEC engine");
        wm.addAssertion("my-car is stylish");
        wm.addAssertion("my-car has several color models");
        wm.addAssertion("my-car has several seats");
        wm.addAssertion("my-car is a wagon");
        //初期条件としてこれらをaddする
        rules = new ArrayList<Rule>();
        loadRules(fileName);
    }

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
                ArrayList bindings = wm.matchingAssertions(antecedents);
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

    }

    private String instantiate(String thePattern, HashMap theBindings){
        String result = new String();
        StringTokenizer st = new StringTokenizer(thePattern);
        for(int i = 0 ; i < st.countTokens();){
            String tmp = st.nextToken();
            if(var(tmp)){
                result = result + " " + (String)theBindings.get(tmp);
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

    public boolean matching(String string1,String string2,HashMap<String,String> bindings){
        this.vars = bindings;
        return matching(string1,string2);
    }

    public boolean matching(String string1,String string2){
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
            if(!tokenMatching(st1.nextToken(),st2.nextToken())){
                // トークンが一つでもマッチングに失敗したら失敗
                return false;
            }
        }
        // 最後まで O.K. なら成功
        return true;
    }



    boolean tokenMatching(String token1,String token2){
        //System.out.println(token1+"<->"+token2);
        if(token1.equals(token2)) return true;
        if( var(token1) && !var(token2)) return varMatching(token1,token2);
        if(!var(token1) &&  var(token2)) return varMatching(token2,token1);
        return false;
    }

    boolean varMatching(String vartoken,String token){
        if(vars.containsKey(vartoken)){
            if(token.equals(vars.get(vartoken))){
                return true;
            } else {
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