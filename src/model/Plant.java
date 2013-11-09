package model;

import java.util.Observable;

public class Plant extends Npc {
	
	int cost;
	
	public Plant(String type, int health, int damage, int cost, Observable pnz){
		super(type, health, damage, pnz);
		this.cost=cost;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
