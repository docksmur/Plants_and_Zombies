package model;

import java.util.Observable;

public class PeaShooter extends Plant {

	public PeaShooter(Observable pnz){
		super("Pea Shooter",1, 3, 5, pnz);
		pnz.addObserver(this);
	}
	
}
