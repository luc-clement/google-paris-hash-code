package fr.google.paris.hashcode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import fr.google.paris.hashcode.domain.Surface;

public class TrialRound {
	
	public static boolean[][] map;
	public static boolean[][] isDrawn;
	public static double tauxDeRemplissage = 0.7;
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		// Maxime : init the map (by reading the input file)
		Maxime.init("/home/volodia/doodle.txt");
		
		int width = map.length;
		int height = map[0].length;
		int petitCote = Math.min(height, width);
		int Smax = petitCote % 2 == 0 ? (petitCote - 2) / 2 : (petitCote - 1)/2;

		List<String> paintOrders = Alexis.definePaintOrders(Smax);
		List<String> eraseOrders = Louis.defineEraseOrders();

		Maxime.output("result.txt", paintOrders, eraseOrders);
	}

}
