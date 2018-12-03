package main;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Main {
	public static final String PATH = "Data/DS1-trace.txt";
	private static String URL = "rmi://localhost:12345/";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			LogServer logServer = new LogServer(PATH);
			LocateRegistry.createRegistry(12345);
			Naming.rebind(URL+"ABC", logServer);
			System.out.println("Serveur started");
			System.out.println(logServer.genPath(5));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Serveur failed");
			e.printStackTrace();
		}

	}

}
