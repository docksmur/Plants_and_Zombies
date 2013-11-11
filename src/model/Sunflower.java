package model;

import java.util.Observable;
import java.util.Observer;

/**
 * 
 * a sunflower plant
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.4.1
 * 
 */

public class Sunflower extends Plant implements Observer{
	
	public int sunPoints;
	
	/**
	 * create new sunflower
	 * 
	 * @param pnz the observable
	 */
	public Sunflower(Observable pnz){
		super("Sunflower",1, 0, 1, pnz);
		pnz.addObserver(this);
		sunPoints=5;
	}
	
	
	
	/**
	 * add sun points
	 * 
	 * @param arg0 the observable
	 */
	public void play(Observable arg0){
		PnZModel game = (PnZModel) arg0;
		game.addSunPoints(sunPoints);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		play(arg0);
	}
	
	/**
	 * disable the addition of sun points (called when destroyed)
	 */
	public void setDisabled(){
		sunPoints=0;
	}
	

}
