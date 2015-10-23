
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Asif
 */
public class NewMain {
  

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        GoogleProject obj= new GoogleProject();
        String filename="CaptionsOnly.txt";
        String content = obj.readFile(filename);
        
   try {
            int i = 1;
            String input = "6281006437647";
            System.out.println("Input: "+input);
            ArrayList<String> results= obj.sendList(input);
            
            for (String x : results) {
              // content=results.toString();
               // System.out.println(i++ + " ------ " + x);
            }
            
           // System.out.println(content);
            //obj.getStringTripple(content);
        } catch (IOException ex) {
            Logger.getLogger(GoogleProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     String output=  obj.getStringTripple(content);
       /* String uniqueString = join(unique.keySet(), ", ");
        List<Integer> value = new ArrayList<Integer>(unique.values());

      //  System.out.println("Output = " + uniqueString);
       // System.out.println("Values = " + value);
   */
        
   
    }
}
