package controls;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dao.Database;
import dto.MessageObjects;
import model.ProjectManager;


@WebServlet("/GetRenglon")
public class GetRenglon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
      public GetRenglon() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//	String codigoMen = request.getParameter("codigoMen");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String message_id2 = request.getParameter("message_id2");
		try
		{
			Database database= new Database();
			ProjectManager projectManager= new ProjectManager();
			MessageObjects messagesData = null;
			Connection connection = database.Get_Connection();
			messagesData = projectManager.GetRenglon(connection, request, response);
			Gson gson = new Gson();
			String messages = gson.toJson(messagesData);
			out.println("{\"Renglon\":"+messages+"}");
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
