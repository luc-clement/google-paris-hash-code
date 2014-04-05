package fr.google.paris.hashcode.mainRound;

import java.util.HashMap;
import java.util.Map;

public class Djikstra {
	private Intersection source;
	private Integer[] temps=new Integer[17958];
	private Map<Integer, Intersection> parcours = new HashMap<>();
	private Map<Integer, Intersection> C=new HashMap<>();
	private Map<Integer, Intersection> D=new HashMap<>();
	
	public Djikstra(){
		super();
	}
	
	public Djikstra(Intersection source){
		super();
		this.source=source;
		temps[0]=0;
		this.parcours.put(0,source);
		for (int i=1;i<17958;i++){
			if(this.source.intersectionsJoignables.containsKey(i))
				temps[i]=this.source.intersectionsJoignables.get(i).tempsParcours;
			else
				temps[i]=Integer.MAX_VALUE;
			this.parcours.put(i,source);
		}
		
		this.C=MainRound.Intersections;
		this.C.remove(this.source.id);
		this.D.put(source.id,source);	
		}
	
	public void compute(){
		while(!C.isEmpty()){
			Integer s_index=-1;
			for (int i=1;i<17958;i++){
				if(this.C.containsKey(i)){
					s_index=i;
					break;
				}
				}
			
		    for (int i = 1; i < temps.length; i++) {
		    	if (temps[i] < temps[s_index] && this.C.containsKey(i)) {
		    		s_index=i;
		          }
		      }
		    D.put(s_index, C.get(s_index));
		    C.remove(s_index);
		    
		    
		    for (int i=1;i<17958;i++){
		    	if(D.get(s_index).intersectionsJoignables.containsKey(i)){
		    		Integer t_st=D.get(s_index).intersectionsJoignables.get(i).tempsParcours;
		    		if(temps[s_index]+t_st < temps[i])
		    			temps[i]=temps[s_index]+t_st;
		    	}
		    		
		    }
		}
		
	}
}
	
	



