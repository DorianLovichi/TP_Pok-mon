package pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Combat {
	
	//correspond à la liste des types présents dans le jeu
	//permet à partir du type d'un pokemon d'obtenir ses forces et faiblesses
	protected static ArrayList<String> listeType = new ArrayList<String>();
	//contient les rapports de forces et faiblesses pour chaque type
	protected static ArrayList<float[]> tableauType = genererTableauType();	
	
	//permet de générer le tableau des types (contient les coefficients des
	// forces faiblesses) ainsi que la liste des types
	public static ArrayList<float[]> genererTableauType(){
		//crée le pokedex : Arraylist composé d'Array : contient tous les pokemons
		ArrayList<float[]> tableauType = new ArrayList<>();
		
		String line = "";
		
		//définit le séparateur
		String splitBy = ";";
		
		try   {  
			//permet de récupérer le fichier csv 
			BufferedReader br = new BufferedReader(new FileReader("pokemon/src/pokemon/TableauType.csv")); 
			
			
			//permet de garder la liste des types présents
			for (String s : br.readLine().split(splitBy)) {
				//System.out.println(s);
				listeType.add(s);
			}
			//listeType = br.readLine().split(splitBy);
			
			//pour chaque ligne suivante
			while ((line = br.readLine()) != null){
				//initialisation de l'array type
				float[] type = new float[listeType.size()];

				//split les données en un tableau
				//converti les String en float
				for (int i = 0; i < listeType.size(); i++) {
					type[i] = Float.parseFloat(line.split(splitBy)[i]);

				}
				
				// ajoute le nouveau pokemon au pokedex
				tableauType.add(type);
			}
			//ferme le reader
			br.close();
			//System.out.println("Pokedex généré");
		}   
		catch (IOException e){  
			e.printStackTrace();  
		}
		
		return tableauType;
	}
	
	
	//calcule les dégats causés par l'attaque d'un pokemon sur le pokemon défenseur
	public static double attaquer(Pokemon p_att, Pokemon p_def, double m) {

				
		//pout obtenir les dégats réalisés on multiplie les PC du pokemon
		//attaquant par le coefficient multiplicateur
		double degat = m * p_att.getPC();
		
		//enlève les PV au pokemon defenseur
		p_def.prendreDegat(degat);
		
		//double[] arr = {m,degat};
		return degat;
	}
	
	
	//caclule le coefficient multiplicateur
	//obtenu grâce aux forces et faiblesses de chaque type
	public static double getMultiplicateur(Pokemon p_att, Pokemon p_def){
		
		//initilisation du multiplicateur
		double multiplicateur = 1.0;
		
		//récupère le(s) type(s) de chaque pokemon
		ArrayList <String> type_att = p_att.getType();
		ArrayList <String> type_def = p_def.getType();

		//identifiant pour pouvoir parcourir le tableau des types
		int id_attaque;
		int id_defense;
		
		
		for (String att : type_att) {
			//permet de récupérer l'index de l'attaque dans la liste
			id_attaque = listeType.indexOf(att);
			
			// si bug de Normal (pas trouvé par listeType.indexOf()
			if(id_attaque == -1) {
				id_attaque = 0;
			}
			for (String def : type_def) {
				
				//permet de récupérer l'index du type de défense
				id_defense = listeType.indexOf(def);
				
				// si bug de Normal (pas trouvé par listeType.indexOf()
				if(id_defense == -1) {
					id_defense = 0;
				}
				
				//récupère le coefficient correspondnat au type de l'attaque contre
				// le type de défense
				multiplicateur = multiplicateur * tableauType.get(id_attaque)[id_defense];
			}
		}
		
		return multiplicateur;
	}
	
	//permet d'afficher un message concernant l'efficacité d'une attaque
	public static String messageEfficacite(double multiplicateur) {
		
		String res = "";
		
		if (multiplicateur <= 0.5) {
			res = "Ce n'est pas très efficace";
		}
		else if(multiplicateur >= 2){
			res = "C'est très efficace";
		}
		
		return res;
	}
	
	//si le pokemon défenseur n'a plus de pv : il est KO
	public static boolean isKO(Joueur att, Pokemon p_def) {
		
		//si le pokemon n'a plus de pv
		if (p_def.getPV() <= 0) {
			//distribution des bonbons à l'attaquant
			att.addBonbon(p_def.getType());
			
			
			return true;
		}
		else {
			return false;
		}
	}
	
	//vérifie si un joueur a encore des pokemons non KO
	public static boolean plusDePokemon(Joueur def) {
		//si le joueur en défense n'a plus de pokemon
		if (def.getListePokemonCombat().isEmpty()) {
			//victoire de l'attaquant
			return true;
		}
		//il reste des pokemons au defenseur
		return false;
	}
	
	//supprime le pokemon de la liste de combat du joueur
	public static void supprimerPokemon(Joueur j) {
		//ajout à la liste de pokemons possédés
		j.setListePokemon(j.getListePokemonCombat().get(0));
		//suppression de la liste de combat
		j.getListePokemonCombat().remove(0);
	}
	
}