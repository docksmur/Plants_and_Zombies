package model;

import java.util.Observable;
import java.util.Observer;

public class Sunflower extends Plant implements Observer{
	
	public Sunflower(Observable pnz){
		super("Sunflower",1, 0, 1, pnz);
	}
	
	public void play(Observable arg0){
		PnZModel game = (PnZModel) arg0;
		game.addSunPoints(5);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		play(arg0);
		
	}
	

}
