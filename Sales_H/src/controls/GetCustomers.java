package controls;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dao.Database;
import model.ProjectManager;
import dto.MessageObjects;
import dto.CustomerObjetcs;

@WebServlet("/GetCustomers")
public class GetCustomers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetCustomers() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try
		{
			Database database= new Database();
			ProjectManager projectManager= new ProjectManager();
			//ArrayList messagesData =null;
			String para_ = request.getParameter("retrieved"); 
			List<CustomerObjetcs> myDataList = new ArrayList<CustomerObjetcs>();
			
			Connection connection = database.Get_Connection();
			myDataList = projectManager.GetCustomers(connection, request, response);
			int itemCount = myDataList.size();
			Gson gson = new Gson();
			String messages = gson.toJson(myDataList);
			String jackson_ = "{\"data\":[{\"count\" :"+"\""+itemCount+"\""+",\"json\":"+messages+"}]}";
			out.println(jackson_);
			
			//out.println("{\"Customers\":"+messages+"}");
		}
		catch (Exception ex)
		{
			out.println("Error: " + ex.getMessage());
		}
		finally
		{
			out.close();
		}
	}
}