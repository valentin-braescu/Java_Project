/**
 * 
 */
package Server;

import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JFrame;

/**
 * @author Sebastien
 *
 */
public class SingleServer extends JFrame {

	private Listener listener;
	private Collection<Worker> colWorker;
	private Connection con;
	private ServerGUI gui;
	
	//Create an object of SingleServer
	private static SingleServer instance = new SingleServer();
	/**
	 * @param args
	 */
	
	//Make the constructor private so that this class cannot be instanciated
	private SingleServer() {
		// Server interface
		colWorker = new LinkedList<Worker>();
		setTitle("Server");
		gui = new ServerGUI(this);
		setContentPane(gui);
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//Get the only object available
	public static SingleServer getInstance() {
		return instance;
	}
	
	public void startServer() {
		System.out.println("[+] Starting server ...");
		//Connect to the database
		con = null;
	    String url = "jdbc:mysql://localhost:3306/";
	    String db = "bddjava";
	    String driver = "com.mysql.jdbc.Driver";
	    String user = "root";
	    String pass = "root";
	    try{
		    Class.forName(driver);
		    con = DriverManager.getConnection(url+db, user, pass);
	    } catch (SQLException s){
	    	System.out.println("Fail to connect to the database.");
	    } catch(ClassNotFoundException e) {
	    	System.out.println("Connot find the driver in the classpath !");
	    	
	    } catch (Exception e){
	    	e.printStackTrace();
	    }
	    //Run listener
		listener = new Listener(this);
	}
	
	public void addWorker(Worker worker) {
		colWorker.add(worker);
	}
	
	public void delWorker(Worker worker) {
		colWorker.remove(worker);
		listener.removeUser();
	}
	
	public boolean checkLoginPass(String data) {
		String[] parts = data.split("\t");
		String login = parts[0];
		String pass = parts[1];
		PreparedStatement prepst;
		String query;
		prepst = null;
		query = "";
		boolean exist = false;
		// Check if the user already exists in the database
		
		// mysql SELECT prepared statement
		query = "SELECT COUNT(login) as account FROM users WHERE login=? AND password=?";
		// create mysql SELECT prepared Statement
		try {
			prepst = con.prepareStatement(query);
			prepst.setString(1,login);
			prepst.setString(2,pass);
			// execute the prepared Statement
			ResultSet res = prepst.executeQuery();
			// Get the first line
			res.next();
			if(res.getInt("account") == 1) {
				// The user exists in the database
				exist = true;
			}
			else {
				// The user doesn't exist in the database
				exist = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Closing the Statement
		try {
			prepst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}
	
	public boolean creaCompte(String data) {
		String[] parts = data.split("\t");
		String login = parts[0];
		String pass = parts[1];
		PreparedStatement prepst;
		String query;
		prepst = null;
		query = "";
		boolean creation = false;
		
		try {
			// Check if the user already exists in the database
			
			// mysql SELECT prepared statement
			query = "SELECT COUNT(login) as account FROM users WHERE login=?";
			// create mysql SELECT prepared Statement
			prepst = con.prepareStatement(query);
			prepst.setString(1,login);
			// execute the prepared Statement
			ResultSet res = prepst.executeQuery();
			// Get the first line
			res.next();
			if(res.getInt("account") == 0) {
				// This login doesn't exist
				// Create the new account
				prepst = null;
				query = "";
				// mysql INSERT prepared statement
				query = "INSERT INTO users (login,password) VALUES (?,?)";
				// create mysql INSERT prepared Statement
				prepst = con.prepareStatement(query);
				prepst.setString(1,login);
				prepst.setString(2,pass);
				// execute the prepared Statement
				prepst.execute();
				creation = true;
			}
			else {
				// This login already exists
				creation = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Closing the Statement
		try {
			prepst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return creation;
	}
	
	
	public boolean conCompte(String data) {
		boolean connection = false;
		// Check if the user exists in the database
		boolean exist = checkLoginPass(data);
		if(exist) {
			connection = true;
		}
		else {
			connection = false;
		}
		return connection;
	}
	
	public boolean modifCompte(String data) {
		String[] parts = data.split("\t");
		String oldLogin = parts[0];
		String oldPass = parts[1];
		String newLogin = parts[2];
		String newPass = parts[3];
		PreparedStatement prepst;
		String query;
		prepst = null;
		query = "";
		boolean modif = false;
		// Check if the user exists in the database
		boolean exist = checkLoginPass(oldLogin+"\t"+oldPass);
		if(exist) {
			try {
				// mysql UPDATE prepared statement
				query = "UPDATE users SET login=? AND password=? WHERE login=? AND password=?";
				// create mysql UPDATE prepared Statement
				prepst = con.prepareStatement(query);
				prepst.setString(1,newLogin);
				prepst.setString(2,newPass);
				prepst.setString(3,oldLogin);
				prepst.setString(4,oldPass);
				// execute the prepared Statement
				prepst.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			modif = true;
		}
		else {
			modif = false;
		}
		// Closing the Statement
		try {
			prepst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return modif;
	}
	
	public boolean uploadText(String data, String clientLogin) {
		String[] parts = data.split("\t");
		String title = parts[0];
		String description = parts[1];
		PreparedStatement prepst;
		String query;
		prepst = null;
		query = "";
		// mysql INSERT prepared statement
		query = "INSERT INTO posts (id,title,desc) VALUES (?,?,?)";
		// create mysql INSERT prepared Statement
		try {
			prepst = con.prepareStatement(query);
			prepst.setString(1,"SELECT id FROM users WHERE login="+clientLogin);
			prepst.setString(2,title);
			prepst.setString(3,description);
			// execute the prepared Statement
			prepst.execute();
			prepst.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
		
	}
	
	public void uploadImage(BufferedImage img, String clientLogin) {
		
	}
	
	public void stopServer() {
		try {
			System.out.println("[-] Disconnecting from database");
			System.out.println("[-] Closing server");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

