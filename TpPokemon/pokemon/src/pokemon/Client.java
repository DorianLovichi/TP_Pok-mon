package pokemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
    	
    	
    	Scanner sc = new Scanner(System.in);  // Cree un objet Scanner
        System.out.println("Entrez votre pseudo");
        String pseudo = sc.nextLine();  
    	//initialise le joueur et son équipe
    	Joueur j = Jeu.initialiserJoueur(pseudo);
    	//initialise le socket
        Socket clientSocket = new Socket(InetAddress.getLocalHost(),2000);
        System.out.println("Connecté au server");

        
        //permet d'envoyer des objets au server
        ObjectOutputStream objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
        
        //pour lire les réponses du server
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        String line = null;
        

        //envoie les informations du joueur au server
        objectOut.writeObject(j);
        Serialiser serialiser = new Serialiser();
        try{
            serialiser.serialize(j, pseudo+".txt");
            Joueur clientDeserialisee = (Joueur) serialiser.deserialize(pseudo+".txt");
            System.out.println(clientDeserialisee.toString());

        }catch(Exception e){
            e.printStackTrace();
        }
        
        //tant que le combat n'est pas fini : lecture des messages envoyés
        while (!"fin".equalsIgnoreCase(line)) {
        	
        	System.out.println(in.readLine());
        	
        }
        sc.close();
        clientSocket.close();
    }
}

   