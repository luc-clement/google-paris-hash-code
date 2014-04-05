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

		/*
		 * TESTS
		 */
		InputOutput.intput("/home/volodia/paris_54000.txt");
		LOGGER.info("---------- INITIAL INFORMATIONS ----------");
		LOGGER.info("nombre d'intersections : " + nbIntersections);
		LOGGER.info("nombre de rues : " + nbRues);
		LOGGER.info("temps virtuel disponible : " + tempsVirtuel);
		LOGGER.info("nombre de véhicules : " + nbVehicules);
		LOGGER.info("position initiale : " + initialPosition);
		
		LOGGER.info("Taille de la liste vehicules : " + vehicules.size());
		LOGGER.info("Taille de la HashMap Intersections : " + Intersections.size());
		//LOGGER.info("Nombre d'intersections joignables depuis le départ : " + Intersections.get(initialPosition).intersectionsJoignables.size());
		LOGGER.info("------------------------------------------");

		
	}

}
