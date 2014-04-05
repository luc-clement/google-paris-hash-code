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

		initialLogs();


		
		
		InputOutput.output("/home/volodia/paris_54000_solution.txt");
		
	}
	
	/**
	 * Met à 0 la longueur de toutes les rues traversées par un parcours (dans les deux sens)
	 * 
	 * @param parcours La liste des identifiants des intersections parcourues, dans l'ordre
	 */
	public static void supprimerScoreParcours(List<Integer> parcours) {
		for (int i=0; i<parcours.size()-1; ++i) {
			Intersections.get(i).rueParcourue(i+1);
			Intersections.get(i+1).rueParcourue(i);
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
		LOGGER.info("nombre de véhicules : " + nbVehicules);
		LOGGER.info("position initiale : " + initialPosition);
		
		LOGGER.info("Taille de la liste vehicules : " + vehicules.size());
		LOGGER.info("Taille de la HashMap Intersections : " + Intersections.size());
		LOGGER.info("------------------------------------------");
	}
}
