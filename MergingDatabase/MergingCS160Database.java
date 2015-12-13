/*
* Alik-Serguy Alphonsovich Rukubayihunga
* CS152-Programming Paradigms
* TuTh 12:00-13:15 Fall 2015
*/
package cs160.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author AlSyR
 */
public class CS160Database {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        PrintWriter file = new PrintWriter((new FileWriter("/Users/AlSyR/Downloads/prof")));
        BufferedReader br = new BufferedReader(new FileReader("/Users/AlSyR/Downloads/professors.txt"));
        
//        PrintWriter file = new PrintWriter((new FileWriter("/Users/AlSyR/Downloads/courses")));
//        BufferedReader br = new BufferedReader(new FileReader("/Users/AlSyR/Downloads/courses.txt"));
        
//        String test =  "(1, 'Margaret Schedel', 'https://coursera-instructor-photos.s3.amazonaws.com/60/29d66075696e9452be2b598b06eda1/headshot2012.jpg'123, 1322)";
        String input;
        
        
        Pattern pattern = Pattern.compile("(\\([0-9]+,)");
        Pattern pattern2 = Pattern.compile("([0-9]+\\))");
        
        
        while((input = br.readLine()) != null){
            Matcher matcher = pattern.matcher(input);
            Matcher matcher2 = pattern2.matcher(input);
            String newInput = input;
            
            if (matcher.find( )) {
                int intValue = Integer.parseInt((matcher.group(0)).replaceAll("[^0-9]", ""));
                int newValue = intValue + 4000;
                String newString = "(" + newValue +",";
                newInput = newInput.replaceAll("(\\([0-9]+,)",newString);
//                System.out.println(test);
            }
            if (matcher2.find( )) {
                int intValue = Integer.parseInt((matcher2.group(0)).replaceAll("[^0-9]", ""));
                int newValue = intValue + 4000;
                String newString = newValue +")";
                newInput = newInput.replaceAll("([0-9]+\\))",newString);
//                System.out.println(test);
            }
            file.println(newInput);
//            System.out.println(test);
        }
        br.close();
        file.close();
             
    }
    
}
