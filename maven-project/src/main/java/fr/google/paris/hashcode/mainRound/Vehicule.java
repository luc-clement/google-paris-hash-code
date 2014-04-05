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
		itineraireFinal = new ArrayList<Integer>();
		itineraireFinal.add(positionInitiale.id);
	}

	// depart excluded from path
	public List<Integer> getBestSubItineraire(int departID, int remainingMoves, int remainingTime) {
		//LOGGER.info(departID +" "+ remainingMoves +" "+ remainingTime);

		List<Integer> bestItineraire = new ArrayList<Integer>();
		if(remainingMoves == 0 || remainingTime <= 0)
			return bestItineraire;

		Intersection depart = MainRound.Intersections.get(departID);
		HashMap<Integer,List<Integer>> itineraires = new HashMap<Integer,List<Integer>>();

		//LOGGER.info("nb intersections joignables : " + depart.intersectionsJoignables.keySet().size());
		for(int i : depart.intersectionsJoignables.keySet()){
			if(remainingTime - depart.intersectionsJoignables.get(i).tempsParcours < 0)
				continue;
			itineraires.put(i, new ArrayList<Integer>());
			itineraires.get(i).add(i);
			itineraires.get(i).addAll(getBestSubItineraire(i,remainingMoves-1,remainingTime - depart.intersectionsJoignables.get(i).tempsParcours));
		}

		double bestRatio = 0.;
		double currentRatio = 0.;
		int bestLongueur = 0;
		int currentLongueur = 0;
		// TODO calcul bestRatio en prenant en compte le depart !
		for(int i : itineraires.keySet()){
			//			currentRatio = ratioItineraire(itineraires.get(i));
			currentLongueur = longueurParcours(departID, itineraires.get(i));
//			LOGGER.info(itineraires.get(i).size());
//			if(currentRatio >= bestRatio){
//				bestItineraire = itineraires.get(i);
//				bestRatio = currentRatio;
//			}
			if (currentLongueur >= bestLongueur) {
				bestLongueur = currentLongueur;
				bestItineraire = itineraires.get(i);
			}
		}
		return bestItineraire;
	}

	public List<Integer> getItineraire(){
		List<Integer> currentSubItineraire;
		while(tempsRestant > 0){
			//LOGGER.info("temps restant : " + tempsRestant);
			currentSubItineraire = getBestSubItineraire(itineraireFinal.get(itineraireFinal.size()-1), 13,tempsRestant);
			if (currentSubItineraire.isEmpty()) {
				//LOGGER.info("empty subitineraire");
				break;
			}
			itineraireFinal.addAll(currentSubItineraire);
			tempsRestant = MainRound.tempsVirtuel - tempsParcours(itineraireFinal);
			MainRound.supprimerScoreParcours(itineraireFinal);
		}
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
			result += MainRound.Intersections.get(itineraire.get(i)).intersectionsJoignables.get(itineraire.get(i+1)).tempsParcours;
		}

		return result;
	}

	public int longueurParcours(int depart, List<Integer> itineraire) {
		int distanceParcours = 0;
		if (itineraire.isEmpty())
			return 0;

		List<Point> ruesVisitees = new ArrayList<Point>();

		distanceParcours += MainRound.Intersections.get(depart).intersectionsJoignables.get(itineraire.get(0)).longueur;
		ruesVisitees.add(new Point(itineraire.get(0), depart));
		ruesVisitees.add(new Point(depart, itineraire.get(0)));



		for (int i=0; i<itineraire.size()-1; ++i) {
			if (!ruesVisitees.contains(new Point(itineraire.get(i), itineraire.get(i+1))))
				distanceParcours += MainRound.Intersections.get(itineraire.get(i)).intersectionsJoignables.get(itineraire.get(i+1)).longueur;

			ruesVisitees.add(new Point(itineraire.get(i), itineraire.get(i+1)));
			ruesVisitees.add(new Point(itineraire.get(i+1), itineraire.get(i)));
		}
		return distanceParcours;
	}

}
