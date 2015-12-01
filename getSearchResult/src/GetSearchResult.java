
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

    


   public static ArrayList<String> getTitle(String searchString) throws IOException {
        ArrayList<String> results = new ArrayList<>();
        //<editor-fold defaultstate="collapsed" desc="initiliaize google bot for searching">
        String google = "http://www.upcdatabase.com/item/";
        String charset = "UTF-8";
        String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
        String url;
        //System.out.println(google + URLEncoder.encode(searchString, charset));
      //  System.setProperty("http.proxyHost", "10.1.20.18");
       // System.setProperty("http.proxyPort", "9090");
        Document doc = Jsoup.connect(google + URLEncoder.encode(searchString, charset)).userAgent(userAgent).get();
        // System.out.println(doc);

//</editor-fold>
        //get center colum only
        Element searchResultCenterContent = doc.select(".data").select("tr").get(2).select("td").get(2);
        System.out.println(searchResultCenterContent.text());
        
        return results;
    }
   
    public static ArrayList<String> getDesc(String searchString) throws IOException {
        ArrayList<String> results = new ArrayList<>();
        //<editor-fold defaultstate="collapsed" desc="initiliaize google bot for searching">
        String google = "http://www.google.com/search?q=";
        String charset = "UTF-8";
        String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
        String url;
        //System.out.println(google + URLEncoder.encode(searchString, charset));
        System.setProperty("http.proxyHost", "10.1.20.18");
        System.setProperty("http.proxyPort", "9090");
        Document doc = Jsoup.connect(google + URLEncoder.encode(searchString, charset)).userAgent(userAgent).get();
        // System.out.println(doc);

//</editor-fold>
        //get center colum only
        Element searchResultCenterContent = doc.getElementById("center_col");

        //get Description
      Elements top10SearchResults = searchResultCenterContent.getElementsByClass("st");
        //travers each result, extract text(description) from it and add it to array
        for (Element link : top10SearchResults) {
            results.add(link.text());
            //System.out.println(" ok1 ==  "+link.text());
        }

        return results;
    }

    public static void main(String[] args) {
        try {
//            int i = 1;
//            String input = "012000014338";
//            System.out.println("Input: "+input);
//            
//            ArrayList<String> title=getTitle(input);
//           
//            for (String x : title) {
//                System.out.println( x);
//                
//            }
            java.util.regex.Pattern p = java.util.regex.Pattern.compile("(\\d+\\s?(?:Gr|gm|ml|kg|KG|Kg|g|G|gr|l|L|ML|Ml))");
            java.util.regex.Matcher m = p.matcher("Nestle Nesvita Milk 200gm");
            
            
            ArrayList<String> matches = new ArrayList<String>();
            if (m.find()) 
                System.out.println( m.group(1));
                else
                System.out.println("Nestle Nesvita Milk 200ml");
           
        } catch (Exception ex) {
            Logger.getLogger(GetSearchResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
