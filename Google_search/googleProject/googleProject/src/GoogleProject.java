
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import javax.lang.model.element.Element;
//import javax.lang.model.util.Elements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang3.ArrayUtils;
public class GoogleProject {

    public static ArrayList<String> sendList(String searchString) throws IOException {
        ArrayList<String> results = new ArrayList<>();
        //<editor-fold defaultstate="collapsed" desc="initiliaize google bot for searching">
        String google = "http://www.google.com/search?q=";
        String charset = "UTF-8";
        String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
        String url;
        //System.out.println(google + URLEncoder.encode(searchString, charset));
        // System.setProperty("http.proxyHost", "10.2.20.18");
        //System.setProperty("http.proxyPort", "9090");
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

    public static String join(Collection<String> s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator<String> iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }

    public String readFile(String filename) throws IOException {
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

    public String getStringTripple(String a) {

        String s[] = a.split(" ");
        List<String> asList = Arrays.asList(s);
        //Set<String> mySet = new HashSet<String>(asList);
        Map<String, Integer> unique = new LinkedHashMap<String, Integer>();
        String string = "";
        int count = 0;
        for (int i = 0; i < asList.size(); i++) {
            for (int j = i; j < s.length; j++) {
                count++;
                if (count > 7) {
                    break;
                }
                string += asList.get(j);
                if (unique.get(string) == null) {
                    unique.put(string, 1);
                } else {
                    unique.put(string, unique.get(string) + 1);
                }
               //System.out.println(asList.get(i));

            }
            count = 0;
            string = "";
        }
        String uniqueString = join(unique.keySet(), ", ");
        List<Integer> value = new ArrayList<Integer>(unique.values());
        // Get a set of the entries
        Set set = unique.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        String max = "";
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
         System.out.print(me.getKey() + ": ");
             System.out.println(me.getValue());
        }
        int maxValueInMap = (Collections.max(unique.values()));  // This will return max value in the Hashmap
        System.out.println(maxValueInMap);
        for (Map.Entry<String, Integer> entry : unique.entrySet()) {  // Itrate through hashmap
            if (entry.getValue() == maxValueInMap) {
                System.out.println(entry.getKey());     // Print the key with max value
                max = entry.getKey();
            }
        }

       // System.out.println("Output = " + uniqueString);
        //System.out.println("Values = " + value);
        return max;
    }

}
