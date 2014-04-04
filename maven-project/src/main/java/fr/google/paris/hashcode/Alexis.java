package fr.google.paris.hashcode;

import java.util.ArrayList;
import java.util.List;

import fr.google.paris.hashcode.domain.Surface;

public class Alexis {
	
	public static List<String> definePaintOrdersOfSize(int s){
		Surface S;
		int x, y, xBest, yBest;
		double t, tBest;
		List<String> orders = new ArrayList<String>();
		x = 0;
		y = 0;
		t = 0;
		xBest = 0;
		yBest = 0;
		tBest = 0;
		boolean go = true;
		while(go){
			go = false;
			for(x = 0; x < TrialRound.map.length - (2*s+1); ++x)
				for(y = 2*s; y < TrialRound.map[0].length - (2*s+1); ++y){
					S = new Surface(x,y,2*s+1);
					t = S.tauxRemplissage();
					if(t > TrialRound.tauxDeRemplissage && t > tBest && t < 1){
						xBest = x;
						yBest = y;
						tBest = t;
					}
				}
			if(tBest > 0){
				orders.add("PAINTSQ "+xBest+" "+yBest+" "+s);
				S = new Surface(xBest,yBest,2*s+1);
				S.Draw();
				xBest = 0;
				yBest = 0;
				tBest = 0;
				go = true;
			}
		}
		return orders;
	}
	
	public static List<String> definePaintOrders(int sMax){
		List<String> orders = new ArrayList<String>();
		for(int s = sMax; s >= 0; --s)
			orders.addAll(definePaintOrdersOfSize(s));
		return orders;
	}
}
