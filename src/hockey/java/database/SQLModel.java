package hockey.java.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import hockey.java.Hockey;
import hockey.java.Master;
import hockey.java.front.Game;
import hockey.java.front.User;
import hockey.java.packet.Constants;
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
		
		if(username == null || username.equals("") || pw == null || pw.equals("") || cpw == null || cpw.equals("")) { //they shouldn't be empty
			//check = false;
			System.out.println("username or password is empty");
			return new PacketReturn(Constants.SIGNUPFAILURE, "username or password is empty");
		}
		else if(!pw.equals(cpw)){ //password and confirm password should be the same
			//check = false;
			System.out.println("confirm password is different");
			return new PacketReturn(Constants.SIGNUPFAILURE, "confirm password is different");
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
					return new PacketReturn(Constants.SIGNUPFAILURE, "username is already taken");
				} else {
					System.out.println("inserting user to db");
					//no error, update new player in database
					ps = connection.prepareStatement("INSERT INTO Player (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, username);
					ps.setString(2, pw);
					ps.executeUpdate();
					
					//ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
					//ps.setString(1, username);
					//ps.executeUpdate();
					rs = ps.getGeneratedKeys();	
					int id = -1;
					if(rs.next()) {
						id = (int)rs.getLong(1);
					}
					
					
					User tmp = new User(id);
					tmp.setUsername(username);
					//System.out.println("After setUser(), Hockey.getUser().getUsername() is "+ Hockey.getUser().getUsername());
					Master.getMap().put(id, tmp);
					return new PacketReturn(Constants.LOGINSUCCESS, id, username);
				}
			}
			catch(SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
				//check = false;
				return new PacketReturn(Constants.SIGNUPFAILURE, "SQL error.");
			} finally {
				try {
					if(rs != null) {rs.close();}
					if(ps != null) {ps.close();}
				}catch(SQLException sqle) {
					System.out.println("sqle closing stuff: " + sqle.getMessage());
				}		
			} 
		}
		
	}
	
	public PacketReturn checkLogin(String username, String pw) {
		
		if(username == null || username == "" || pw == null || pw == "") { //they shouldn't be empty
			return new PacketReturn(Constants.LOGINFAILURE, "username or password is empty");
		}
		else {
			try {
				ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
				ps.setString(1, username);
				rs = ps.executeQuery();
				if(!rs.next()) {
		            //check = false; //user does not exist
					return new PacketReturn(Constants.LOGINFAILURE, "user does not exist");
				}
				else {
					//p.username = username;
					//p.id = rs.getInt(1);
					
					String dbpw = rs.getString(3);						
					if(!dbpw.equals(pw)) { //once found this name exists, look at pw in database
			            //check = false; //password does not match with database			
						return new PacketReturn(Constants.LOGINFAILURE, "password is incorrect");
					}
				}
				//check = true;
				//p.status = 3;
				User tmp = new User(rs.getInt(1));
				tmp.setUsername(username);
				//Hockey.setUser(tmp);
				Master.getMap().put(rs.getInt(1), tmp); //create a new user and put it in map
				return new PacketReturn(Constants.LOGINSUCCESS, rs.getInt(1), username);
			} catch (SQLException e) {
				System.out.println("sqle: " + e.getMessage());
				//check = false;
				return new PacketReturn(Constants.LOGINFAILURE, "SQL error.");
			}		
		}		
	}
	
	public PacketReturn signAsGuest() {
		try {
			System.out.println("trying to insert a guest");
			
			ps = connection.prepareStatement("INSERT INTO Player (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);			
			ps.setString(1, "GUEST");
			ps.setString(2, "GUEST");
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			int id = -1;
			if(rs.next()) {
				id = (int)rs.getLong(1);
			}
			//rs = ps.executeQuery();
			System.out.println("insertion done");
			System.out.println("guest id is " + id);
			
			User tmp = new User(id);
			tmp.setUsername("GUEST");
			//Hockey.setUser(tmp);
			Master.getMap().put(id, tmp); //put it in online users map
			return checkList(id);			
			
		}catch (SQLException e) {
			System.out.println("sqle: " + e.getMessage());
			return new PacketReturn(Constants.SIGNUPFAILURE, "SQL error.");
		}		
	}
	
	public PacketReturn loggedPlay(String username) {
		int id = -1;
		try {
			ps = connection.prepareStatement("SELECT * FROM Player WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getInt(1);
				System.out.println("user "+username +" has id: "+id);
			}
		}catch (SQLException e) {
			System.out.println("sqle: " + e.getMessage());
		}
		return checkList(id);
	}
	
	public PacketReturn checkList(int id) { //process all online users
		String username = "";
		try {
			ps = connection.prepareStatement("SELECT * FROM Player WHERE playerID=?");
			ps.setString(1, String.valueOf(id));
			rs = ps.executeQuery();
			if(rs.next()) {
				username = rs.getString(2);
			}
		}catch (SQLException e) {
			System.out.println("sqle: " + e.getMessage());
		}
		
		if(Master.getPlayerlist().size() == 0) {
			Master.getPlayerlist().add(id);
			return new PacketReturn(Constants.PLAYFAILURE, id, "Not Enough Players. Please Wait.");
		}
		else if(Master.getPlayerlist().size() == 1) {
			Master.getPlayerlist().add(id);			
			return new PacketReturn(Constants.PLAYSUCCESS, id, username);
		}
		else{
			Master.getWaitlist().add(id);
			return new PacketReturn(Constants.PLAYFAILURE, id, "Game already in progress. Please Wait.");
		}			
	}
}
