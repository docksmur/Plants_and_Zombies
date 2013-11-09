package model;

import java.util.Observable;
import java.util.Observer;

public class Npc implements Observer{
	
	protected String type;	//name of this type of npc
	protected int health;		//current health of the npc
	protected int damage;		//the amount of damage the npc can do
	
	public Npc(String type, int health, int damage, Observable pnz){
		this.type = type;		//set the type of npc
		this.health = health;	//set its starting health
		this.damage = damage;	//set the amount of damage
		pnz.addObserver(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + damage;
		result = prime * result + health;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
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
