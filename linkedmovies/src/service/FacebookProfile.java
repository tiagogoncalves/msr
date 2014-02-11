package service;
import org.json.*;
import java.util.ArrayList;

public class FacebookProfile 
{
	private JSONObject profile;
	
	FacebookProfile(JSONObject profile)
	{
		this.profile = profile;
	}
	
	//Pegar um campo personalizado
	public Object getFieldObj(String field)
	{
		if(this.profile.has(field))
			return this.profile.get(field);
		else
			return null;
	}
	
	//Pegar um array dos dados comuns 
	public ArrayList getUser()
	{
		ArrayList<String> user = new ArrayList<String>();
		user.add(this.profile.getString("first_name")+" "+this.profile.getString("last_name"));
		user.add(this.profile.getString("birthday"));
		user.add(this.profile.getString("gender"));
		user.add(this.profile.getJSONObject("hometown").getString("name"));
		user.add(this.profile.getString("religion"));
		
		return user;
	}
	
	public ArrayList getUserMovies()
	{
		ArrayList<String> movies = new ArrayList<String>();
		
		if(this.profile.has("movies"))
		{
			JSONArray jArr = this.profile.getJSONObject("movies").getJSONArray("data");
			JSONObject jObj;
			for(int i=0; i < jArr.length(); i++)
			{
				jObj = (JSONObject) jArr.get(i);
				movies.add((String)jObj.getString("name"));
			}
		}
		return movies;
	}
	
	public ArrayList getUserLikes()
	{
		ArrayList<String> likes = new ArrayList<String>();
		
		if(this.profile.has("likes"))
		{
			JSONArray jArr = this.profile.getJSONObject("likes").getJSONArray("data");
			JSONObject jObj;
			for(int i=0; i < jArr.length(); i++)
			{
				jObj = (JSONObject) jArr.get(i);
				likes.add((String)jObj.getString("name"));
			}
		}
		return likes;
	}
	
	public ArrayList getUserSubscribedto()
	{
		ArrayList<String> subscribedto = new ArrayList<String>();
		
		if(this.profile.has("subscribedto"))
		{
			JSONArray jArr = this.profile.getJSONObject("subscribedto").getJSONArray("data");
			JSONObject jObj;
			for(int i=0; i < jArr.length(); i++)
			{
				jObj = (JSONObject) jArr.get(i);
				subscribedto.add((String)jObj.getString("name"));
			}
		}
		return subscribedto;
	}
	
}
