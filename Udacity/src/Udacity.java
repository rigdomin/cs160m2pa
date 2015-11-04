import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;;

public class Udacity 
{
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoClassDefFoundError
	{
		String line = null;
		ArrayList<String> links = new ArrayList<String>();
		
		//READS ALL URLs FROM TEXTFILE
		try{
			FileReader fi = new FileReader("src/UdacityCourselinks.txt");
			BufferedReader bf = new BufferedReader(fi);
			
			while((line = bf.readLine()) != null)
			{
				if(line.length() > 0 && line != "\n")
				{
					links.add(line.split("\n")[0]);
					//System.out.println(links.get(i++));
				}	
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File Not Found");
		}
		
		//JSOUP CODE TO RETRIEVE DATA
		Document doc;
		Elements currElem;	
		Elements all;
		
		Calendar cal;
		Date now;
		Timestamp time;
		
		String url = null;
		String title = null;
		String startDate = null;
		String shortDesc = null;
		String fullDesc = null;
		String price = null;
		String crsImg = null;
		String instructor = null;
		String category = null;
        String duration = null;
        String durationTime=null;
        String durationUnit=null;
        String videoLink = null;
        String language = "english";
        String site = "https://www.udacity.com/";
        
		ArrayList<String> instructors = new ArrayList<String>();
		String []tmpAry;
		
		int i = 0; // this is outside of loop to use 'i' value later in code
		for(; i < links.size(); i++)
		{
			instructors.clear();
			
			//CONTAINS COURSE URL
			url = links.get(i);
			doc = Jsoup.connect(url).timeout(0).get(); 
			
			//RETREIVES COURSE TITLE
			title = doc.select("h1[class=course-header-title h-slim]").text();
			
			//RETREIVES COURSE FULL DESCRIPTION
			fullDesc = doc.select("div[class=pretty-format]").select("p").text();
			
			//RETREIVES COURSE SHORT DESCRIPTION
			shortDesc = doc.select("meta[name=description]").first().attr("content");
			
			//RETREIVES COURSE IMAGE URL
			crsImg = doc.select("div.container.banner-content").attr("style");
			if(crsImg.contains("url") && !crsImg.matches(".*url\\(\\)"))
				crsImg = crsImg.split("url\\(")[1].split("\\)")[0];
			else
				crsImg = "n/a";
			
			//RETREIVES COURSE INSTRUCTORS
			currElem = doc.select("div.col-xs-12.instructor-information.pull-left");
			tmpAry = currElem.select("a").text().split("([^\\u9662] ){2,}");
			for(int j = 0; j < tmpAry.length; j++)
			{
				if(tmpAry[j].matches(".*[a-zA-Z]+.*"))
				{
					instructors.add(tmpAry[j]);
				}
			}
			
			//RETREIVES COURSE DURATION
            if(doc.text().contains("expectedDuration"))
            {
                all=doc.select("script[type=text/javascript]");
                
                for (Element current : all)
                {
                    if (current.toString().contains("courseState"))
                    {
                        String[] parsed = current.toString().split(",");
                        for (String parsedElement : parsed)
                        {                            
                            if (parsedElement.contains("expectedDuration:"))
                            {
                                durationTime=parsedElement.split(":")[1].trim();                                
                            }
                            
                            if(parsedElement.contains("expectedDurationUnit:"))
                            {
                                durationUnit=parsedElement.split("'")[1].trim();                                
                                break;
                            }
                        }
                        break;
                    }
                }
            }                  
            if(!durationTime.contains("null"))
            {
                duration=durationTime + " " + durationUnit;
            }else
            {
                duration = "n/a";
            }
			
			//RETRIEVES CATEGORIES
            category = doc.select("div.row.row-gap-small").select("div.col-xs-12.text-center").select("a").text();
            if(category.length() < 1)
            	category = "n/a";
			
			//RETRIEVES VIDEO LINK
            videoLink = doc.select("div[itemprop=video]").select("meta[itemprop=embedUrl]").attr("content");
            if(videoLink.contains("^\\s+$") || videoLink.contains("v=") && videoLink.split("watch.v")[1].length() < 2)
            	videoLink = "n/a";
            
			//COLLECTS TIMESTAMP
			cal = Calendar.getInstance();
			now = cal.getTime();
			time = new Timestamp(now.getTime());
			
			
			/******************************PRINT ALL CONTENT**********************************/
			//price = doc.select("div[class=media-body]").select("strong").text();  //SCRIPT GENERATED
			//startDate = doc.select("strong[class=ng-binding]").text();			//SCRIPT GENERATED
			
			System.out.println(title);
			System.out.println("\tVideoLink:    " + videoLink);
			System.out.println("\tTimeStamp:    " + time);
			System.out.println("\tCategory:     " + category);
			System.out.println("\tDuration:     " + duration);
			System.out.println("\tDESCR:        " + fullDesc);
			System.out.println("\tSMALLDESC:    " + shortDesc);
			System.out.println("\tIMAGE URL:    " + crsImg);
			System.out.println("\tINSTRUCTORS:  " + instructors);
			
			//System.out.println("\tPRICE: " + price);								//SCRIPT GENERATED
			//System.out.println("\t" + startDate);									//SCRIPT GENERATED
			
		}
		System.out.println("\nA Total of [" + (i + 1) + "] courses");	
	}
}
