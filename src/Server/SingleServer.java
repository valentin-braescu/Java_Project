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
	
	public synchronized void delWorker(Worker worker) {
		colWorker.remove(worker);
		listener.removeUser();
	}
	
	public synchronized int checkLoginPass(String data) {
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
			if(res.next()) {
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
	
	public synchronized boolean creaCompte(String data) {
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
	
	
	public synchronized int conCompte(String data) {
		// Check if the user exists in the database
		// If id == 0, the user doesn't exist
		int id = checkLoginPass(data);
		return id;
	}
	
	public synchronized boolean modifCompte(String data) {
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
	
	public synchronized boolean upload(String data, BufferedImage img, String clientLogin,int clientId, String date) {
		String[] parts = data.split("\t");
		String title = parts[0];
		String description = parts[1];
		int nbFood = Integer.valueOf(parts[2]);
		PreparedStatement prepstInsert1;
		PreparedStatement prepstInsert2;
		String queryInsert1;
		String queryInsert2;
		prepstInsert1 = null;
		prepstInsert2 = null;
		queryInsert1 = "";
		queryInsert2 = "";
		
		// Image treatment
		// The default extension is PNG (need to work on that)
        try {
        	System.out.println("Uploading");
            // Save the image with a temporary name
            File outputFile = new File("C:\\Users\\S�bastien\\Desktop\\Cours\\3A\\Java\\JavaProject\\Java_Project\\images\\"+clientLogin+"_temp.png");
            if (outputFile.createNewFile()){
            	//System.out.println("File is created!");
            }else{
            	//System.out.println("File already exists.");
            }
            ImageIO.write(img, "png", outputFile);
          
            // Open the image as a file and hash it
            File tempImage = new File("C:\\Users\\S�bastien\\Desktop\\Cours\\3A\\Java\\JavaProject\\Java_Project\\images\\"+clientLogin+"_temp.png");
            // Create message digest
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Get the checksum
            String checksum = getFileChecksum(md,tempImage);

            // Rename the image previously saved
            File oldfile =new File("C:\\Users\\S�bastien\\Desktop\\Cours\\3A\\Java\\JavaProject\\Java_Project\\images\\"+clientLogin+"_temp.png");
    		File newfile =new File("C:\\Users\\S�bastien\\Desktop\\Cours\\3A\\Java\\JavaProject\\Java_Project\\images\\"+checksum+".png");
    		if(oldfile.renameTo(newfile)){
    			//System.out.println("[+] Rename succesful");
    		}else{
    			//System.out.println("[-] Rename failed");
    		}
    		
    		
    		// Search for food codes in the database
    		// /////// rep�rer les aliments qui ne sont pas enregistr�s dans la table
    		boolean alimentsPresents = true;
    		int cpt=0;
    		while(cpt<nbFood && alimentsPresents) {
    			int foodCode = getFoodCode(parts[3+cpt]);
    			cpt++;
    			if(foodCode==0) {
    				// If only one food doesn't exist in the database, the upload is canceled
    				alimentsPresents=false;
    			}
    		}
    		if(alimentsPresents) {
    			// Compute the score of the dish
    			String tempScore = "";
    			for(int i=0; i< nbFood; i++) {
    				tempScore += getFoodScore(parts[3+i]);
    			}
    			String finalScore = computeScore(tempScore);
    			
    			// INSERT the infos in the Database
    			// mysql INSERT prepared statement
    			queryInsert1 = "INSERT INTO posts (id,title,description,imageName,date,score) VALUES (?,?,?,?,?,?)";
    			// create mysql INSERT prepared Statement
    			// Build the SQL INSERT query
    			prepstInsert1 = con.prepareStatement(queryInsert1);
    			prepstInsert1.setInt(1,clientId);
    			prepstInsert1.setString(2,title);
    			prepstInsert1.setString(3,description);
    			prepstInsert1.setString(4,checksum);
    			prepstInsert1.setString(5,date);
    			prepstInsert1.setString(6,finalScore);
    			// execute the prepared Statement
    			prepstInsert1.execute();
    			prepstInsert1.close();
    			
    			// Create a link between the food and the users in the database
    			for(int i=0; i< nbFood; i++) {
    				// INSERT a line containing (userId, date, food_code)
    				queryInsert2 = "INSERT INTO food_posts (userId,date,food_code) VALUES (?,?,?)";
    				// create mysql INSERT prepared Statement
    				// Build the SQL INSERT query
    				prepstInsert2 = con.prepareStatement(queryInsert2);
    				prepstInsert2.setInt(1,clientId);
    				prepstInsert2.setString(2,date);
    				int foodCode = getFoodCode(parts[3+i]);
    				prepstInsert2.setInt(3,foodCode);
    				// execute the prepared Statement
    				prepstInsert2.execute();
    				prepstInsert2.close();
    			}
    		}
    		else {
    			// At least one of the food uploaded doesn't belong to our database. The user needs to give more infos about the food.
    			return false;
    		}
    		
        } catch(IOException | SQLException e) {
        	System.out.println("[x] IO error.");
        	return false;
        } catch (NoSuchAlgorithmException e) {
        	System.out.println("[x] Algorithm error.");
        	return false;
		}
        return true;
	}
	
	public synchronized int getFoodCode(String food) {
		// Return the code of the first food matching in the database
		PreparedStatement prepst;
		int foodCode = 0;
		String query = "";
		prepst = null;
		try {
			// Check if the food already exists in the database
			// mysql SELECT prepared statement
			query = "SELECT code FROM food WHERE nom=?";
			// create mysql SELECT prepared Statement
			prepst = con.prepareStatement(query);
			prepst.setString(1,food);
			// execute the prepared Statement
			ResultSet res = prepst.executeQuery();
			// Get the first line
			if(!res.next()) {
				foodCode = 0;
			}
			else {
				foodCode = res.getInt("code");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foodCode;
	}
	
	private synchronized static String getFileChecksum(MessageDigest digest, File file) throws IOException
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
	
	public synchronized String sendPostText(int line) {
		// Return a line with: "username,title,description,nutriScore,date,imageName"
		// If line = 2, return the second last line uploaded.
		String response = "";
		PreparedStatement prepst;
		String query;
		prepst = null;
		query = "";
		boolean lineFound = true;
		// mysql SELECT prepared statement
		query = "SELECT u.login, u.id, p.title, p.description, p.imageName, p.date, p.score FROM users AS u INNER JOIN posts AS p ON u.id = p.id ORDER BY p.date DESC";
		// create mysql SELECT prepared Statement
		try {
			prepst = con.prepareStatement(query);
			// execute the prepared Statement
			ResultSet res = prepst.executeQuery();
			if(!res.next()) {
				// No line found
				lineFound = false;
			}
			else {
				res.last();
				int nbLines = res.getRow();
				res.beforeFirst();
				res.next();
				if(nbLines<line) {
					// Not enough lines to get this line number
					lineFound = false;
				}
				else {
					// Get the good line
					for(int i=1; i<line;i++) {
						res.next();
					}
				}
			}
			
			if(lineFound) {
				// For each post, the server send the infos to the client
				String login = res.getString("login");
				int id = res.getInt("id");
				String title = res.getString("title");
				String description = res.getString("description");
				String imageName = res.getString("imageName");
				String date = res.getString("date");
				String score = res.getString("score");
				int nbFood = getNbFood(id, date);
				response+=login+"\t"+title+"\t"+description+"\t"+imageName+"\t"+date+"\t"+Integer.valueOf(nbFood);
				for(int j=0;j<nbFood;j++) {
					// We add the list of food associated with an image
					response += "\t"+getFoodUsed(id, date,j+1);
				}
				response += "\t"+score;
			}
			prepst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public synchronized int getNbFood(int id, String date) {
		// Return the number of food associated with a post
		PreparedStatement prepst;
		String query = "";
		prepst = null;
		int nbFood = 0;
		try {
			// mysql SELECT prepared statement
			query = "SELECT count(userId) as cpt FROM food_posts WHERE userId=? AND date=?";
			// create mysql SELECT prepared Statement
			prepst = con.prepareStatement(query);
			prepst.setInt(1,id);
			prepst.setString(2,date);
			// execute the prepared Statement
			ResultSet res = prepst.executeQuery();
			res.next();
			nbFood = res.getInt("cpt");
			prepst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbFood;
	}
	
	public synchronized String getFoodScore(String foodName) {
		// Return the score of a food
		PreparedStatement prepst;
		String query = "";
		prepst = null;
		String score = "";
		try {
			// mysql SELECT prepared statement
			query = "SELECT score FROM food WHERE nom=?";
			// create mysql SELECT prepared Statement
			prepst = con.prepareStatement(query);
			prepst.setString(1,foodName);
			// execute the prepared Statement
			ResultSet res = prepst.executeQuery();
			res.next();
			score = res.getString("score");
			prepst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return score;
	}
	
	public synchronized String computeScore(String score) {
		String finalScore = "";
		int nbA=0,nbB=0,nbC=0,nbD=0,nbE=0;
		// We count the number of A, B, C, D, and E scores, and we compute the average number to get the final score
		nbA+=occurences(score,"a");
		nbB+=occurences(score,"b");
		nbC+=occurences(score,"c");
		nbD+=occurences(score,"d");
		nbE+=occurences(score,"e");
		int moy =(int)((nbA*5+nbB*4+nbC*3+nbD*2+nbE)/(nbA+nbB+nbC+nbD+nbE));
		switch(moy) {
		case 1:
			finalScore = "e";
			break;
		case 2:
			finalScore = "d";
			break;
		case 3:
			finalScore = "c";
			break;
		case 4:
			finalScore = "b";
			break;
		case 5:
			finalScore = "a";
			break;
		}
		return finalScore;
	}
	
	public synchronized int occurences(String str, String oc) {
		int cpt = 0;
		int indexInit = 0;
		while(str.indexOf(oc,indexInit)!=-1) {
			cpt++;
			indexInit = str.indexOf(oc,indexInit);
		}
		return cpt;
	}
	public synchronized String getFoodUsed(int id, String date, int line) {
		// Return the name of the food associated with an image (if lots of food, refer to the line)
		PreparedStatement prepst;
		String foodName = "";
		String query = "";
		prepst = null;
		int foodCode=0;
		try {
			// mysql SELECT prepared statement
			query = "SELECT food_code FROM food_posts WHERE userId=? AND date=?";
			// create mysql SELECT prepared Statement
			prepst = con.prepareStatement(query);
			prepst.setInt(1,id);
			prepst.setString(2,date);
			// execute the prepared Statement
			ResultSet res = prepst.executeQuery();
			// Get the good line
			for(int i=0; i<line; i++) {
				res.next();
			}
			foodCode = res.getInt("food_code");
			foodName = foodNameFromCode(foodCode);
			prepst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foodName;
	}
	
	public synchronized String foodNameFromCode(int foodCode) {
		// Return the name of the food associated with a food code.
		PreparedStatement prepst;
		String query = "";
		prepst = null;
		String foodName="";
		try {
			// mysql SELECT prepared statement
			query = "SELECT nom FROM food WHERE code=?";
			// create mysql SELECT prepared Statement
			prepst = con.prepareStatement(query);
			prepst.setInt(1,foodCode);
			// execute the prepared Statement
			ResultSet res = prepst.executeQuery();
			// Get the good line
			res.next();
			foodName = res.getString("nom");
			prepst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foodName;
	}
	
	public synchronized int searchFoodLines(String food) {
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
			if(res.next()) {
				nbLines = res.getInt("nbLines");
			}
			prepst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbLines;
	}
	
	public synchronized String searchFoodInfos(String food, int line) {
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
			info += res.getInt("code")+"\t"+res.getString("type_de_produit")+"\t"+res.getString("nom")+"\t"+res.getString("marque")+"\t"+res.getString("categorie")+"\t"+res.getString("score")+"\t"+res.getInt("valeur_energetique")+"\t"+res.getFloat("acides_gras_satures")+"\t"+res.getFloat("sucres")+"\t"+res.getFloat("proteines")+"\t"+res.getFloat("fibres")+"\t"+res.getFloat("sel_ou_sodium")+"\t"+res.getInt("teneur_fruits_legumes");
			prepst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	
	public synchronized boolean addNewFood(String food) {
		// Format: type_de_produit, nom, marque, categorie, score, valeur_energetique, acides_gras_satures, sucres, proteines, fibres, sel_ou_sodium, teneur_fruits_legumes
		String[] parts = food.split("\t");
		String type_de_produit = parts[0];
		String nom = parts[1];
		String marque = parts[2];
		String categorie = parts[3];
		String score = parts[4];
		int valeur_energetique = Integer.valueOf(parts[5]);
		float acides_gras_satures = Float.valueOf(parts[6]);
		float sucres = Float.valueOf(parts[7]);
		float proteines = Float.valueOf(parts[8]);
		float fibres = Float.valueOf(parts[9]);
		float sel_ou_sodium = Float.valueOf(parts[10]);
		int teneur_fruits_legumes = Integer.valueOf(parts[11]);
		
		try {
			String query = "";
			PreparedStatement prepst = null;
			// INSERT a line containing (userId, date, food_code)
			query = "INSERT INTO food (type_de_produit,nom,marque,categorie,score,valeur_energetique,acides_gras_satures,sucres,proteines,fibres,sel_ou_sodium,teneur_fruits_legumes) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			// create mysql INSERT prepared Statement
			// Build the SQL INSERT query
			prepst = con.prepareStatement(query);
			prepst.setString(1,type_de_produit);
			prepst.setString(2,nom);
			prepst.setString(1,marque);
			prepst.setString(2,categorie);
			prepst.setString(1,score);
			prepst.setInt(2,valeur_energetique);
			prepst.setFloat(1,acides_gras_satures);
			prepst.setFloat(2,sucres);
			prepst.setFloat(1,proteines);
			prepst.setFloat(2,fibres);
			prepst.setFloat(1,sel_ou_sodium);
			prepst.setInt(2,teneur_fruits_legumes);
			// execute the prepared Statement
			prepst.execute();
			prepst.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
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

