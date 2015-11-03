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
		
		String url = null;
		Document doc;
		String title, startDate, fullDesc;
		int i = 0;
		
		for(; i < links.size(); i++)
		{
			url = links.get(i);
			//System.out.println(url);
			doc = Jsoup.connect(url).timeout(0).get(); 
			

			title = doc.select("h1[class=course-header-title h-slim]").text();
			fullDesc = doc.select("div[class=pretty-format]").select("p").text();
			//startDate = doc.select("strong[class=ng-binding]").text();
			System.out.println(title);
			System.out.println("\t" + fullDesc.substring(0, 19));
			//System.out.println("\t" + startDate);
			
		}
		System.out.println("\nA Total of [" + (i + 1) + "] courses");
		/*
		Document doc = Jsoup.connect(url).get();
		
		Elements ele = doc.select("div[class=col-xs-9 course-list]");
		Elements title = ele.select("h2");
		Elements link = ele.select("div[href]");
		
		System.out.println("URL: " + url);
		//System.out.println(doc);
		System.out.println("Current Content\nTitle: " + title + "\nLink: " + link);
		*/
	}
}
