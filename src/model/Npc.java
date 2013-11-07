package model;

import java.util.Observable;
import java.util.Observer;

public class Npc implements Observer{
	
	protected String type;	//name of this type of npc
	protected int health;		//current health of the npc
	protected int damage;		//the amount of damage the npc can do
	
	public Npc(String type, int health, int damage){
		this.type = type;		//set the type of npc
		this.health = health;	//set its starting health
		this.damage = damage;	//set the amount of damage
	}
	
	public String getType() {
		return type;
	}

	public int getHealth() {
		return health;
	}

	public int getDamage() {
		return damage;
	}
	
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
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
