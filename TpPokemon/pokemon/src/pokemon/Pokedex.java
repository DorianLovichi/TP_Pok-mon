package pokemon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;  
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Pokedex{
	
	//permet de générer un pokedex à partir d'un fichier csv
	public static ArrayList<String[]> genererPokedex() {
		
		//crée le pokedex : Arraylist composé d'Array : contient tous les pokemons
		ArrayList<String[]> pokedex = new ArrayList<>();
		
		String line = ""; 
		
		//définit le séparateur
		String splitBy = ";";
		  String workingDirectory = System.getProperty("user.dir");

        // Construisez le chemin du fichier en utilisant File.separator pour la portabilité
        String filePath = workingDirectory +File.separator+ "TP_Pok-mon-main" +File.separator+ "TpPokemon"+ File.separator+ "pokemon" + File.separator + "src" + File.separator + "pokemon" + File.separator + "bdd.csv";
		try   {  
			//permet de récupérer le fichier csv 
			BufferedReader br = new BufferedReader(new FileReader("TP_Pok-mon-main/TpPokemon/pokemon/src/pokemon/bdd.csv")); 
			
			//pour chaque ligne
			while ((line = br.readLine()) != null){
				//split les données en un tableau
				String[] pokemon = line.split(splitBy);
				
				// ajoute le nouveau pokemon au pokedex
				pokedex.add(pokemon);
			}
			//ferme le reader
			br.close();
			//System.out.println("Pokedex généré");
		}   
		catch (IOException e){  
			e.printStackTrace();  
		}
		
		return pokedex;
	}
	

	public static void afficherPokedex(ArrayList<String[]> pokedex) {
		for (String i[] : pokedex) {
            System.out.println(Arrays.toString(i));
        }
	}
}
