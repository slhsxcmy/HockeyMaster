package hockey.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLModel {
	private boolean singlePlayerDebug = true;
	
	Connection connection = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String query = "";
	
	public SQLModel() {
		if(singlePlayerDebug) return;
		connection = SQLConnection.Connector();
		if(connection == null) {
			System.out.println("connection failed");
			System.exit(1);
		}
		
	}
	
	public boolean isDbConnected() {
		if(singlePlayerDebug) return true;
		try{
			return !(connection.isClosed());		
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkSignUp(String username, String pw, String cpw) {
		if(singlePlayerDebug) return true;
		if(username == null || pw == null || cpw == null) { //they shouldn't be empty
			return false;
		}
		else if(!pw.equals(cpw)){ //password and confirm password should be the same
			return false;
		}
		else {
			try {
				ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
				ps.setString(1, username);
				rs = ps.executeQuery();	
				while(rs.next()) {
					if(rs.getString(1)!=null) { //username already taken
			            return false; 
					}
				}
				//no error, update new player in database
				ps = connection.prepareStatement("INSERT INTO Player (username, password) VALUES (?, ?)");
				ps.setString(1, username);
				ps.setString(2, pw);
				ps.executeUpdate();
				return true;
			}
			catch(SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
				return false;
			} finally {
				try {
					if(rs != null) {rs.close();}
					if(ps!=null) {ps.close();}
				}catch(SQLException sqle) {
					System.out.println("sqle closing stuff: " + sqle.getMessage());
				}		
			} 
		}
	}
	
	public boolean checkLogin(String username, String pw) {
		if(username == null || pw == null) { //they shouldn't be empty
			return false;
		}
		else {
			try {
				ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
				ps.setString(1, username);
				rs = ps.executeQuery();
				if(!rs.next()) {
		            return false; //user does not exist
				}
				else {
					String dbpw = rs.getString(3);				
					if(!dbpw.equals(pw)) { //once found this name exists, look at pw in database
			            return false; //password does not match with database			
					}
				}
				return true;
			} catch (SQLException e) {
				System.out.println("sqle: " + e.getMessage());
				return false;
			}
				
		}
	}
}
