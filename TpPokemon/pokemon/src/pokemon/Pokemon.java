package pokemon;

import java.io.Serializable;
import java.util.ArrayList;

public class Pokemon implements Serializable{
	
	//attributs présente dans bdd
	private int id;
	private String nom;
	private ArrayList<String> type;
	private int nbForme;
	private int formeActuelle;
	private ArrayList<String> evolution;
	
	//attributs à générer
	private int PV = 0;
	private int PC = 0;
	
	
	//Constructeur
	public Pokemon(String[] donneePokemon) {
		this.id = Integer.parseInt(donneePokemon[0]);
		this.nom = donneePokemon[1];
		
		this.type = new ArrayList<>();
		this.type = setType(donneePokemon);

		this.nbForme = Integer.parseInt(donneePokemon[4]);
		this.evolution = setEvolution(this.nbForme, donneePokemon);
		
		this.PV = initPV();
		this.PC = initPC();
	}
	
	
	//getters 
	public ArrayList<String> getType() {
		return type;
	}
	
	public int getPC() {
		return this.PC;
	}
	
	public int getPV() {
		return this.PV;
	}

	public String getNom() {
		return this.nom;
	}
	
	public int getNbForme() {
		return nbForme;
	}


	public int getFormeActuelle() {
		return formeActuelle;
	}

	
	//setter
	//permet de créer l'arrayliste contenant le(s) type(s) du pokemon
	public ArrayList<String> setType(String[] donneePokemon){
		ArrayList<String> res = new ArrayList<>();
		
		//on boucle sur les données du type (index 2 et 3) 
		for (int i = 2; i<4; i++){
			//Si la chaine n'est pas vide : il y a un type
			if (!donneePokemon[i].isBlank()) {
				//ajout du type à l'arrayList
				res.add(donneePokemon[i]);
			}
		}
		
		return res;
	}
	
	
	//permet à partir du nombre de formes du pokemon de créer l'arrayList contenant
	//le nom de ses évolutions
	public ArrayList<String> setEvolution(int nbForme, String[] donneePokemon) {
		ArrayList<String> res = new ArrayList<>();

		//on n'ajoute pas d'évolution si le pokemon possède une seule forme
		if(nbForme > 1){
			//on ajoute les évoluions à la liste, jusqu'à ce qu'il y ait le bon nombre de forme
			//NOTE : index de la liste du début des evolution = 5 
			for (int i = 5; res.size()<nbForme-1; i++) {

				res.add(donneePokemon[i]);
			}
		}
		
		return res;
	}

	
	public void setNbForme(int nbForme) {
		this.nbForme = nbForme;
	}

	
	public void setPV(int p) {
		this.PV += PV + (PV * (p/100));
	}
	
	public void setPC(int p) {
		this.PC += PC + (PC * (p/100));
	}

	
	//initialisation des PV et PC aléatoirement
	public int initPV() {
		//met les pv aléatoirement entre 20 et 50
		return 20 + (int)(Math.random() * ((50 - 20) + 1));
	}

	public int initPC() {
		//met les pc aléatoirement entre 1 et 8
		return 1 + (int)(Math.random() * ((8 - 3) + 1));
	}
	

	//permet de passer à la forme suivante
	public void gererEvolution() {
		this.formeActuelle += 1;
		//change le nom pour correspondre à celui de la forme actuelle
		this.nom = this.evolution.get(formeActuelle-1);
	}

	
	public void prendreDegat(double degat) {
		this.PV -= degat;
	}
	
	
	@Override
	public String toString() {
		return "\nPokemon [nom=" + nom + ", type=" + type + ", evolution="
				+ evolution + ", PV=" + PV + ", PC=" + PC + "]";
	}
}
