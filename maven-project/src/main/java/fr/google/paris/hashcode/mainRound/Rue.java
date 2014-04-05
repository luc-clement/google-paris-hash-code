package fr.google.paris.hashcode.mainRound;

import org.apache.log4j.Logger;

public class Rue {
	
	private final static Logger LOGGER = Logger.getLogger(Rue.class);

	int departId;
	int arriveeId;
	int tempsParcours;
	int longueur;
	
	
	public Rue(int departId, int arriveeId, int tempsParcours, int longueur) {
		this.departId = departId;
		this.arriveeId = arriveeId;
		this.tempsParcours = tempsParcours;
		this.longueur = longueur;
	}
	
	public void supprimerScore() {
		this.longueur = 0;
	}
	
}
