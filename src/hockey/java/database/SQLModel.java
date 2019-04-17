package hockey.java.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hockey.java.packet.PacketReturn;

public class SQLModel {
	//private boolean singlePlayerDebug = true;
	
	Connection connection = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String query = "";
	
	public SQLModel() {
		//if(singlePlayerDebug) return;
		connection = SQLConnection.Connector();
		if(connection == null) {
			System.out.println("connection failed");
			System.exit(1);
		} else {
			System.out.println("Server connected to DB!");
		}
		
	}
	
	public boolean isDbConnected() {
		//if(singlePlayerDebug) return true;
		try{
			return !(connection.isClosed());		
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public PacketReturn checkSignUp(String username, String pw, String cpw) {
		PacketReturn p = new PacketReturn();
		boolean check = true;
		
		//if(singlePlayerDebug) return true;
		if(username == null || pw == null || cpw == null) { //they shouldn't be empty
			check = false;
		}
		else if(!pw.equals(cpw)){ //password and confirm password should be the same
			check = false;
		}
		else {
			try {
				ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
				ps.setString(1, username);
				rs = ps.executeQuery();	
				while(rs.next()) {
					if(rs.getString(1)!=null) { //username already taken
			            check = false; 
					}
				}
				//no error, update new player in database
				ps = connection.prepareStatement("INSERT INTO Player (username, password) VALUES (?, ?)");
				ps.setString(1, username);
				ps.setString(2, pw);
				ps.executeUpdate();
				
				p.username = username;
				p.id = rs.getInt(1);
				
				check = true;
			}
			catch(SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
				check = false;
			} finally {
				try {
					if(rs != null) {rs.close();}
					if(ps!=null) {ps.close();}
				}catch(SQLException sqle) {
					System.out.println("sqle closing stuff: " + sqle.getMessage());
				}		
			} 
		}
		
		if(check) {
			p.status = 1;			
		}else {
			p.status = 2;
		}
		
		return p;
	}
	
	public PacketReturn checkLogin(String username, String pw) {
		PacketReturn p = new PacketReturn();		
		boolean check = true;
		
		if(username == null || pw == null) { //they shouldn't be empty
			check = false;
		}
		else {
			try {
				ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
				ps.setString(1, username);
				rs = ps.executeQuery();
				if(!rs.next()) {
		            check = false; //user does not exist
				}
				else {
					p.username = username;
					p.id = rs.getInt(1);
					
					String dbpw = rs.getString(3);						
					if(!dbpw.equals(pw)) { //once found this name exists, look at pw in database
			            check = false; //password does not match with database			
					}
				}
				check = true;				
			} catch (SQLException e) {
				System.out.println("sqle: " + e.getMessage());
				check = false;
			}		
		}
		//if user exist, id, username, status all not null
		//if user doesnt exist, only status not null
		
		if(check) {
			p.status = 3;			
		}else {
			p.status = 4;
		}
		return p;
	}
}
