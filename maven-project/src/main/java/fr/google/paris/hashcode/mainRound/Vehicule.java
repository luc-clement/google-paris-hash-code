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
		List<Integer> intersectionsExclues = new ArrayList<Integer>();
		intersectionsExclues.add(intersectionsVisitees.get(0).id);
		itineraireFinal = getSubItineraire(intersectionsVisitees.get(0).id,tempsRestant, intersectionsExclues);
		return itineraireFinal;
	}
	
	public List<Integer> getItineraireFinal() {
		if (itineraireFinal != null)
			return itineraireFinal;
		
		itineraireFinal = new ArrayList<Integer>();
		for (Intersection intersection : intersectionsVisitees) {
			itineraireFinal.add(intersection.id);
		}
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
		for(int i : itineraire) if(i != lastIntersection){
			result += MainRound.Intersections.get(lastIntersection).intersectionsJoignables.get(i).longueur;
			MainRound.Intersections.get(lastIntersection).intersectionsJoignables.get(i).longueur = 0;
			lastIntersection = i;
		}
		return result;
	}
	
	
	
	
	
	
	
	public void defineItineraire() {
		while (canStillMove()) {
			int bestNextIntersection = bestNextIntersection();
			if (bestNextIntersection != -1) {
				tempsRestant -= intersectionsVisitees.get(intersectionsVisitees.size()-1).intersectionsJoignables.get(bestNextIntersection).tempsParcours;
				intersectionsVisitees.add(MainRound.Intersections.get(bestNextIntersection));
				intersectionsVisitees.get(intersectionsVisitees.size() - 2).rueParcourue(bestNextIntersection);
			} else {
				LOGGER.info("Can't move anymore.");
				break;
			}
		}
	}
	
	public int bestNextIntersection() {
		Intersection positionActuelle = intersectionsVisitees.get(intersectionsVisitees.size()-1);
		
		int result = -1;
		Double bestRatio = 150000.;
		
		for (int i : positionActuelle.intersectionsJoignables.keySet()) {
			if (positionActuelle.intersectionsJoignables.get(i).tempsParcours <= tempsRestant) {
				Double ratio = ((double) positionActuelle.intersectionsJoignables.get(i).tempsParcours) / ((double) positionActuelle.intersectionsJoignables.get(i).longueur);
				if (ratio <= bestRatio) {
					result = i;
					bestRatio = ratio;
				}
			}
		}

		return result;

	}
	
	public boolean canStillMove() {
		Intersection positionActuelle = intersectionsVisitees.get(intersectionsVisitees.size()-1);
		for (int i : positionActuelle.intersectionsJoignables.keySet()) {
			if (positionActuelle.intersectionsJoignables.get(i).tempsParcours <= tempsRestant)
				return true;
		}
		return false;
	}
}
