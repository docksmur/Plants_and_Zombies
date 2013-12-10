package model;

import java.io.Serializable;
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

public class Sunflower extends Plant implements Observer, Serializable{
	
	private int sunPoints;
	
	/**
	 * create new sunflower
	 * 
	 * @param pnz the observable
	 */
	public Sunflower(Observable pnz, int row, int col){
		super("Sunflower",1, 0, 1, row, col, pnz);
		pnz.addObserver(this);
		sunPoints=5;									//this plant generates 5 sun points per turn
	}
	
	/** stop the sunflower producing sun points when destroyed
	 * @see model.Npc#damaged(int)
	 */
	@Override
	public int damaged(int damage){				//if this plant dies stop it from making sun points
		int i = super.damaged(damage);
		if (i!=-1){
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
		super.update(o, obj);				//call npcupdate
		PnZModel pnzm = (PnZModel) o;		//cast observable as model
		if (((String)obj).equalsIgnoreCase("plants")){		//if it is a plant update get plants to do damage
			play(pnzm);
		}else if (((String)obj).equalsIgnoreCase("undo")){	//if it is an undo update undo
			undo(pnzm);
		}else if (((String)obj).equalsIgnoreCase("redo")){	//if it is a redo update redo
			redo(pnzm);
		}
	}
	
	/**
	 * disable the addition of sun points (called when destroyed)
	 */
	public void setDisabled(){	//diasble the plant from making sun points
		sunPoints=0;
	}
	
	/** undoes stuff that this did and was done to it
	 * @see model.Npc#undo(model.PnZModel)
	 */
	@Override
	public void undo(PnZModel pnzm){
		super.undo(pnzm);			//call npc undo
		if (pnzm.getGrid().get(row).get(col)==null){	//if this is no longer valid plant disable it
			sunPoints=0;
		}
	}
	
	/** redoes stuff that this did and was done to it
	 * @see model.Npc#undo(model.PnZModel)
	 */
	public void redo(PnZModel pnzm){
		super.redo(pnzm);			//call npc redo
		if (pnzm.getGrid().get(row).get(col)!=null){	//if this is a valid plant ensure it produces sun points
			if (pnzm.getGrid().get(row).get(col) instanceof Sunflower){
				sunPoints=5;
			}
		}
	}

	/**get the amount of sun points this flower makes
	 * @return the amount of sunflowers it produces
	 */
	public int getSunPoints() {
		return sunPoints;
	}

}
