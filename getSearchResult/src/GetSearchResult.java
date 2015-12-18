
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class GetSearchResult {

    
static ArrayList<String> sku = new ArrayList<String>();

   public static ArrayList<String> getTitle(String searchString, int index) throws IOException {
        ArrayList<String> results = new ArrayList<>();
        //<editor-fold defaultstate="collapsed" desc="initiliaize google bot for searching">
        String google = "http://www.mycart.pk/";
        String charset = "UTF-8";
        String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
        String url;
        //System.out.println(google + URLEncoder.encode(searchString, charset));
      //  System.setProperty("http.proxyHost", "10.1.20.18");
       // System.setProperty("http.proxyPort", "9090");
        Document doc = Jsoup.connect(searchString).timeout(0).get();
      // System.out.println(doc);

        
//</editor-fold>
        //get center colum only
       // Element searchResultCenterContent = doc.select(".data").select("tr").get(2).select("td").get(2);
       Elements metalinks = doc.select("meta");
      // System.out.println(metalinks.toString());
       // System.out.println(index+"     "+ searchString+ "   , "+  metalinks.get(5).attr("content").replaceAll("sku:", "").toString());
        sku.add(metalinks.get(5).attr("content").replaceAll("sku:", "").toString());
       // System.out.println(searchString+ " , "+ sku.get(index-1));
        return results;
    }
   public static String readFile(String filename) throws IOException {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return content;
    }
    public static void main(String[] args) {
        try {
         String filename="mycart.pk plinks.txt";
         String filecontent=readFile(filename);
         //System.out.println(filecontent);
         String[] splited = filecontent.split("\n");
        // System.out.println(splited[3]); 
          File f = new File ("F:\\Final Year Project\\GithubHareem\\getSearchResult\\Sku.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);

         for(int i=0;i<5;i++){
             getTitle(splited[i],i+1);
         //aquafina-mineral-water-500ml.html
             System.out.println(splited[i]+ "  , "+sku.get(i));
             bw.write(sku.get(i) + System.getProperty("line.separator")); 
      
           }
         

        for(String s : sku) {
           // bw.write(s + System.getProperty("line.separator")); 
        }
        bw.close();
        } catch (Exception ex) {
            Logger.getLogger(GetSearchResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
