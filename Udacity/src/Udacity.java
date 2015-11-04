import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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
		//String url = "https://www.udacity.com/courses/all";
		String line = null;
		ArrayList<String> links = new ArrayList<String>();
		//int i = 0;
		
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
		String url = null;
		Document doc;
		Elements currElem;
		String title, startDate, shortDesc, fullDesc, price, crsImg, instructor;
		String duration = null, durationType;
		ArrayList<String> instructors = new ArrayList<String>();
		String []tmpAry;
		int i = 0;
		
		for(; i < links.size(); i++)
		{
			//clears instructors list for next page
			instructors.clear();
			
			//CONTAINS COURSE URL
			url = links.get(i);
			//System.out.println(url);
			
			doc = Jsoup.connect(url).timeout(0).get(); 
			
			//RETREIVES COURSE TITLE
			title = doc.select("h1[class=course-header-title h-slim]").text();
			
			//RETREIVES COURSE FULL DESCRIPTION
			fullDesc = doc.select("div[class=pretty-format]").select("p").text();
			
			//RETREIVES COURSE SHORT DESCRIPTION
			shortDesc = doc.select("meta[name=description]").first().attr("content");
			
			//RETREIVES COURSE IMAGE URL
			crsImg = doc.select("div.container.banner-content").attr("style");
			//System.out.println(">>>>" + crsImg);
			if(crsImg.contains("url") && !crsImg.matches(".*url\\(\\)"))
				crsImg = crsImg.split("url\\(")[1].split("\\)")[0];
			else
			{
				//System.out.println("CHECK " + doc.select("img").text());
				crsImg = "N/A";
			}
			
			//RETREIVES COURSE INSTRUCTORS
			currElem = doc.select("div.col-xs-12.instructor-information.pull-left");
			//split("\\9662 \\9662 ");
			tmpAry = currElem.select("a").text().split("([^\\u9662] ){2,}");
			for(int j = 0; j < tmpAry.length; j++)
			{
				if(tmpAry[j].matches(".*[a-zA-Z]+.*"))
				{
					//System.out.println("\tINSTRUCTOR: " + tmpAry[j]);
					instructors.add(tmpAry[j]);
				}
			}
			
			//RETREIVES COURSE DURATION
			if(doc.text().contains("expectedDuration: "))
				duration = doc.text().split("expectedDuration: ")[1];
			if(duration != null)
				System.out.println("DUR: " + duration);
			
			
			/*****************************************************************************************/
			//price = doc.select("div[class=media-body]").select("strong").text();  //SCRIPT GENERATED
			//startDate = doc.select("strong[class=ng-binding]").text();			//SCRIPT GENERATED
			
			
			System.out.println(title);
			//System.out.println("\tDESCR:       " + fullDesc);
			//System.out.println("\tSMALLDESC:   " + shortDesc);
			//System.out.println("\tIMAGE URL:   " + crsImg);
			//System.out.println("\tINSTRUCTORS: " + instructors);
			
			//System.out.println("\tPRICE: " + price);								//SCRIPT GENERATED
			//System.out.println("\t" + startDate);									//SCRIPT GENERATED
			
		}
		System.out.println("\nA Total of [" + (i + 1) + "] courses");	
	}
}
