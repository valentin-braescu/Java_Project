/**
 * 
 */
package Server;

import java.util.Collection;

/**
 * @author Sebastien
 *
 */
public class SingleServer {

	private Listener listener;
	private Collection<Worker> colWorker;
	
	//Create an object of SingleServer
	private static SingleServer instance = new SingleServer();
	/**
	 * @param args
	 */
	
	//Make the constructor private so that this class cannot be instanciated
	private SingleServer() {
		startServer();
	}
	
	//Get the only object available
	public static SingleServer getInstance() {
		return instance;
	}
	
	public void startServer() {
		System.out.println("[+] Starting server ...");
		listener = new Listener(this);
	}
	
	public void addWorker(Worker worker) {
		colWorker.add(worker);
	}
	
	public void delWorker(Worker worker) {
		colWorker.remove(worker);
	}
	
	public boolean creaCompte() {
		//TODO
		return true;
	}
	public boolean conCompte() {
		//TODO
		return true;
	}
	
	public boolean modifCompte() {
		//TODO
		return true;
	}
	
	
}

