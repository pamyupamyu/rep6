import java.util.*;
import java.io.*;

/**
 * RuleBaseSystem
 * 
 */

//前向き推論
public class RuleBaseSystem {
    static RuleBase rb;
    public static void main(String args[]){
        rb = new RuleBase();
        rb.forwardChain();      
    }
}

/**
 * ワーキングメモリを表すクラス．
 *
 * 
 */
class WorkingMemory {
    ArrayList<String> assertions;    

    WorkingMemory(){
        assertions = new ArrayList<String>();
    }

    /**
     * マッチするアサーションに対するバインディング情報を返す
     * （再帰的）
     *
     * @param     前件を示す ArrayList(変数を含む質問文)
     * @return    バインディング情報が入っている ArrayList
     */
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
                    binding,
                    flag)){
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
                        (HashMap)bindings.get(i),
                        flag)){
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

/**
 * ルールベースを表すクラス．
 *
 * 
 */
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
        //追加
        try{
			while(true){
				System.out.println("追加するアサーションを入力してください。");
				System.out.println("終了するならexitを入力してください。");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String s = br.readLine();
				if(s.equals("exit")) break;
				wm.addAssertion(s);
			}
		}catch(IOException e){
			System.out.println("Exception :" + e);
		}
        //ここまで
        
        rules = new ArrayList<Rule>();
        loadRules(fileName); //ルールを全部読み取る
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
                ArrayList<String> antecedents = aRule.getAntecedents();
                String consequent  = aRule.getConsequent();
                //HashMap bindings = wm.matchingAssertions(antecedents);
                ArrayList bindings = wm.matchingAssertions(antecedents,0); //変数束縛を行う
                if(bindings != null){
                    for(int j = 0 ; j < bindings.size() ; j++){
                        //後件をインスタンシエーション(具体化)
                        String newAssertion =
                            instantiate((String)consequent,
                                        (HashMap)bindings.get(j));
                        //ワーキングメモリーになければ成功
                        if(!wm.contains(newAssertion)){
                            System.out.println("Success: "+newAssertion);
                            wm.addAssertion(newAssertion);
                            /*追加
                            for(int k = 0; k < bindings.size(); ++k){
                            	System.out.println(bindings.get(k));
                            }
                            ここまで
                            */
                            newAssertionCreated = true;
                        }
                    }
                }
            }
            System.out.println("Working Memory"+wm); //ルールを1周調べたら現状のワーキングメモリ出力
        } while(newAssertionCreated); //ワーキングメモリに変更を行わなくなるまで繰り返す
        System.out.println("No rule produces a new assertion");
        //追加
        ArrayList<String> Q = new ArrayList<String>();
        
        try{
			while(true){
				System.out.println("質問文を1行づつ入力してください。(例)?a is a ?b");
				System.out.println("すべての質問文を入力したらexitを入力してください。");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String s = br.readLine();
				if(s.equals("exit")) break;
				Q.add(s);
			}
		}catch(IOException e){
			System.out.println("Exception :" + e);
		}
        System.out.println("Query" + Q);
        System.out.println("Answer" + wm.matchingAssertions(Q,1));
        //ここまで
    }

    //thePatternが変数のままの後件で、その変数が具体化できるなら、具体化した文に書き換える。
    private String instantiate(String thePattern, HashMap theBindings){
        String result = new String();
        StringTokenizer st = new StringTokenizer(thePattern);
        for(int i = 0 ; i < st.countTokens();){
            String tmp = st.nextToken();
            //後件に具体化されない変数があった場合どうなる？？？？
            /*具体例
             * if ?xはポッケである
             * then ?xには?zが入る
             * 
             * 結果
             * 具体化されてないので?zにnullが入る
             * 
             * apply rule:CarRule8
             * Success: my-car is a null
             * ADD:my-car is a null
             * apply rule:CarRule9
             * apply rule:CarRule10
             * apply rule:CarRule11
             * apply rule:CarRule12
             * apply rule:CarRule13
             * apply rule:CarRule14
             * apply rule:CarRule15
             * 本来CarRule11を満足するはずなのにしない。
             * 
             * 解決
             * CarRule8の"?x is a Honda"を"?x is a ?y"にしてみてください
             * 
             */
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
                        if("rule".equals(st.sval)){ //ruleの行に入ったら
                        	st.nextToken();
//                            if(st.nextToken() == '"'){
                                name = st.sval; //rule名をnameに保存
                                st.nextToken();
                                if("if".equals(st.sval)){ //ifの行に入ったら
                                    antecedents = new ArrayList<String>();
                                    st.nextToken();
                                    while(!"then".equals(st.sval)){ //まだthenでないなら
                                        antecedents.add(st.sval); //antecedentsに条件を追加していく
                                        st.nextToken();
                                    }
                                    if("then".equals(st.sval)){ //thenなら
                                        st.nextToken();
                                        consequent = st.sval; //consequentに追加するアサーションを保存
                                    }
                                }
//                            }
                         //追加
                        }else{
                        	System.out.println("ERROR");
                        	break;
                        }
                        //ここまで
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
        }
    }
}

/**
 * ルールを表すクラス．
 *
 * 
 */
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

    public boolean matching(String string1,String string2,HashMap<String,String> bindings,int flag){
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
            //
            
            /*追加
            if(flag == 1)
             	System.out.println(vars);
            ここまで
            */
        }
        return true;
    }

    boolean var(String str1){
        // 先頭が ? なら変数
        return str1.startsWith("?");
    }

}
