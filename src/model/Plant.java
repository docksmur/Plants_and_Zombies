package model;

import java.io.Serializable;
import java.util.Observable;

/**
 * 
 * a plant
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.4.1
 * 
 */

public class Plant extends Npc implements Serializable{
	
	private int cost;
	
	/**
	 * Create a plant
	 * 
	 * @param type the type of plant
	 * @param health the current health of the plant
	 * @param damage the amount of damage this plant does
	 * @param cost the cost in sun points
	 * @param pnz the observable
	 */
	public Plant(String type, int health, int damage, int cost, int row, int col, Observable pnz){
		super(type, health, damage, row, col, pnz);		//call npc constructor with the passed health damage row col cost and observable
		this.cost=cost;									//set this plants cost
	}

	/**get the cost of the plant
	 * @return the cost of this plant
	 */
	public int getCost() {								//get this plants cost
		return cost;
	}

	/**set the cost of the plant
	 * @param cost set the cost of this plant
	 */
	public void setCost(int cost) {						//set this plants cost
		this.cost = cost;
	}

}
