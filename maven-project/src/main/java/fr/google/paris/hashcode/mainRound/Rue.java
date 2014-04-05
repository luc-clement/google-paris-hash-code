package fr.google.paris.hashcode.mainRound;

import org.apache.log4j.Logger;

public class Rue {
	
	private final static Logger LOGGER = Logger.getLogger(Rue.class);

	int departId;
	int arriveId;
	int tempsParcours;
	int longueur;
	
	
	public Rue(int departId, int arriveId, int tempsParcours, int longueur) {
		this.departId = departId;
		this.arriveId = arriveId;
		this.tempsParcours = tempsParcours;
		this.longueur = longueur;
	}
	
	
	
}
