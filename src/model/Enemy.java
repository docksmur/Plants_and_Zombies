package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Observable;
import java.util.Stack;

/**
 * 
 * an enemy
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.4.1
 * 
 */

public class Enemy extends Npc implements Serializable{
	
	public final static int LOSE = -1;
	public final static int CONT = 0;
	Stack<Integer> oldCols =  new Stack<Integer>();		//stack of old locations
	Stack<Integer> futureCols =  new Stack<Integer>();	//stack of undone columns
	
	/**
	 * Create a new enemy
	 * 
	 * @param type the type of enemy
	 * @param health the health of the enemy
	 * @param damage the damage of the enemy
	 * @param i the number of the enemy
	 * @param pnz the observable
	 */
	public Enemy(String type, int health, int damage,int row , int col, Observable pnz){
		super(type, health, damage, row, col, pnz);
		pnz.addObserver(this);						//add this to observer list
	}
	
	/**
	 * Move the zombie on the grid
	 * 
	 * @param grid the grid it is moving on
	 * @return whether it reached the end and ate the brains of the user
	 */
	public int move(ArrayList<ArrayList<Npc>> grid){
		if(this.health!=0){		//if this enemy isn't dead
			if (col<=0){		//if the enemy is at the end the game is over and you have lost
				return LOSE;	
			}
			//System.out.println("moving places");
			if(grid.get(row).get(col-1)==null){	//if the next place the zombie is moving to is empty it can move
				grid.get(row).set(col-1, this);	//zombie is in new place
				grid.get(row).set(col, null);	//zombie is no longer in the old spot
				col--;							//zombies column is now one less i.e. on closer to the end
				if(col <= 0){					//if at the end the game is over
					return LOSE;
				}
			}else{
				if(grid.get(row).get(col-1) instanceof Plant){
					if(grid.get(row).get(col-1).damaged(this.damage)!=-1){	//if there is something there attack it
						grid.get(row).set(col-1, null);						//if the hit was a lethal hit the spot is now empty
					}
				}
			}
		}
		return CONT;														//game didn't end
	}
	
	
	@Override
	public void update(Observable o, Object obj) {
		PnZModel pnzm = ((PnZModel)o);				//cast observable as model
		String s = (String) obj;					//get the update message
		if (s.equalsIgnoreCase("move")){			//if it's a move update save the health then save location then move to new location
			super.update(o, obj);
			oldCols.push(col);
			int over = move(pnzm.getGrid()); 	//move this zombie
			if (over==-1){						//end if the zombie is at the end
				pnzm.setRunning(false);
			}
		}
		if (s.equalsIgnoreCase("undo")){		//undo movement
			undo(pnzm);
		}
		if (s.equalsIgnoreCase("redo")){		//redo movement
			redo(pnzm);
		}
	}
	

	/** 
	 * Redoes any movements as well as calling health restoring
	 * 
	 * @param pnzm the model of the game
	 */
	@Override
	public void redo(PnZModel pnzm) {
		if (!futureCols.empty()){				//if you can redo do so
			oldCols.push(col);
			col = futureCols.pop();				//store redo for re-un-doing
			super.redo(pnzm);					//restore health
		}
	}
	
	/** 
	 * Undoes any movements as well as calling health restoring
	 * 
	 * @param pnzm the model of the game
	 */
	@Override
	public void undo(PnZModel pnzm) {
		if (!oldCols.empty()){					//if you can undo do so
			futureCols.push(col);
			col = oldCols.pop();				//store undo for redoing
			super.undo(pnzm);					//restore health
			if (super.futureHealth.peek()==0){
				pnzm.lessRemaining(-1);
			}
		}
	}
	
}
