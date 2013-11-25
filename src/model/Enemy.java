package model;

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

public class Enemy extends Npc {
	
	Stack<Integer> oldCols =  new Stack<Integer>();
	Stack<Integer> futureCols =  new Stack<Integer>();
	
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
		pnz.addObserver(this);
	}
	
	/**
	 * Move the zombie on the grid
	 * 
	 * @param grid the grid it is moving on
	 * @return whether it reached the end and ate the brains of the user
	 */
	/**
	 * @param grid
	 * @return
	 */
	public int move(ArrayList<ArrayList<Npc>> grid){
		if(this.health!=0){
			if (col<=0){
				return -1;	
			}
			System.out.println("moving places");
			if(grid.get(row).get(col-1)==null){
				grid.get(row).set(col-1, this);
				grid.get(row).set(col, null);
				col--;
				if(col <= 0){
					return -1;
				}
			}else{
				if(grid.get(row).get(col-1).damaged(this.damage)!=-1){
					grid.get(row).set(col-1, null);
				}
			}
		}
		return 0;
	}
	
	@Override
	public void update(Observable o, Object obj) {
		PnZModel pnzm = ((PnZModel)o);
		String s = (String) obj;
		if (s.equalsIgnoreCase("move")){
			super.update(o, obj);
			oldCols.push(col);
			int over = move(pnzm.getGrid()); 	//move this zombie
			if (over==-1){						//end if the zombie is at the end
				pnzm.setRunning(false);
			}
		}
		if (s.equalsIgnoreCase("undo")){
			if (!oldCols.empty()){
				futureCols.push(oldCols.peek());
				col = oldCols.pop();
				super.undo(pnzm);
			}
		}
		if (s.equalsIgnoreCase("redo")){
			if (!futureCols.empty()){
				oldCols.push(futureCols.peek());
				col = futureCols.pop();
				super.redo(pnzm);
			}
		}
	}
	
	
}
