package fr.google.paris.hashcode.mainRound;

import java.util.ArrayList;
import java.util.List;

public class Vehicule {
	
	int tempsRestant;
	List<Intersection> intersectionsVisitees = new ArrayList<Intersection>();
	
	public Vehicule (int tempsInitial, Intersection positionInitiale ) {
		this.tempsRestant = tempsInitial;
		intersectionsVisitees.add(positionInitiale);
	}
}
