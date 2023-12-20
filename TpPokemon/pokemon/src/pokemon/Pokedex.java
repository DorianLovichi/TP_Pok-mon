package pokemon;

import java.io.BufferedReader;  
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
		
		try   {  
			//permet de récupérer le fichier csv 
			BufferedReader br = new BufferedReader(new FileReader("pokemon/src/pokemon/bdd.csv")); 
			
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
