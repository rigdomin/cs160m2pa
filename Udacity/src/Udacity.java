import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
		
		//SQL CONNECTION
		Class.forName("com.mysql.jdbc.Driver");
		final String DB_URL = "jdbc:mysql://localhost/moocs160";
		final String USER = "root";
		final String PASS = "chelley4pig";
		
		//File fo = new File("db.txt", "w");
		
		
		//JSOUP SCRAPING
		int i = 0; // this is outside of loop to use 'i' value later in code
		//for(; i < links.size(); i++)
		
		try
		{
			//System.out.println("Connecting to a selected database...");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//System.out.println("Connected database successfully...");
			Statement stmt = conn.createStatement();
			
			FileWriter fo;
			fo = new FileWriter("src/db.txt");
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
				
				//SQL POPULATION USING JDBC
				//String sql = "SELECT id,title FROM course_data";
				/*
				String sql = "INSERT INTO course_data (title, short_desc, long_desc, course_link," +
						"video_link, course_length, course_image, category, site," +
						"language, time_scrape)" +
						"VALUES ()";*/
				
				String sql1 = "INSERT INTO course_data (title, short_desc, long_desc, course_link,";
				String sql2 = "VALUES ("+"'"+title+"',"+"'"+shortDesc+"',"+"'"+fullDesc.substring(0,10)+"',"+"'"+url+"',";
				
				if(videoLink != "n/a" && videoLink != null)
				{
					//sql1 += " video_link,";
					//sql2 += "'"+videoLink+"',";
				}
				
				if(duration != "n/a" && duration != null)
				{
					sql1 += " course_length,";
					sql2 += durationTime+",";
				}
				
				if(crsImg != "n/a" && crsImg != null)
				{
					sql1 += " course_image,";
					sql2 += "'http:"+crsImg+"',";
				}
				
				if(category != "n/a" && category != null)
				{
					sql1 += " category,";
					sql2 += "'"+category+"',";
				}
				
				sql1 += " site,";
				sql2 += "'"+site+"',";
				
				sql1 += " language) ";
				sql2 += "'"+language+"');";
			
				/*
				sql1 += " time_scrape) ";
				sql2 += "NOW())";*/
				
				System.out.println("MySQL STatement:\n" + sql1 + sql2);
				//System.out.println("Inserting records into the table...");
				//stmt.executeUpdate("INSERT INTO course_data(title) VALUES('test3')");
				//System.out.println("Inserted records into the table...");
				//stmt.executeUpdate(sql1 + sql2);
				
				/*ResultSet rs = stmt.executeQuery("SELECT id,title FROM course_data");
				while(rs.next()){
					System.out.println(rs.getInt("id") + ")" + rs.getString("title"));
				}
				rs.close();*/
				
				fo.write(sql1 + sql2 + "\n");
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
			stmt.close();
			conn.close();
			fo.close();
		}catch(SQLException e)
		{
			System.out.println("Could not connect");
		}
		System.out.println("\nA Total of [" + i + "] courses");
	}
}
