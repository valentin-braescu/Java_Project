/**
 * 
 */
package Server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.imageio.ImageIO;
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
	
	public int checkLoginPass(String data) {
		String[] parts = data.split("\t");
		String login = parts[0];
		String pass = parts[1];
		PreparedStatement prepst;
		String query;
		prepst = null;
		query = "";
		int userId = 0;
		// Check if the user already exists in the database
		
		// mysql SELECT prepared statement
		query = "SELECT id FROM users WHERE login=? AND password=?";
		// create mysql SELECT prepared Statement
		try {
			prepst = con.prepareStatement(query);
			prepst.setString(1,login);
			prepst.setString(2,pass);
			// execute the prepared Statement
			ResultSet res = prepst.executeQuery();
			// Get the first line
			res.next();
			if(!res.wasNull()) {
				// The user exists in the database
				userId = res.getInt("id");
			}
			else {
				// The user doesn't exist in the database
				userId = 0;
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
		return userId;
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
	
	
	public int conCompte(String data) {
		// Check if the user exists in the database
		// If id == 0, the user doesn't exist
		int id = checkLoginPass(data);
		return id;
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
		int id = checkLoginPass(oldLogin+"\t"+oldPass);
		if(id!=0) {
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
	
	public boolean upload(String data, BufferedImage img, String clientLogin,int clientId, String date) {
		String[] parts = data.split("\t");
		String title = parts[0];
		String description = parts[1];
		PreparedStatement prepstInsert;
		String queryInsert;
		prepstInsert = null;
		queryInsert = "";
		// Image treatment
		// The default extension is PNG (need to work on that)
        try {
            // Save the image with a temporary name
            File outputFile = new File("C:\\Users\\Sébastien\\Desktop\\Cours\\3A\\Java\\JavaProject\\Java_Project\\images\\"+clientLogin+"_temp.png");
            if (outputFile.createNewFile()){
            	System.out.println("File is created!");
            }else{
            	System.out.println("File already exists.");
            }
            ImageIO.write(img, "png", outputFile);
          
            // Open the image as a file and hash it
            File tempImage = new File("C:\\Users\\Sébastien\\Desktop\\Cours\\3A\\Java\\JavaProject\\Java_Project\\images\\"+clientLogin+"_temp.png");
            // Create message digest
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Get the checksum
            String checksum = getFileChecksum(md,tempImage);

            // Rename the image previously saved
            File oldfile =new File("C:\\Users\\Sébastien\\Desktop\\Cours\\3A\\Java\\JavaProject\\Java_Project\\images\\"+clientLogin+"_temp.png");
    		File newfile =new File("C:\\Users\\Sébastien\\Desktop\\Cours\\3A\\Java\\JavaProject\\Java_Project\\images\\"+checksum+".png");
    		if(oldfile.renameTo(newfile)){
    			System.out.println("[+] Rename succesful");
    		}else{
    			System.out.println("[-] Rename failed");
    		}
    		
    		// INSERT the infos in the Database
			// mysql INSERT prepared statement
			queryInsert = "INSERT INTO posts (id,title,description,imageName,date) VALUES (?,?,?,?,?)";
			// create mysql INSERT prepared Statement
			// Build the SQL INSERT query
			prepstInsert = con.prepareStatement(queryInsert);
			prepstInsert.setInt(1,clientId);
			prepstInsert.setString(2,title);
			prepstInsert.setString(3,description);
			prepstInsert.setString(4,checksum);
			prepstInsert.setString(5,date);
			// execute the prepared Statement
			prepstInsert.execute();
			prepstInsert.close();
        } catch(IOException | SQLException e) {
        	System.out.println("[x] IO error.");
        	return false;
        } catch (NoSuchAlgorithmException e) {
        	System.out.println("[x] Algorithm error.");
        	return false;
		}
        return true;
	}
	
	
	private static String getFileChecksum(MessageDigest digest, File file) throws IOException
	{
		// GetFileChecksum function implemented here: https://howtodoinjava.com/core-java/io/how-to-generate-sha-or-md5-file-checksum-hash-in-java/ 
	    // Get file input stream for reading the file content
	    FileInputStream fis = new FileInputStream(file);
	     
	    // Create byte array to read data in chunks
	    byte[] byteArray = new byte[1024];
	    int bytesCount = 0;
	      
	    // Read file data and update in message digest
	    while ((bytesCount = fis.read(byteArray)) != -1) {
	        digest.update(byteArray, 0, bytesCount);
	    };
	     
	    // close the stream
	    fis.close();
	     
	    // Get the hash's bytes
	    byte[] bytes = digest.digest();
	     
	    // This bytes[] has bytes in decimal format;
	    // Convert it to hexadecimal format
	    StringBuilder sb = new StringBuilder();
	    for(int i=0; i< bytes.length ;i++)
	    {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	     
	    // return complete hash
	    return sb.toString();
	}
	
	public String getUploadedText(int line) {
		// Return a line with: "username,title,description,nutriScore,date,imageName"
		// If line = 2, return the second last line uploaded.
		String response = "";
		PreparedStatement prepst;
		String query;
		prepst = null;
		query = "";
		boolean lineFound = true;
		// mysql SELECT prepared statement
		query = "SELECT u.login, p.title, p.description, p.date, p.imageName FROM users AS u INNER JOIN posts AS p ON u.id = p.id ORDER BY p.date DESC";
		// create mysql SELECT prepared Statement
		try {
			prepst = con.prepareStatement(query);
			// execute the prepared Statement
			ResultSet res = prepst.executeQuery();
			// Get the good line
			for(int i=0; i<line;i++) {
				res.next();
				if(res.wasNull()) {
					// Not enough lines to find the expected one
					i = line;
					lineFound = false;
				}
			}
			if(lineFound) {
				response+=res.getString("login")+"\t"+res.getString("title")+"\t"+res.getString("description")+"\t"+res.getString("date")+"\t"+res.getString("imageName");
			}
			prepst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	public int searchFoodLines(String food) {
		// Looking for the number of lines corresponding to this food in the database
		PreparedStatement prepst;
		String query;
		prepst = null;
		query = "";
		int nbLines = 0;
		// mysql SELECT prepared statement
		query = "SELECT count(code) as nbLines FROM food WHERE nom LIKE ?";
		// create mysql SELECT prepared Statement
		try {
			prepst = con.prepareStatement(query);
			prepst.setString(1,"%"+food+"%");
			// execute the prepared Statement
			ResultSet res = prepst.executeQuery();
			res.next();
			if(!res.wasNull()) {
				nbLines = res.getInt("nbLines");
			}
			prepst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbLines;
	}
	
	public String searchFoodInfos(String food, int line) {
		// Looking for information about this food/line in the database
		PreparedStatement prepst;
		String query;
		prepst = null;
		query = "";
		String info = "";
		// mysql SELECT prepared statement
		query = "SELECT * FROM food WHERE nom LIKE ?";
		// create mysql SELECT prepared Statement
		try {
			prepst = con.prepareStatement(query);
			prepst.setString(1,"%"+food+"%");
			// execute the prepared Statement
			ResultSet res = prepst.executeQuery();
			for(int i=0; i<line; i++) {
				res.next();
			}
			info += res.getString("code")+"\t"+res.getString("type_de_produit")+"\t"+res.getString("nom")+"\t"+res.getString("marque")+"\t"+res.getString("categorie")+"\t"+res.getString("score")+"\t"+res.getInt("valeur_energetique")+"\t"+res.getFloat("acides_gras_satures")+"\t"+res.getFloat("sucres")+"\t"+res.getFloat("proteines")+"\t"+res.getFloat("fibres")+"\t"+res.getFloat("sel_ou_sodium")+"\t"+res.getInt("teneur_fruits_legumes");
			prepst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
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

