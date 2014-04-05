package fr.google.paris.hashcode.mainRound;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class Intersection {
	
	private final static Logger LOGGER = Logger.getLogger(Intersection.class);
	
	int id;
	double latitude;
	double longitude;
	HashMap<Integer, Rue> intersectionsJoignables = new HashMap<Integer, Rue>();
	
	public Intersection(int id, double latitude, double longitude) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public void addIntersectionJoignables(int intersectionId, Rue rue) {
		if (intersectionsJoignables.containsKey(intersectionId)) {
			LOGGER.error("Tentative d'ajout d'une intersection joignable déjà enregistrée");
		} else {
			intersectionsJoignables.put(intersectionId, rue);
		}
	}
	
	/**
	 * Si il est possible d'aller de cette intersection à l'intersection d'identifiant arriveeId, mettre à 0 la longueur de ce trajet
	 * @param arriveeId l'identifiant de l'arrivée
	 */
	public void rueParcourue(int arriveeId) {
		if (!intersectionsJoignables.containsKey(arriveeId)) {
			LOGGER.info("On ne peut aller de cette rue (" +id+ ") vers la rue " + arriveeId);
		} else {
			intersectionsJoignables.get(arriveeId).supprimerScore();
		}
	}

}
