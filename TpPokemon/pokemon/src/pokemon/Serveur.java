package pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
 
public class Serveur{
	
	private static ArrayList<Joueur> listeJoueur = new ArrayList<Joueur>();
	private static ArrayList<Socket> listeClient = new ArrayList<>();
	private static int nbJoueurConnecte = 0;
	private static boolean run = true;
	private static int nbTour = 1;
	
	
	//permet d'envoyer un message à tous les joueurs
	//utilisée pour décrire l'avancement des combats
	public static void broadcast_message(String message) throws IOException {
		 for (Socket c : listeClient) {
         	PrintWriter out = new PrintWriter(c.getOutputStream(), true);
         	out.println(message);
         }
	}
	
	public static void afficher_recapitulatif(Joueur j_att, Joueur j_def, Pokemon p_att, Pokemon p_def) throws IOException {
		broadcast_message("Récapitulatif :");
    	broadcast_message(j_att.getNom() + " : " + j_att.getListePokemonCombat().size() + " pokemon(s)");
    	broadcast_message(j_def.getNom() + " : " + j_def.getListePokemonCombat().size() + " pokemon(s)");
    	broadcast_message("Pokemon attaquant " + p_att.getNom() + " - PV : " + p_att.getPV());
    	broadcast_message("Pokemon defenseur " + p_def.getNom() + " - PV : " + p_def.getPV());
	}
	
	
    public static void main(String[] args){
    	
        ServerSocket server = null;
        PrintWriter out;
  
        try {
  
        	//création du socket du server
            server = new ServerSocket(2000);
            server.setReuseAddress(true);
  
            //boucler pour recevoir les demandes des clients
            while (nbJoueurConnecte < 2) {
  
            	//nouvelle connection d'un client
                Socket client = server.accept();
                
                
    			//permet de recevoir des messages au client
    			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
  
                //afficher le client connecté
                System.out.println("Nouvel utilisateur connecté " + client.getInetAddress().getHostAddress());
  
                //permet de recevoir les objets envoyés par le client (la liste des pokemons
    			ObjectInputStream objectIn = new ObjectInputStream(client.getInputStream());
                
    			//initialisation joueur
    			Joueur j = null;
    			
    			//récupère l'objet joueur envoyé par le client
				try {
					j = (Joueur) objectIn.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
                // créer un nouvea thread permettant de gérer chaque client
                //indépendamment 
                ClientHandler clientSock = new ClientHandler(client);
  
                
                //ajoute le joueur à la liste (permet de gérer les combats)
                listeJoueur.add(j);
                listeClient.add(client);
                new Thread(clientSock).start();
                nbJoueurConnecte += 1;
                
            }
            for (Socket c : listeClient) {
            	out = new PrintWriter(c.getOutputStream(), true);
            	out.println("Tous les joueurs ont rejoint");
            }
            
            while(run) {
            	
            	Joueur j_attaquant;
            	Joueur j_defenseur;
            	
            	//permet de récupérer les dégats infligés
            	double degat;
            	
            	//permet de récupérer le multiplicateur
            	//pour indiquer l'efficacité d'une attaque
            	double multiplicateur;
            	

            	//si nombre de tour impair : joueur 1 attaque
            	if (nbTour%2 != 0) {
            		j_attaquant = listeJoueur.get(0);
            		j_defenseur = listeJoueur.get(1);
            		
            	}
            	//si nombre de tour pair : joueur 2 attaque
            	else {
            		j_attaquant = listeJoueur.get(1);
            		j_defenseur = listeJoueur.get(0);
            	}
            	
            	//définition des pokemons attaquants et défenseurs
            	Pokemon p_att = j_attaquant.getListePokemonCombat().get(0);
            	Pokemon p_def = j_defenseur.getListePokemonCombat().get(0);

            	
            	afficher_recapitulatif(j_attaquant, j_defenseur, p_att, p_def);


            	//Déroulement du combat
            	//le pokemon attaquant attaque
				
            	multiplicateur = Combat.getMultiplicateur(p_att, p_def);
            	degat = Combat.attaquer(p_att, p_def, multiplicateur);
            	
            	//indique quel pokemon a attaqué
        		broadcast_message(p_att.getNom() + " de " + j_attaquant.getNom() + " attaque le " + p_def.getNom() + " de " + j_defenseur.getNom());
        		
        		//envoie le message concernant l'efficacité de l'attaque
        		broadcast_message(Combat.messageEfficacite(multiplicateur));
        		
        		//indique le nombre de dégats reçus par le pokemon defenseur
        		broadcast_message("Il inflige " + degat + " dégats au " + p_def.getNom() + " de " + j_defenseur.getNom());

            	//si le pokemon défenseur est KO
            	if (Combat.isKO(j_attaquant, p_def)) {
            		//message annonçant le KO du pokemon
        			broadcast_message("Le " + p_def.getNom() + " de " + j_defenseur.getNom() + " n'a plus de PV. Il est KO");
        			
        			//informe l'attaquant des bonbons gagnés
					
        			broadcast_message(j_attaquant.getNom() + " reçoit un/des bonbon(s) " + p_def.getType());
        			
        			//on supprime le pokemon battu
        			Combat.supprimerPokemon(j_defenseur);
        			
            		//on vérifie s'il reste des pokemons au joueur défenseur
            		if(Combat.plusDePokemon(j_defenseur)) {
            			//annonce du gagnant
            			broadcast_message(j_defenseur.getNom() + " n'a plus de Pokemon en forme.");
            			broadcast_message(j_attaquant.getNom() + " a gagné le combat");
            			//on arrête la boucle
            			run = false;
            		}
            		
            	}
            	
            	nbTour += 1;
            }
            
            broadcast_message("fin");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
