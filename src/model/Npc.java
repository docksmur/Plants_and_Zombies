package model;

import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

/**
 * 
 * an npc
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.4.1
 * 
 */

public class Npc extends Observable implements Observer{
	
	protected String type;	//name of this type of npc
	protected int health;		//current health of the npc
	protected int damage;		//the amount of damage the npc can do
	protected int row;
	protected int col;
	protected Stack<Integer> oldHealth =  new Stack<Integer>();
	protected Stack<Integer> futureHealth =  new Stack<Integer>();
	
	/**
	 * Create an Npc
	 * 
	 * @param type the type of the npc
	 * @param health the health of the npc
	 * @param damage the damage of the npc
	 * @param pnz the observable
	 */
	public Npc(String type, int health, int damage, int row, int col, Observable pnz){
		this.type = type;		//set the type of npc
		this.health = health;	//set its starting health
		this.damage = damage;	//set the amount of damage
		this.row = row;
		this.col = col;
	}
	

	@Override
	public boolean equals(Object obj) {
		//System.out.println("equal compare");
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Npc other = (Npc) obj;
		if (damage != other.damage)
			return false;
		if (health != other.health)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	/**
	 * Get the Npc type
	 * 
	 * @return the type of npc
	 */
	public String getType() {
		return type;
	}

	/**
	 * Get the Npc health
	 * 
	 * @return the health of the npc
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Get the Npc damage
	 * 
	 * @return the damage of the npc
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Apply damage to the Npc
	 * 
	 * @param damage the damage to be done to this npc
	 * @return -1 if it wasn't a deadly hit or the extra damage if it was a deadly hit
	 */
	public int damaged(int damage){
		if (damage>=health){		//if it is lethal damage return the excess damage
			health = 0;
			return damage-health;
		}else{						//otherwise just subtract the damage
			health-=damage;
			return -1;
		}
	}

	@Override
	public void update(Observable o, Object obj) {
		oldHealth.push(health);
	}
	
	public void undo(PnZModel pnzm){
		if (!oldHealth.isEmpty()){
			health = oldHealth.peek();
			futureHealth.push(oldHealth.pop());
		}
	}
	
	public void redo(PnZModel pnzm){
		if (!futureHealth.isEmpty()){
			health = futureHealth.peek();
			oldHealth.push(futureHealth.pop());
		}
	}

}
