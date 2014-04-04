package fr.google.paris.hashcode.domain;

import fr.google.paris.hashcode.TrialRound;


public class Surface {

	private int x;
	private int y;
	private int width;
	
	
	public double tauxRemplissage() {
		int numberOfPaintedCases = 0;
		
		for (int i=0; i<width; ++i)
			for (int j=0; j<width; j++)
				if (TrialRound.map[x+i][y+j] == true)
					++numberOfPaintedCases;
		
		return ((double) numberOfPaintedCases) / ((double) (width*width));
	}
	
	public void Draw () {
		for (int i=0; i<width; ++i)
			for (int j=0; j<width; j++)
				TrialRound.isDrawn[x+i][y+j] = true;
	}
	
	public boolean isDrawn() {
		for (int i=0; i<width; ++i)
			for (int j=0; j<width; j++)
				if (TrialRound.isDrawn[x+i][y+j] == false)
					return false;
		return true;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public Surface() {
		super();
	}
	public Surface(int x, int y, int width) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
	}
}
