package fr.google.paris.hashcode.mainRound;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	public List<Integer> moveTo(int nIntersectionsMax){
		List<Integer> path = new ArrayList<Integer>();

		return path;
	}

	// depart excluded from path
	public List<Integer> getBestSubItineraire(int departID, int remainingMoves, int remainingTime){
		List<Integer> bestItineraire = new ArrayList<Integer>();
		if(remainingMoves == 0 || remainingTime <= 0)
			return bestItineraire;

		Intersection depart = MainRound.Intersections.get(departID);
		HashMap<Integer,List<Integer>> itineraires = new HashMap<Integer,List<Integer>>();

		for(int i : depart.intersectionsJoignables.keySet()){
			itineraires.put(i, new ArrayList<Integer>());
			itineraires.get(i).add(i);
			itineraires.get(i).addAll(getBestSubItineraire(i,remainingMoves-1,remainingTime - depart.intersectionsJoignables.get(i).tempsParcours));
		}

		double bestRatio = 0;
		double currentRatio = 0;
		// TODO calcul bestRatio en prenant en compte le depart !
		for(int i : itineraires.keySet()){
			currentRatio = ratioItineraire(itineraires.get(i));
			if(currentRatio >= bestRatio){
				bestItineraire = itineraires.get(i);
				bestRatio = currentRatio;
			}
		}
		return bestItineraire;
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
		//LOGGER.info(departID);
		Intersection depart = MainRound.Intersections.get(departID);
		HashMap<Integer,Integer> scores = new HashMap<Integer,Integer>();
		HashMap<Integer,List<Integer>> itineraires = new HashMap<Integer,List<Integer>>();

		List<Integer> currentItineraire;
		List<Integer> currentIntersectionsExclues;
		int nouveauTempsRestant;
		int intersectionBest1 = -1;
		int bestScore1 = 0;
		int intersectionBest2 = -1;
		int bestScore2 = 0;
		int currentScore = 0;
		for(int i : depart.intersectionsJoignables.keySet()){
			currentScore = depart.intersectionsJoignables.get(i).tempsParcours;
			nouveauTempsRestant = tempsRestant - currentScore;
			if(!intersectionsExclues.contains(i) && nouveauTempsRestant >= 0 && currentScore >= bestScore1){
				bestScore1 = currentScore;
				intersectionBest1 = i;
			}
		}
		//LOGGER.info("bestScore1 " + bestScore1);

		for(int i : depart.intersectionsJoignables.keySet()){
			currentScore = depart.intersectionsJoignables.get(i).tempsParcours;
			nouveauTempsRestant = tempsRestant - currentScore;
			if(!intersectionsExclues.contains(i) && nouveauTempsRestant >= 0 && currentScore >= bestScore2 && i != intersectionBest1){
				bestScore2 = currentScore;
				intersectionBest2 = i;
			}
		}
		//LOGGER.info("bestScore2 " + bestScore2);

		if(intersectionBest1 != -1){
			scores.put(intersectionBest1, 0);
			itineraires.put(intersectionBest1, new ArrayList<Integer>());
			itineraires.get(intersectionBest1).add(intersectionBest1);

			nouveauTempsRestant = tempsRestant - depart.intersectionsJoignables.get(intersectionBest1).tempsParcours;
			if(!intersectionsExclues.contains(intersectionBest1) && nouveauTempsRestant >= 0){
				currentIntersectionsExclues = new ArrayList<Integer>();
				currentIntersectionsExclues.add(intersectionBest1);
				currentIntersectionsExclues.addAll(intersectionsExclues);
				currentItineraire = getSubItineraire(intersectionBest1,nouveauTempsRestant,currentIntersectionsExclues);
				itineraires.get(intersectionBest1).addAll(currentItineraire);
				scores.put(intersectionBest1,calculateScore(itineraires.get(intersectionBest1)));
			}
		}
		if(intersectionBest2 != -1){
			scores.put(intersectionBest2, 0);
			itineraires.put(intersectionBest2, new ArrayList<Integer>());
			itineraires.get(intersectionBest2).add(intersectionBest2);

			nouveauTempsRestant = tempsRestant - depart.intersectionsJoignables.get(intersectionBest2).tempsParcours;
			if(!intersectionsExclues.contains(intersectionBest2) && nouveauTempsRestant >= 0){
				currentIntersectionsExclues = new ArrayList<Integer>();
				currentIntersectionsExclues.add(intersectionBest2);
				currentIntersectionsExclues.addAll(intersectionsExclues);
				currentItineraire = getSubItineraire(intersectionBest2,nouveauTempsRestant,currentIntersectionsExclues);
				itineraires.get(intersectionBest2).addAll(currentItineraire);
				scores.put(intersectionBest2,calculateScore(itineraires.get(intersectionBest2)));
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

	public List<Integer> getSubItineraireHardComplexity(int departID, int tempsRestant, List<Integer> intersectionsExclues){ // depart must be in IntersectionsVisitees ?
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
				Double ratio = ((double) positionActuelle.intersectionsJoignables.get(i).longueur) == 0 ? 149000. : ((double) positionActuelle.intersectionsJoignables.get(i).tempsParcours) / ((double) positionActuelle.intersectionsJoignables.get(i).longueur);
				//LOGGER.info("ratio : " + ratio);
				if (ratio <= bestRatio) {
					result = i;
					bestRatio = ratio;
					//LOGGER.info("Ratio plus interessant, on ajoute l'intersection");
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

	// Longueur / tempsParcours
	public Double ratioItineraire(List<Integer> itineraire) {
		Double tempsParcours = 0.;
		Double distanceParcours = 0.;

		List<Point> ruesVisitees = new ArrayList<Point>();

		for (int i=0; i<itineraire.size()-1; ++i) {
			if (!ruesVisitees.contains(new Point(i, i+1)))
				distanceParcours += MainRound.Intersections.get(i).intersectionsJoignables.get(i+1).longueur;

			tempsParcours += MainRound.Intersections.get(i).intersectionsJoignables.get(i+1).tempsParcours;

			ruesVisitees.add(new Point(i, i+1));
			ruesVisitees.add(new Point(i+1, i));
		}

		return (distanceParcours / tempsParcours);

	}
	
	public int tempsParcours(List<Integer> itineraire) {
		int result = 0;
		
		for (int i=0; i<itineraire.size()-1; ++i) {
			result += MainRound.Intersections.get(i).intersectionsJoignables.get(i+1).tempsParcours;
		}
		
		return result;
	}
}
