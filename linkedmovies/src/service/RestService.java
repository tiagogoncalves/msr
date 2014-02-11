package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;


/**
 * Servlet implementation class RestService
 */
@WebServlet("/RestService")
public class RestService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    try {
	    	
	        String json_str = request.getParameter("profile");
	        if(!json_str.isEmpty())
	        {
		        JSONObject jObj = new JSONObject(json_str);
		        FacebookProfile profile = new FacebookProfile(jObj);
		        
		        //Só pra testar
		        System.out.println(profile.getUser().toString());
		        System.out.println(profile.getUserMovies().toString());
		        System.out.println(profile.getUserLikes().toString());
		        System.out.println(profile.getUserSubscribedto().toString());
	        }
	    } 
	    catch (Exception ex) {
	        out.println("failed");
	    }
	}

}
