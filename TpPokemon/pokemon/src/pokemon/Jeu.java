package pokemon;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Jeu {
	public static boolean ready = false;
	public static Joueur initialiserJoueur(String pseudo) {
		//génère le pokedex
		ArrayList<String[]> pokedex = Pokedex.genererPokedex();
		Serialiser serialiser = new Serialiser();
		

		//création du joueur
		Joueur j = new Joueur(pseudo);
		//génération aléatoire de son équipe
		j.genererEquipe(pokedex);
		
		System.out.println("Bonjour " + j.getNom());
		System.out.println("Votre équipe a été générée avec succès.");
		
		

		Scanner scanner = new Scanner(System.in);  // Déclaration de scanner ici

		while (true) {
            afficherMenu();

            // Lire le choix de l'utilisateur
            int choix = scanner.nextInt();

            // Effectuer une action en fonction du choix
            switch (choix) {
                case 1:
                    System.out.println("Vous êtes prêt");
					ready = true;
                    return j;
                    
                case 2:
                    System.out.println("Votre équipe a été générée avec succès.");
					System.out.println(j.getListePokemonCombat());
                    break;

                case 3:
                    System.out.println("voici vos bobons");
					System.out.println(j.getBonbon());
                    break;

                case 4:
                    System.out.println("Au revoir !");
					System.exit(0);

                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
			
        }
		
	}
	private static void afficherMenu() {
        System.out.println("Menu :");
        System.out.println("1. Combattre");
        System.out.println("2. Afficher les pokémons");
        System.out.println("3. Afficher les bonbons");
        System.out.println("4. Quitter");
        System.out.print("Faites votre choix : ");
    }
	
}
