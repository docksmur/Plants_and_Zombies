package model;

import java.util.ArrayList;
import java.util.Observable;

/**
 * 
 * an enemy
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.4.1
 * 
 */

public class Enemy extends Npc {
	
	int enNumber=100;		//enemy number to help identify

	/**
	 * Create a new enemy
	 * 
	 * @param type the type of enemy
	 * @param health the health of the enemy
	 * @param damage the damage of the enemy
	 * @param i the number of the enemy
	 * @param pnz the observable
	 */
	public Enemy(String type, int health, int damage, int i, Observable pnz){
		super(type, health, damage, pnz);
		this.enNumber = i;
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
		for (ArrayList<Npc> n:grid){
			for (Npc e:n){
				if (e instanceof Enemy){				//go through the npc grid to find this object
					Enemy en = (Enemy) e;
					if (this.equals(en)){
						int place = n.indexOf(e);
						if(place==0){					//if the place is 0 the game is over
							return -1;					//game over
						}else{
							if(n.get(place-1)==null){	//if the next place is empty move there
								n.set(place, null);
								n.set(place-1, e);
								if(place==1){
									return -1;			//if the place you move to is the last col the game is over
								}
								return 1; 				//moved
							}else {
								if(n.get(place-1).damaged(this.getDamage())!=-1){	//if it's a lethal hit remove the plant
									if(n.get(place-1) instanceof Sunflower){		//if it's a sunflower make sure it stops producing
										Sunflower s = (Sunflower) n.get(place-1);
										s.setDisabled();
									}
									n.set(place-1,null);
									return 50;					//hit and killed
								}else{
									return 25; 					//hit but didn't kill
								}
							}
						}
					}
				}
			}
		}
		return 0; 												//wasn't this zombie
	}
	
	@Override
	public void update(Observable o, Object obj) {
		PnZModel thing = ((PnZModel)o);
		int over = move(thing.getGrid()); 	//move this zombie
		if (over==-1){						//end if the zombie is at the end
			thing.setRunning(false);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enemy other = (Enemy) obj;
		if (enNumber != other.enNumber)
			return false;
		return true;
	}

	/**
	 * Get the number of the enemy
	 * 
	 * @return the enemy's number
	 */
	public int getEnNumber() {
		return enNumber;
	}

	/**
	 * Set the number of the enemy
	 * 
	 * @param number the new enemy number
	 */
	public void setNumber(int number) {
		this.enNumber = number;
	}	
	
}
