package model;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * 
 * a pea shooter plant
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.4.1
 * 
 */

public class PeaShooter extends Plant implements Observer, Serializable {
	
	/**
	 * Create a pea shooter
	 * 
	 * @param pnz the observable class this will be observing
	 */
	public PeaShooter(Observable pnz, int row, int col){
		super("Pea Shooter",1, 2, 5, row, col, pnz);
		pnz.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object obj){
		PnZModel pnzm = (PnZModel) o;						//cast observable as model
		if (((String)obj).equalsIgnoreCase("plants")){		//if this is a plants update store health then damage first valid enemy
			super.update(o, obj);
			damage(pnzm);
		}else if (((String)obj).equalsIgnoreCase("undo")){	//if it's an undo update undo
			undo(pnzm);
		}else if (((String)obj).equalsIgnoreCase("redo")){	//if it's a redo update redo
			redo(pnzm);
		}
	}

	private void damage(PnZModel pnzm) {
		int i = pnzm.firstInRow(row);		//find first enemy
		if (i<5 && i>col){					//if it's in a valid location do damage
			//System.out.println("damaging zombie");
			if(pnzm.getGrid().get(row).get(i).damaged(this.damage)!=-1){	//do damage
				pnzm.getGrid().get(row).set(i, null);						//remove if it's a lethal blow
				pnzm.lessRemaining(1);										//now there's one less remaining enemy
			}
		}
	}
	
	@Override
	public void undo(PnZModel pnzm){
		super.undo(pnzm);								//call npc's undo
		if (pnzm.getGrid().get(row).get(col)==null){	//if there is no longer an item in this location that means it's been removed
			damage=0;									//if it's not there it can't do damage
		}
		if (pnzm.getGrid().get(row).get(col)!=null){						//if theres a peashoter in this location it is valid
			if (pnzm.getGrid().get(row).get(col) instanceof PeaShooter){
				damage=2;													//valid peashooters do 2 damage so make sure they're doing that
			}
		}
	}
	
	@Override
	public void redo(PnZModel pnzm){
		super.redo(pnzm);													//call npc's redo
		if (pnzm.getGrid().get(row).get(col)!=null){						//if theres a peashoter in this location it is valid
			if (pnzm.getGrid().get(row).get(col) instanceof PeaShooter){
				damage=2;													//valid peashooters do 2 damage so make sure they're doing that
			}
		}
		if (pnzm.getGrid().get(row).get(col)==null){	//if there is no longer an item in this location that means it's been removed
			damage=0;									//if it's not there it can't do damage
		}
	}
	
	
	@Override
	public int damaged(int damage){				//do damage
		int i = super.damaged(damage);
		if (i != -1){							//if it dies it can't do damage any more
			damage = 0;
		}
		return i;
	}
	
}
