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
		sunPoints=5;									//this plant generates 5 sun points per turn
	}
	
	@Override
	public int damaged(int damage){				//if this plant dies stop it from making sun points
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
	
	public void undo(PnZModel pnzm){
		super.undo(pnzm);			//call npc undo
		if (pnzm.getGrid().get(row).get(col)==null){	//if this is no longer valid plant disable it
			sunPoints=0;
		}
	}
	
	public void redo(PnZModel pnzm){
		super.redo(pnzm);			//call npc redo
		if (pnzm.getGrid().get(row).get(col)!=null){	//if this is a valid plant ensure it produces sun points
			if (pnzm.getGrid().get(row).get(col) instanceof Sunflower){
				sunPoints=5;
			}
		}
	}

}
