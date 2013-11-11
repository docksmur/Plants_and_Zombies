package model;

import java.util.Observable;

/**
 * 
 * a pea shooter plant
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.4.1
 * 
 */

public class PeaShooter extends Plant {

	/**
	 * Create a pea shooter
	 * 
	 * @param pnz the pbservable class this will be observing
	 */
	public PeaShooter(Observable pnz){
		super("Pea Shooter",1, 3, 5, pnz);
		pnz.addObserver(this);
	}
	
}
