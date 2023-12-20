package pokemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
	
	private final Socket clientSocket;
	
	public ClientHandler(Socket socket) {
		this.clientSocket = socket;
	}

	
	
	public void run() {
		PrintWriter out = null;
		BufferedReader in = null;		
		try {
			
			//permet d'envoyer des messages au clientj
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			//permet de recevoir des messages au client
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			
			String line;
			
			
			while ((line = in.readLine()) != null) {
				System.out.println("Envoyé depuis le client : %s" + line);
				out.println(line);
				
			}
			
			
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if(in != null) {
					in.close();
					clientSocket.close();
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
