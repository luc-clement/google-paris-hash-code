package fr.google.paris.hashcode.mainRound;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import fr.google.paris.hashcode.trialRound.TrialRound;

public class InputOutput {
	
	public static void intput(String filePath) throws IOException, FileNotFoundException {


		Scanner scanner = new Scanner(new File(filePath));
		
		// Lecture de la première ligne
		String pLigne = scanner.nextLine();
		String[] pLigneSplit = pLigne.split(" ");
		MainRound.nbIntersections = Integer.parseInt(pLigneSplit[0]);
		MainRound.nbRues = Integer.parseInt(pLigneSplit[1]);
		MainRound.tempsVirtuel = Integer.parseInt(pLigneSplit[2]);
		MainRound.nbVehicules = Integer.parseInt(pLigneSplit[3]);
		MainRound.initialPosition = Integer.parseInt(pLigneSplit[4]);
		
		//Lecture des intersections
		for (int i=0; i<MainRound.nbIntersections; ++i) {
			String ligneLue = scanner.nextLine();
			String[] ligneLueSplit = ligneLue.split(" ");
			double latitude = Double.parseDouble(ligneLueSplit[0]);
			double longitude = Double.parseDouble(ligneLueSplit[1]);
			MainRound.Intersections.put(i, new Intersection(i, latitude, longitude));
		}
		
		// Initialisation de la flotte :
		for (int i=0; i<MainRound.nbVehicules; ++i) {
			Vehicule vehicule = new Vehicule(MainRound.tempsVirtuel, MainRound.Intersections.get(MainRound.initialPosition));
			MainRound.vehicules.add(vehicule);
		}

		// Lecture des rues
		for (int j=0; j<MainRound.nbRues; ++j) {
			
			String ligneLue = scanner.nextLine();
			String[] ligneLueSplit = ligneLue.split(" ");
			int departId = Integer.parseInt(ligneLueSplit[0]);
			int arriveeId = Integer.parseInt(ligneLueSplit[1]);
			int sensRue = Integer.parseInt(ligneLueSplit[2]);
			int tempsParcours = Integer.parseInt(ligneLueSplit[3]);
			int longueur=Integer.parseInt(ligneLueSplit[4]);
			
			Rue rue1 = new Rue(departId, arriveeId, tempsParcours, longueur);
			MainRound.Intersections.get(departId).addIntersectionJoignables(arriveeId, rue1);
			
			if(sensRue==2) {
				Rue rue2= new Rue(arriveeId, departId, tempsParcours, longueur);
				MainRound.Intersections.get(arriveeId).addIntersectionJoignables(departId, rue2);
			}
		}		
		scanner.close();

	}
	
	public static void output(String destinationPath) throws IOException, FileNotFoundException {
		
		//Ecriture dans le fichier
		File f = new File(destinationPath);
		FileWriter fw = new FileWriter(f);
		
		fw.write(MainRound.nbVehicules);
		
		for(Vehicule vehiculeLu : MainRound.vehicules) {
			List<Integer> itineraireLu= vehiculeLu.getItineraire();
			for(int intersectionLue: itineraireLu) {
				fw.write(intersectionLue);
			}
		}
            
		fw.close();
	}

}
