
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleSearchCrawler {

    public static ArrayList<String> sendList(String searchString) throws IOException {
        ArrayList<String> results = new ArrayList<>();
        //<editor-fold defaultstate="collapsed" desc="initiliaize google bot for searching">
        String google = "http://www.google.com/search?q=";
        String charset = "UTF-8";
        String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
        String url;
        //System.out.println(google + URLEncoder.encode(searchString, charset));
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
            int i = 1;
            String input = "6281006437647";
            System.out.println("Input: "+input);
            
            for (String x : sendList(input)) {
                System.out.println(i++ + " ------ " + x);
            }

        } catch (IOException ex) {
            Logger.getLogger(GoogleSearchCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
