package controls;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;

import model.ProjectManager;
import dao.Database;
import dto.MessageObjects;

/**
 * Servlet implementation class InsertMessage
 */
@WebServlet("/InsertCustomer")
public class InsertCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter(); 
		out.println("Conectado con el Servlet!");
		   
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null) {  
			   ipAddress = request.getRemoteAddr();  
		   }
		   
		String prueba = null;
		System.out.println(ipAddress);
				
		String json = request.getParameter("data");
		JSONObject jsonDataObject = (JSONObject) JSONValue.parse(json);
		prueba = ((String)jsonDataObject.get("country"));
		out.println(prueba);
		
		try
		{
			
			Database database= new Database();
			ProjectManager projectManager= new ProjectManager();
			//String message = null;
			List<MessageObjects> InsertPeople = new ArrayList<MessageObjects>();; 
			Connection connection = database.Get_Connection();
			InsertPeople = projectManager.InsertCustomer(connection, request, response);
			if (InsertPeople!=null)
			{
				out.println("Exito!");
			}
			else
			{
				out.println("false");
			} 
		}
		catch (Exception ex)
		{
			out.println("Error Aqui: " + ex.getMessage());
		}
		finally
		{
			out.close();
		}
	}

}