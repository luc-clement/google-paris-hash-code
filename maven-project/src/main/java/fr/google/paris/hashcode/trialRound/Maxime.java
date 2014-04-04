package fr.google.paris.hashcode.trialRound;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Maxime {


	public static void init(String filePath) throws IOException, FileNotFoundException {
		int length;
		int width;

		Scanner scanner = new Scanner(new File(filePath));
		length = Integer.parseInt(scanner.next());
		width = Integer.parseInt(scanner.next());
		
		TrialRound.map = new boolean[length][width];
		TrialRound.isDrawn = new boolean[length][width];
		for (int i=0; i< length; ++i)
			for (int j=0; j<width; ++j)
				TrialRound.isDrawn[i][j] = false;
		
		scanner.nextLine();
		for (int i=0;i<length;i++) {
			String ligne = scanner.nextLine();
			for(int j=0;j<width;j++){
				char c=ligne.charAt(j);
				if(c=='.') TrialRound.map[i][j]=false;
				else TrialRound.map[i][j]=true;
			}
		}
		
		scanner.close();

	}
	
	public static void output(String destinationPath, List<String> paint, List<String> erase) throws IOException, FileNotFoundException {
		int nbOperations = paint.size()+erase.size();
		File f = new File(destinationPath);
		FileWriter fw = new FileWriter(f);
		fw.write(nbOperations);
		for(String s : paint) {
			fw.write(s);
		}
		for(String s : erase) {
			fw.write(s);
		}
		fw.close();
	}

}
