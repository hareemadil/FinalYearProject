
import java.sql.ResultSet;
import java.util.ArrayList;



/**
 *
 * @author Hx
 */
public class NameMapping {

    /**
     * @param args the command line arguments
     */
    static int threhsold = 5;
    static int globalVariable=1;
    static ArrayList<ArrayList<String>> SimilarWords;
    static ArrayList<String> cluster;
    static String[][] dataBase;
    
    public static void main(String[] args) {
        SimilarWords = new ArrayList<>();
        cluster = new ArrayList<>();
        try {
            populateDbArray();
            
            
            for(int i=0; i < dataBase.length; i++){
                cluster.clear();
            for(int j=i+1; j < 2 /*dataBase.length*/; j++){
                //System.out.println(dataBase[i][0]+","+dataBase[j][0]+" = "+editDistance(dataBase[i][0],dataBase[j][0]));        
                if(editDistance(dataBase[i][0], dataBase[j][0])< 15){
                    if( Math.abs(Integer.parseInt(dataBase[i][1].replace("Rs.", "").replace("Price", ""))-Integer.parseInt(dataBase[j][1].replace("Rs.", "").replace("Price", ""))) < 10 ){
                        if(editDistance(dataBase[i][2], dataBase[j][2]) < 5){
                            cluster.add(dataBase[i][0]);
                            cluster.add(dataBase[i][1]);
                        }
                    }
                }
            
            }
            SimilarWords.add(cluster);
            
            }
            
         
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static int editDistance(String word1, String word2) {
	int len1 = word1.length();
	int len2 = word2.length();
 
	// len1+1, len2+1, because finally return dp[len1][len2]
	int[][] dp = new int[len1 + 1][len2 + 1];
 
	for (int i = 0; i <= len1; i++) {
		dp[i][0] = i;
	}
 
	for (int j = 0; j <= len2; j++) {
		dp[0][j] = j;
	}
 
	//iterate though, and check last char
	for (int i = 0; i < len1; i++) {
		char c1 = word1.charAt(i);
		for (int j = 0; j < len2; j++) {
			char c2 = word2.charAt(j);
 
			//if last two chars equal
			if (c1 == c2) {
				//update dp value for +1 length
				dp[i + 1][j + 1] = dp[i][j];
			} else {
				int replace = dp[i][j] + 1;
				int insert = dp[i][j + 1] + 1;
				int delete = dp[i + 1][j] + 1;
 
				int min = replace > insert ? insert : replace;
				min = delete > min ? min : delete;
				dp[i + 1][j + 1] = min;
			}
		}
	}
 
	return dp[len1][len2];
}

    private static void populateDbArray() throws Exception {
        Database db = new Database();
        db.openConnection();
        ResultSet rs = db.read("select pname,price,weight from `combined` order by pname asc");
        ResultSet rs2 = db.read("select count(*) from combined");
        rs.next();
        rs2.next();
        System.out.println("size="+rs2.getInt(1));
        dataBase = new String[rs2.getInt(1)][3];
        
        for (int i=1;i <= dataBase.length; i++){
        dataBase[i-1][0]= rs.getString("pname");
        dataBase[i-1][1]= rs.getString("price");
        dataBase[i-1][2]= rs.getString("weight");
    //        System.out.println("["+i+"]:"+dataBase[i-1][0]+",2:"+dataBase[i-1][1]+",3:"+dataBase[i-1][2]);
            rs.next();
        }
        
    }

    

}
