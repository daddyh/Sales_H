package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.Project;
import dto.MessageObjects;
import dto.CustomerObjetcs;

public class ProjectManager
{
	
	public Boolean CheckUser(Connection connection, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String account_number = null;
		Boolean exist = false;
		try {
			account_number = request.getParameter("account_number");
			if(account_number != null && account_number !="" && account_number.length()>0)
			{
				Project project= new Project();
				exist=project.CheckUser(connection, request, response);
			}
		} catch (Exception e) {
			throw e;
		}
		return exist;
	}
	
	public Boolean CustomerUpdate(Connection connection, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String account_number = null;
		boolean sucess = false;
		try {
			//account_number = request.getParameter("account_number");
			//if(account_number != null && account_number !="" && account_number.length()>0)
			//{
				Project project= new Project();
				sucess=project.CustomerUpdate(connection, request, response);
			//}
		} catch (Exception e) {
			throw e;
		}
		return sucess;
	}
	

	public String InsertMessage(Connection connection, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = null;
		try {
			message = request.getParameter("message");
			if(message != null && message !="" && message.length()>0)
			{
				Project project= new Project();
				message=project.InsertMessage(connection, request, response);
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	public ArrayList GetMessages(Connection connection, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ArrayList messages = null;
		try {
			// Here you can validate before connecting DAO class, eg. User session condition
			Project project= new Project();
			messages=project.GetMessages(connection, request, response);
		} 
		catch (Exception e) {
			throw e;
		}
		return messages;
	}

	public MessageObjects GetRenglon(Connection connection, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MessageObjects message_id = null;
		String message_id2 = null;
		try {
			message_id2 = request.getParameter("message_id2");
			if(message_id2 != null && message_id2 !="" && message_id2.length()>0)
			{
				Project project= new Project();
				message_id=project.GetRenglon(connection, request, response);
			}
		} catch (Exception e) {
			throw e;
		}
		return message_id;
	}
	
	public List<MessageObjects> InsertCustomer(Connection connection, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<MessageObjects> myDataList = new ArrayList<MessageObjects>();
		
		Boolean status;
		
		try {
				Project project= new Project();
				status=project.InsertCustomer(connection, request, response);

		} catch (Exception e) {
			throw e;
		}
		return myDataList;
	}
	
	public List<CustomerObjetcs> GetCustomers(Connection connection, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<CustomerObjetcs> customers = new ArrayList<CustomerObjetcs>();
		
		try {
			// Here you can validate before connecting DAO class, eg. User session condition
			Project project= new Project();
			customers=project.GetCustomers(connection, request, response);
		} 
		catch (Exception e) {
			throw e;
		}
		return customers;
	}



	
}
