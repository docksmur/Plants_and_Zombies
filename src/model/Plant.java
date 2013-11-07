package model;

public class Plant extends Npc {
	
	int cost;
	
	public Plant(String type, int health, int damage, int cost){
		super(type, health, damage);
		this.cost=cost;
	}

}
