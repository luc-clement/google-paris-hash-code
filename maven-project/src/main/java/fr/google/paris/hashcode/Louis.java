package fr.google.paris.hashcode;

import java.util.ArrayList;
import java.util.List;


public class Louis {
	
	public Louis(){
		super();
	}

	public static List<String> defineEraseOrders(){
		List<String> erase = new ArrayList<String>();
		for (int i=0;i<716;i++){
			for(int j=0;j<1522;j++){
				if(TrialRound.map[i][j]!=TrialRound.isDrawn[i][j]){
					String s="ERASECELL " + i + " " + j;
					erase.add(s);
				}
			}
		}
		return erase;	
	}
	
}
