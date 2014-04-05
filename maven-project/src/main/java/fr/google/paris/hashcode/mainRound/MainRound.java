package fr.google.paris.hashcode.mainRound;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class MainRound {

	private final static Logger LOGGER = Logger.getLogger(MainRound.class);

	public static int nbIntersections;
	public static int nbRues;
	public static int tempsVirtuel;
	public static int nbVehicules;
	public static int initialPosition;

	public static HashMap<Integer, Intersection> Intersections = new HashMap<Integer, Intersection>();
	public static List<Vehicule> vehicules = new ArrayList<Vehicule>();


	public static void main(String[] args) throws FileNotFoundException, IOException {
		// Configuration de Log4J
		BasicConfigurator.configure();
		InputOutput.intput("/home/volodia/paris_54000.txt");
		//InputOutput.intput("/Users/alexisterrat/paris_54000.txt");
		
		initialLogs();

		/*
		 * Solution algorithme récursif croisé (a priori moins performant)
		 */
//		boolean weHaveToContinue = true;
//		HashMap<Vehicule, Boolean> getNext = new HashMap<Vehicule, Boolean>();
//		for (Vehicule vehicule : vehicules) {
//			getNext.put(vehicule, true);
//		}
//		while (weHaveToContinue) {
//			for (Vehicule vehicule : vehicules) {
//				//LOGGER.info("NEXT VEHICULE");
//				//			vehicule.getItineraire();
//				if (getNext.get(vehicule))
//					getNext.put(vehicule, vehicule.getNextPart());
//				//	supprimerScoreParcours(vehicule.getItineraireFinal());
//				//	vehicule.defineItineraire();
//			}
//			weHaveToContinue = getNext.containsValue(true) ? true : false;
//		}


		/*
		 * Solution algorithme récursif non croisee
		 */
		for (Vehicule vehicule : vehicules) {
			LOGGER.info("NEXT VEHICULE");
			vehicule.getItineraire();
		}



		InputOutput.output("/home/volodia/paris_54000_solution.txt");
		//InputOutput.output("/Users/alexisterrat/paris_54000_solution.txt");

	}

	/**
	 * Met a 0 la longueur de toutes les rues traversees par un parcours (dans les deux sens)
	 * 
	 * @param parcours La liste des identifiants des intersections parcourues, dans l'ordre
	 */
	public static void supprimerScoreParcours(List<Integer> parcours) {
		for (int i=0; i<parcours.size()-1; ++i) {
			Intersections.get(parcours.get(i)).rueParcourue(parcours.get(i+1));
			Intersections.get(parcours.get(i+1)).rueParcourue(parcours.get(i));
		}
	}

	/**
	 * Print the initial informations
	 */
	public static void initialLogs() {
		LOGGER.info("---------- INITIAL INFORMATIONS ----------");
		LOGGER.info("nombre d'intersections : " + nbIntersections);
		LOGGER.info("nombre de rues : " + nbRues);
		LOGGER.info("temps virtuel disponible : " + tempsVirtuel);
		LOGGER.info("nombre de vehicules : " + nbVehicules);
		LOGGER.info("position initiale : " + initialPosition);

		LOGGER.info("Taille de la liste vehicules : " + vehicules.size());
		LOGGER.info("Taille de la HashMap Intersections : " + Intersections.size());
		LOGGER.info("------------------------------------------");
	}
}
