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
	public Sunflower(Observable pnz, int row, int col){
		super("Sunflower",1, 0, 1, row, col, pnz);
		pnz.addObserver(this);
		sunPoints=5;
	}
	
	@Override
	public int damaged(int damage){
		int i = super.damaged(damage);
		if (i==-1){
			this.setDisabled();
		}
		return i;
	}
	
	
	
	/**
	 * add sun points
	 * 
	 * @param arg0 the observable
	 */
	public void play(PnZModel game){
		game.addSunPoints(sunPoints);		//add sun points to the game
	}
	
	@Override
	public void update(Observable o, Object obj) {
		super.update(o, obj);
		PnZModel pnzm = (PnZModel) o;
		if (((String)obj).equalsIgnoreCase("plants")){
			play(pnzm);
		}else if (((String)obj).equalsIgnoreCase("undo")){
			undo(pnzm);
		}else if (((String)obj).equalsIgnoreCase("redo")){
			redo(pnzm);
		}
	}
	
	/**
	 * disable the addition of sun points (called when destroyed)
	 */
	public void setDisabled(){
		sunPoints=0;
	}
	
	public void undo(PnZModel pnzm){
		super.undo(pnzm);
		if (pnzm.getGrid().get(row).get(col)==null){
			sunPoints=0;
		}
	}
	
	public void redo(PnZModel pnzm){
		super.redo(pnzm);
		if (pnzm.getGrid().get(row).get(col)!=null){
			if (pnzm.getGrid().get(row).get(col) instanceof Sunflower){
				sunPoints=5;
			}
		}
	}

}
