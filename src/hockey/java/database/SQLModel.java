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
		//PacketReturn p = new PacketReturn();
		//p.status = 2;
		//boolean check = true;
		
		//if(singlePlayerDebug) return true;
		if(username == null || username.equals("") || pw == null || pw.equals("") || cpw == null || cpw.equals("")) { //they shouldn't be empty
			//check = false;
			System.out.println("username or password is empty");
			return new PacketReturn(2, "username or password is empty");
		}
		else if(!pw.equals(cpw)){ //password and confirm password should be the same
			//check = false;
			System.out.println("confirm password is different");
			return new PacketReturn(2, "confirm password is different");
		}
		else {
			try {
				System.out.println("testing user existence");
				ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
				ps.setString(1, username);
				rs = ps.executeQuery();	
				if(rs.next()) {
				    //check = false; 
				    System.out.println("username is already taken");
					return new PacketReturn(2, "username is already taken");
				} else {
					System.out.println("inserting user to db");
					//no error, update new player in database
					ps = connection.prepareStatement("INSERT INTO Player (username, password) VALUES (?, ?)");
					ps.setString(1, username);
					ps.setString(2, pw);
					ps.executeUpdate();
					
					ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
					ps.setString(1, username);
					rs = ps.executeQuery();	
					
					rs.next();
					return new PacketReturn(1, rs.getInt(1), username);
				}
			}
			catch(SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
				//check = false;
				return new PacketReturn(2, "SQL error.");
			} finally {
				try {
					if(rs != null) {rs.close();}
					if(ps != null) {ps.close();}
				}catch(SQLException sqle) {
					System.out.println("sqle closing stuff: " + sqle.getMessage());
				}		
			} 
		}
		
		//p.status = check ? 1 : 2;
		//return p;
	}
	
	public PacketReturn checkLogin(String username, String pw) {
		//PacketReturn p = new PacketReturn();	
		//p.status = 4;
		//boolean check = true;
		
		if(username == null || username == "" || pw == null || pw == "") { //they shouldn't be empty
			//check = false;
			return new PacketReturn(4, "username or password is empty");
		}
		else {
			try {
				ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
				ps.setString(1, username);
				rs = ps.executeQuery();
				if(!rs.next()) {
		            //check = false; //user does not exist
					return new PacketReturn(4, "user does not exist");
				}
				else {
					//p.username = username;
					//p.id = rs.getInt(1);
					
					String dbpw = rs.getString(3);						
					if(!dbpw.equals(pw)) { //once found this name exists, look at pw in database
			            //check = false; //password does not match with database			
						return new PacketReturn(4, "password is incorrect");
					}
				}
				//check = true;
				//p.status = 3;		
				return new PacketReturn(3, rs.getInt(1), username);
			} catch (SQLException e) {
				System.out.println("sqle: " + e.getMessage());
				//check = false;
				return new PacketReturn(4, "SQL error.");
			}		
		}
		//if user exist, id, username, status all not null
		//if user doesnt exist, only status not null
		
		
	}
}
