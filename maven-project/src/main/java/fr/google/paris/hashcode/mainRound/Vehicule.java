package fr.google.paris.hashcode.mainRound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class Vehicule {
	
	private final static Logger LOGGER = Logger.getLogger(Vehicule.class);
	
	int tempsRestant;
	
	public HashMap<Integer, Intersection> intersectionsPossibles;
	
	List<Intersection> intersectionsVisitees = new ArrayList<Intersection>();
	
	public Vehicule (int tempsInitial, Intersection positionInitiale ) {
		this.tempsRestant = tempsInitial;
		intersectionsVisitees.add(positionInitiale);
	}
	
	public List<Integer> getItineraire(){
		List<Integer> it = getSubItineraire(intersectionsVisitees.get(0).id,tempsRestant);
		return it;
	}
	
	public List<Integer> getSubItineraire(int intersection, int temps){
		intersectionsPossibles = MainRound.Intersections;
		
		List<Integer> itineraire = new ArrayList<Integer>();
		//itineraire.add(intersection);
		
		// terminaison : on n'a plus le temps
		
		
		
		Set<Integer> intersectionsJoignables = intersectionsPossibles.get(intersection).intersectionsJoignables.keySet();
		HashMap<Integer,List<Integer>> subItineraires = new HashMap<Integer,List<Integer>>();
		HashMap<Integer,Integer> subScores = new HashMap<Integer,Integer>();
		List<Integer> currentSubItineraire;
		Integer currentSubScore;
		for(Integer i : intersectionsJoignables){
			currentSubItineraire = new ArrayList<Integer>();
			currentSubItineraire.add(intersection);
			currentSubItineraire.addAll(getSubItineraire(i));
		}
		return itineraire;
	}
	
	public int getScore(List<Integer> itineraire){
		int result = 0;
		int lastIntersection = itineraire.get(0);
		for(int i : itineraire) if(i != 0){
			result += intersectionsPossibles.get(lastIntersection).intersectionsJoignables.get(i).longueur;
			lastIntersection = i;
		}
		return result;
	}
	
	public int calculateScore(List<Integer> itineraire){ // & update intersectionsPossibles
		int lastIntersection = itineraire.get(0);
		int result = 0;
		for(int i : itineraire) if(i != 0){
			result += intersectionsPossibles.get(lastIntersection).intersectionsJoignables.get(i).longueur;
			intersectionsPossibles.get(lastIntersection).intersectionsJoignables.get(i).longueur = 0;
			lastIntersection = i;
		}
		return result;
	}
}
