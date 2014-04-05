package fr.google.paris.hashcode.mainRound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class Vehicule {
	
	private final static Logger LOGGER = Logger.getLogger(Vehicule.class);
	
	int tempsRestant;
	
	List<Intersection> intersectionsVisitees = new ArrayList<Intersection>();
	
	List<Integer> itineraireFinal;
	
	public Vehicule (int tempsInitial, Intersection positionInitiale ) {
		this.tempsRestant = tempsInitial;
		intersectionsVisitees.add(positionInitiale);
	}
	
	public List<Integer> getItineraire(){
		List<Integer> it = getSubItineraire(intersectionsVisitees.get(0).id,tempsRestant, new ArrayList<Integer>());
		itineraireFinal = it;
		return it;
	}
	
	public List<Integer> getItineraireFinal() {
		return itineraireFinal;
	}
	
	public List<Integer> getSubItineraire(int departID, int tempsRestant, List<Integer> intersectionsExclues){ // depart must be in IntersectionsVisitees ?
		LOGGER.info(departID);
		Intersection depart = MainRound.Intersections.get(departID);
		HashMap<Integer,Integer> scores = new HashMap<Integer,Integer>();
		HashMap<Integer,List<Integer>> itineraires = new HashMap<Integer,List<Integer>>();
		
		List<Integer> currentItineraire;
		List<Integer> currentIntersectionsExclues;
		int nouveauTempsRestant;
		for(int i : depart.intersectionsJoignables.keySet()){
			
			// init scores & itineraires de i
			scores.put(i, 0);
			itineraires.put(i, new ArrayList<Integer>());
			itineraires.get(i).add(i);
			
			nouveauTempsRestant = tempsRestant - depart.intersectionsJoignables.get(i).tempsParcours;
			if(!intersectionsExclues.contains(i) && nouveauTempsRestant >= 0){
				currentIntersectionsExclues = new ArrayList<Integer>();
				currentIntersectionsExclues.add(departID);
				currentIntersectionsExclues.add(i);
				currentIntersectionsExclues.addAll(intersectionsExclues);
				currentItineraire = getSubItineraire(i,nouveauTempsRestant,currentIntersectionsExclues);
				itineraires.get(i).addAll(currentItineraire);
				scores.put(i,calculateScore(itineraires.get(i)));
			}
		}
		int bestScore = 0;
		List<Integer> bestItineraire = new ArrayList<Integer>();
		for(int i : scores.keySet()){
			if(scores.get(i) > bestScore)
				bestItineraire = itineraires.get(i);
		}
		return bestItineraire;
	}
	
	public int calculateScore(List<Integer> itineraire){ // & update intersectionsPossibles
		int lastIntersection = itineraire.get(0);
		int result = 0;
		for(int i : itineraire) if(i != 0){
			result += MainRound.Intersections.get(lastIntersection).intersectionsJoignables.get(i).longueur;
			MainRound.Intersections.get(lastIntersection).intersectionsJoignables.get(i).longueur = 0;
			lastIntersection = i;
		}
		return result;
	}
}
