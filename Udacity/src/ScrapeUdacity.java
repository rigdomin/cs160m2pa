
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class ScrapeUdacity {

	public static void main(String[] args) throws IOException
	{
		String url = "https://www.udacity.com/public-api/v0/courses";
		Document doc = Jsoup
		        .connect(url)
		        .referrer("https://www.udacity.com/courses/all")
		        .userAgent(
		                "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
		        .ignoreContentType(true).get();
		String jsonData = doc.body().text();
		
		// Prepare timestamp for time_scraped
		Calendar cal = Calendar.getInstance();
		
		FileWriter fo1, fo2;
		fo1 = new FileWriter("src/coursedata.txt");
		fo2 = new FileWriter("src/coursedetail.txt");

		try 
		{
		    JSONObject obj = new JSONObject(jsonData);
		    JSONArray courses = obj.getJSONArray("courses");
		    
		    int course_id = 298;
		    int prof_id = 431;
		    
		    int i;
		    for (i = 0; i < courses.length(); i++) 
		    {
		    	JSONObject course = (JSONObject) courses.get(i);
		    	
		    	// VARIABL DECLARATIONS
		        JSONArray instructors = null;		    	
		        String title, short_desc, long_desc, course_link, video_link, course_image, length_units = "";
		        String category = "";
		        String university = "";
		        String start_date = "0000-00-00";
		        int course_length = 0;
		        String site = "http://www.udacity.com/";
		       	int course_fee = 0;
		       	String language = "english";
		       	String certificate = "no";
		       	java.sql.Timestamp time_scraped = null;
		       	JSONArray affiliates = course.getJSONArray("affiliates");
		       	     	
		       	// SCRAPING STATEMENTS
		       	instructors = course.getJSONArray("instructors"); 		
		        title = course.getString("title");
		        short_desc = course.getString("short_summary");
		        short_desc = short_desc.replaceAll("\\P{Print}", "");
		        short_desc = short_desc.replaceAll("\n\n", "\n");
		        short_desc = short_desc.replaceAll("\'", "");
		       	long_desc = course.getString("summary");
		       	long_desc = long_desc.replaceAll("\\P{Print}", "");
		       	long_desc = long_desc.replaceAll("\n\n", "\n");
		       	long_desc = long_desc.replaceAll("\'", "");
		       	
		       	
		        course_link = course.getString("homepage");
		        video_link = course.getJSONObject("teaser_video").getString("youtube_url");
		        length_units = course.getString("expected_duration_unit");
		        if(length_units.toLowerCase().contains("month"))
		        	course_length = course.getInt("expected_duration") * 4;
		        else
		        	course_length = course.getInt("expected_duration");
		        course_image = course.getString("image");
		        JSONArray categories = course.getJSONArray("tracks");
		        if(categories.length() > 0)
		        {
		        	category = categories.getString(0);
		        }		        
		        if(affiliates.length() > 0)
		        {
			        university = affiliates.getJSONObject(0).getString("name");
			        if(!university.toLowerCase().matches(".*(college|university|institute)+.*"))
			        	university = "";
		        }
		        time_scraped = new java.sql.Timestamp(cal.getTime().getTime());
		        String time = time_scraped.toString().split(" ")[0];
		        
		        
		    	String dataInsert1 = "INSERT INTO course_data (id, title, short_desc, long_desc, course_link, video_link, start_date, course_length, course_image, category, site, course_fee, language, certificate, university, time_scraped)";
		    	String dataInsert2 = " VALUES (" + course_id + ", '" + title + "', '" + short_desc + "', '" + long_desc + "', '" + course_link + "', '" + video_link + "', " + start_date + ", " + course_length + ", '" + course_image + "', '" + category + "', '" + site + "', " + course_fee + ", '" + language + "', '" + certificate + "', '" + university + "', " + time + ");";
		    	String detailInsert1 = "";
		    	String detailInsert2 = "";
		    	
		    	//System.out.println(dataInsert1 + dataInsert2);
		    	fo1.write(dataInsert1 + dataInsert2 + "\n");
		    	
		    	String profname, profimage = "";
		    	for(int j = 0; j < instructors.length(); j++)
		    	{
		    		profname = instructors.getJSONObject(j).getString("name");
		    		profimage = instructors.getJSONObject(j).getString("image");
			    	detailInsert1 = "\tINSERT INTO coursedetails (id, profname, profimage, course_id)";
			    	detailInsert2 = " VALUES (" + prof_id++ + ", '" + profname + "', '" + profimage + "', " + course_id + ");";
			    	//System.out.println(detailInsert1 + detailInsert2);
			    	fo2.write(detailInsert1 + detailInsert2 + "\n");
		    	}
		    	course_id++;
		    	
		    	System.out.println(short_desc + "\n");
		    	System.out.println(long_desc);
		    	
		        System.out.println("-------------------------------------------------------------------------------");
		    }
		    System.out.println("A Total of [" + i + "] Courses Scraped");
		    fo1.close();
		    fo2.close();
		}
		catch (JSONException e)
		{
			System.out.println("Cannot connect to JSON API");
			
		}
	}
}
