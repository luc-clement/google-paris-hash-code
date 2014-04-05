package fr.google.paris.hashcode.mainRound;

import java.util.HashMap;

public class Intersection {
	
	int id;
	double latitude;
	double longitude;
	HashMap<Integer, Rue> intersectionsJoignables = new HashMap<Integer, Rue>();

	
	public Intersection(int id, double latitude, double longitude) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
