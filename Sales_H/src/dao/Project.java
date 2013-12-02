package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dto.CustomerObjetcs;
import dto.MessageObjects;




public class Project {
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	
	
	public Boolean CheckUser (Connection connection,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String account_number = null;
		Boolean exist = false;
		
		try {
			account_number = request.getParameter("account_number");
			PreparedStatement ps2 = connection
			.prepareStatement("SET lc_time_names = 'es_MX';");
			
			ps2.executeQuery();

			PreparedStatement ps = connection
					.prepareStatement("SELECT account_number FROM customers WHERE account_number = ?");
			ps.setString(1, account_number);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				exist = true;
			}

			return exist;

		} catch (Exception e) {
			throw e;
		}
	}
	
	

	public String InsertMessage(Connection connection,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String message = null;
		String fecha_01 = now();

		try {
			message = request.getParameter("message");

			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO messages(message,fecha) VALUES(?,?)");
			ps.setString(1, message);
			ps.setString(2, fecha_01);

			int rs = ps.executeUpdate();
			if (rs > 0) {
				return message;
			} else {
				return null;
			}

		}

		catch (Exception e) {

			throw e;
		}
	}

	public boolean InsertCustomer(Connection connection,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//List<MessageObjects> myDataList = new ArrayList<MessageObjects>();
		boolean executed;
		
		String fecha_01 = now();

		String user_data = request.getParameter("data");
		
		//JSONObject jsonDataObject = (JSONObject) JSONValue.parse(json);
		//CustomerObjetcs customer  = null;
		
		Gson gson = new Gson();
		CustomerObjetcs customer = gson.fromJson(user_data, CustomerObjetcs.class); 
		
		

		
		boolean taxableon = customer.getTaxable();

		try {

			PreparedStatement ps = connection
			.prepareStatement("INSERT INTO people(first_name,last_name,middle_name,email,rfc,phone_number,cellphone,address_1,city,state,zip,country,comments,fecha_alta) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			PreparedStatement ps2 = connection.prepareStatement("");

			ps.setString(1,  (customer.getFirst_name()));
			ps.setString(2,  (customer.getLast_name()));
			ps.setString(3,  (customer.getMiddle_name()));
			ps.setString(4,  (customer.getEmail()));
			ps.setString(5,  (customer.getRfc()));
			ps.setString(6,  (customer.getPhone_number()));
			ps.setString(7,  (customer.getCellphone()));
			ps.setString(8,  (customer.getAddress_1()));
			ps.setString(9,  (customer.getCity()));
			ps.setString(10, (customer.getState()));
			ps.setString(11, (customer.getZip()));
			ps.setString(12, (customer.getCountry()));
			ps.setString(13, (customer.getComments()));
			ps.setString(14, (fecha_01));
		
		    String check_p_moral = (customer.getRfc());
		    int count_p_moral = 0;
		    boolean  p_moral = false;
		    
		    for ( int i = 0; i < 4; i++ ) {  
		    	 char c = check_p_moral.charAt( i );  
		         if (Character.isLetter(c)){  
		        	 count_p_moral++;
		         }  
		    }  
		    
		    if (count_p_moral == 3) {p_moral = true; }
		    

				ps2 = connection
				.prepareStatement("INSERT INTO customers(person_id,account_number,company_name,taxable,p_moral) VALUES(LAST_INSERT_ID(),?,?,?,?)");

				ps2.setString( 1, (customer.getAccount_number()));
				ps2.setString( 2, (customer.getCompany_name()));
				ps2.setBoolean(3, (customer.getTaxable()));
				ps2.setBoolean(4, p_moral);
				

			int rs = ps.executeUpdate();
			int rs2 = ps2.executeUpdate();

			if (rs > 0 && rs2 > 0) {

				executed = true;

			} else {
				executed = false;
			}

		}

		catch (Exception e) {

			throw e;
		}
		
		return executed;
	}

	public MessageObjects GetRenglon(Connection connection,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// ArrayList messageData = new ArrayList();
		MessageObjects messageObject = new MessageObjects();
		String message_id = null;
		try {
			message_id = request.getParameter("message_id2");
			PreparedStatement ps2 = connection
			.prepareStatement("SET lc_time_names = 'es_MX';");
			ps2.executeQuery();

			PreparedStatement ps = connection
			.prepareStatement("SELECT msg_id,message,fecha, DATE_FORMAT(fecha,'%M %d, %Y') AS fechaformatiada FROM messages WHERE msg_id = ?");
			ps.setString(1, message_id);

			ResultSet rs = ps.executeQuery();
			// String temp = rs.getString("msg_id");

			while (rs.next()) {

				messageObject.setMsg_id(rs.getString("msg_id"));
				messageObject.setMessage(rs.getString("message"));
				messageObject.setFecha(rs.getString("fechaformatiada"));
			}

			// message_id = messageObject;

			return messageObject;

		} catch (Exception e) {
			throw e;
		}
	}

	public ArrayList GetMessages(Connection connection,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ArrayList messageData = new ArrayList();
		try {
			PreparedStatement ps2 = connection
			.prepareStatement("SET lc_time_names = 'es_MX';");
			ps2.executeQuery();

			PreparedStatement ps = connection
			.prepareStatement("SELECT msg_id,message,fecha,(DATE_FORMAT(fecha,'%M %d del %Y | %H:%i:%S hrs ')) AS fechaformatiada FROM messages WHERE status = 1 ORDER BY msg_id DESC");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MessageObjects messageObject = new MessageObjects();
				messageObject.setMsg_id(rs.getString("msg_id"));
				messageObject.setMessage(rs.getString("message"));
				messageObject.setFecha(rs.getString("fechaformatiada"));

				messageData.add(messageObject);
			}
			return messageData;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<CustomerObjetcs> GetCustomers(Connection connection,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		List<CustomerObjetcs> customerData = new ArrayList<CustomerObjetcs>();
		int count = 0;
		
		try {
			
			PreparedStatement ps2 = connection
			.prepareStatement("SET lc_time_names = 'es_MX';");
			ps2.executeQuery();
			
			PreparedStatement psCount = connection
			.prepareStatement("SELECT COUNT(*) FROM ( SELECT people.person_id , customers.account_number FROM people JOIN customers USING ( person_id ) LIMIT 50) AS COUNTER");
			psCount.executeQuery();
			ResultSet rs2 = psCount.executeQuery();
			
			while (rs2.next()) {
			count = rs2.getInt(1);
			}
			
					
			PreparedStatement ps = connection	
			.prepareStatement("SELECT people .* , customers.account_number, customers.company_name, customers.taxable, customers.p_moral, (DATE_FORMAT( fecha_alta,  '%M %d del %Y | %H:%i:%S hrs ' )) AS fechaformatiada FROM people JOIN customers USING ( person_id ) where customers.deleted = 0 ORDER BY last_name ASC LIMIT 150");
			
			
					
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				CustomerObjetcs customerObject = new CustomerObjetcs();
				
				customerObject.setFirst_name(rs.getString("first_name"));
				customerObject.setLast_name(rs.getString("last_name"));
				customerObject.setMiddle_name(rs.getString("middle_name"));
				customerObject.setEmail(rs.getString("email"));
				customerObject.setRfc(rs.getString("rfc"));
				customerObject.setPhone_number(rs.getString("phone_number"));
				customerObject.setCellphone(rs.getString("cellphone"));
				customerObject.setAddress_1(rs.getString("address_1"));
				customerObject.setCity(rs.getString("city"));
				customerObject.setState(rs.getString("state"));
				customerObject.setZip(rs.getString("zip"));
				customerObject.setCountry(rs.getString("country"));
				customerObject.setComments(rs.getString("comments"));
				customerObject.setFecha_alta(rs.getString("fechaformatiada"));
				customerObject.setCompany_name(rs.getString("company_name"));
				customerObject.setAccount_number(rs.getString("account_number"));
				customerObject.setTaxable(rs.getBoolean("taxable"));
				customerObject.setP_moral(rs.getBoolean("p_moral"));
				customerObject.setPerson_id(rs.getInt("person_id"));
				
				customerData.add(customerObject);
			}
			
			return customerData;
			
		} catch (Exception e) {	
			throw e;
		}
	}
	
	public Boolean CustomerUpdate(Connection connection,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//List<MessageObjects> myDataList = new ArrayList<MessageObjects>();
		
		boolean success = true;
		
		//String fecha_01 = now(); //Por si Quiero Guardar Fecha de Modificacion
		
		
		//JSONObject jsonDataObject = (JSONObject) JSONValue.parse(json);
		//JSONObject jsonDataObject = (JSONObject) JSONSerializer.toJSON(json); 
		//CustomerObjetcs customer  = (CustomerObjetcs) JSONObject.toBean(jsonDataObject, CustomerObjetcs.class);
		
		String json = request.getParameter("data");
		Gson gson = new Gson();
		CustomerObjetcs customer = gson.fromJson(json, CustomerObjetcs.class); 
		
		//String taxableon = (customer.getTaxable());;
		//boolean taxable = false;
		
		//if (taxableon != null) {taxable = true;}

		try {

			PreparedStatement ps = connection
			.prepareStatement("UPDATE people SET first_name = ?,last_name = ?,middle_name = ?,email = ?,rfc = ?,phone_number = ?,cellphone = ?,address_1 = ?,city = ?,state = ?,zip = ?,country = ?, comments = ? where person_id = ? ");
			
			ps.setString(1,  (customer.getFirst_name()));
			ps.setString(2,  (customer.getLast_name()));
			ps.setString(3,  (customer.getMiddle_name()));
			ps.setString(4,  (customer.getEmail()));
			ps.setString(5,  (customer.getRfc()));
			ps.setString(6,  (customer.getPhone_number()));
			ps.setString(7,  (customer.getCellphone()));
			ps.setString(8,  (customer.getAddress_1()));
			ps.setString(9,  (customer.getCity()));
			ps.setString(10, (customer.getState()));
			ps.setString(11, (customer.getZip()));
			ps.setString(12, (customer.getCountry()));
			ps.setString(13, (customer.getComments()));
			ps.setInt(14,    (customer.getPerson_id()));

			
			
			String check_p_moral = (customer.getRfc());
		    
			int count_p_moral = 0;
		    boolean  p_moral = false;
		    
		    for ( int i = 0; i < 4; i++ ) {  
		    	 char c = check_p_moral.charAt( i );  
		         if (Character.isLetter(c)){  
		        	 count_p_moral++;
		         }  
		    }  
		    
		    if (count_p_moral == 3) {p_moral = true; }
		    

		    PreparedStatement ps2 = connection.prepareStatement("");
			ps2 = connection
			.prepareStatement("UPDATE customers SET account_number = ?,company_name = ?,taxable = ?, p_moral = ? WHERE person_id = ? ");
			
			ps2.setString(1,  (customer.getAccount_number()));
			ps2.setString(2,  (customer.getCompany_name()));
			ps2.setBoolean(3, (customer.getTaxable()));
			ps2.setBoolean(4,  (p_moral));
			ps2.setInt(5,     (customer.getPerson_id()));


			int rs = ps.executeUpdate();
			int rs2 = ps2.executeUpdate();

			if (rs > 0 && rs2 > 0) {
			
				return success = true;

			} else {
				
				return success = false;
			}

		}

		catch (Exception e) {

			throw e;
		}
	}	
	
}
