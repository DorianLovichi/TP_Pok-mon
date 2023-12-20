package pokemon;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Joueur implements Serializable {
	private String nom;
	private ArrayList<String> listeBonbon = new ArrayList<>();
	private ArrayList<Pokemon> listePokemonCombat = new ArrayList<>();
	private ArrayList<Pokemon> listePokemon = new ArrayList<>();

	private int niveau;
	
	
	//constructeur : seulement le nom
	public Joueur(String nom) {
		super();
		this.nom = nom;
		this.niveau = 1;
	}
	
	
	//getter
	public String getNom() {
		return nom;
	}
	


	public ArrayList<String> getBonbon() {
		return listeBonbon;
	}

	

	public ArrayList<Pokemon> getListePokemonCombat() {
		return listePokemonCombat;
	}

	
	//setter

	public void setListePokemon(Pokemon pok) {
		this.listePokemon.add(pok);
	}


	
	public void addBonbon(ArrayList<String> types) {
		for (String bonbon : types) {
			this.listeBonbon.add(bonbon);

		}
	}




	//sélectionne une entrée aléatoire du pokedex et l'ajoute aux pokemons du joueur
	public void addPokemon(ArrayList<String[]> pokedex) {
		int index = (int)(Math.random() * pokedex.size());
		Pokemon pok = new Pokemon(pokedex.get(index));
		
		//s'il reste de la place dans l'équipe de combat
		if (this.listePokemonCombat.size() <6) {
			this.listePokemonCombat.add(pok);
		}
		//dans tous les cas : ajout à la liste des pokemons possédés
		this.listePokemon.add(pok);
		this.addBonbon(pok.getType());

	}
	
	//permet de générer une équipe entière (6 pokémons)
	public void genererEquipe(ArrayList<String[]> pokedex) {
		for (int i=0; i<6; i++) {
			addPokemon(pokedex);
		}
		
	}
	
	//permet d'ajouter un pokemon possédé à la liste de combat
	public void transferVersListeCombat(Pokemon p) {
		if (this.listePokemon.indexOf(p) != -1){
			this.listePokemonCombat.add(p);
		}
	}
	
	
	public ArrayList<String> getListeBonbon() {
		return listeBonbon;
	}
	public int getNombreBonbons() {
		return listeBonbon.size();
	}

	public void setListeBonbon(ArrayList<String> listeBonbon) {
		this.listeBonbon = listeBonbon;
	}


	//permet de faire évoluer un pokemon
	public void evoluer(Pokemon pok) {
		int bonbonsCorrespondants = 0;
		
		//s'il le pokemon possède des formes supérieures
		if(pok.getNbForme() > pok.getFormeActuelle()) {
			//pour chaque type : on regarde si le joueur possède un bonbon
			for(String t : pok.getType()) {
				for (String b : listeBonbon) {
					if (b.equals(t)) {
						bonbonsCorrespondants += 1;
					}
					
				}
				
			}
			
			//si le joueur possède 5 bonbons : on le fait évoluer
			if (bonbonsCorrespondants > 5) {
				pok.gererEvolution();
			}
		}
	}
	


	@Override
	public String toString() {
		return "Joueur [nom=" + nom + ", bonbon=" + listeBonbon + ", listePokemon=" + listePokemon + "\nniveau=" + niveau
				+ "]";
	}
	
	
	//partie sérialisation et désérialisation 
	
	
	public void serialiser() throws IOException {
		// On crée un flux de sortie vers un fichier
		FileOutputStream fichier = new FileOutputStream("pokemon/src/pokemon/"+this.nom+".txt");
		// On crée un flux de sortie d'objet
		ObjectOutputStream flux = new ObjectOutputStream(fichier);
		// On écrit l'objet
		flux.writeObject(this);
		flux.close();

	}
	
	public static Joueur deserialiser(String nom) throws IOException, ClassNotFoundException {
		// On crée un flux d'entrée vers un fichier
		FileInputStream fichier = new FileInputStream("pokemon/src/pokemon/"+nom+".txt");
		// On crée un flux d'entrée d'objet
		ObjectInputStream flux = new ObjectInputStream(fichier);
		// On récupère l'objet 
		return (Joueur) flux.readObject();
	}
	

    public void serialize(Object obj, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(obj);
            System.out.println("Objet sérialisé avec succès.");
        }
    }


    public Object deserialize(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = in.readObject();
            System.out.println("Objet désérialisé avec succès.");
            return obj;
        }
    }

}
