package model;

import java.util.Observable;

/**
 * 
 * a plant
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.4.1
 * 
 */

public class Plant extends Npc {
	
	int cost;
	
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
		super(type, health, damage, row, col, pnz);
		this.cost=cost;
	}

	/**
	 * @return the cost of this plant
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * @param cost set the cost of this plant
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

}
