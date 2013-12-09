package leveleditor;

import java.util.ArrayList;
import java.util.Observable;

import model.Enemy;
import model.Npc;

public class LevelEdit extends Observable{
	
	private ArrayList<ArrayList<Npc>> grid;

	public LevelEdit(){
		grid = new ArrayList<ArrayList<Npc>>(5);
		for (int i=0; i<5;i++){ 									//add 5 zombies both grid and list of upcomings
			grid.add(new ArrayList<Npc>(10));
			for (int j=0; j<10;j++){								//fill the grid with nulls so there can be empty spaces between entities
				grid.get(i).add(null);
			}
		}
	}

	public void placeEnemy(int row, int col, Enemy enemy) {
		grid.get(row).set(col, enemy);
		setChanged();
		notifyObservers();
	}
	
	public void placeEnemy(int row, int col, String s) {
		if (s.equalsIgnoreCase("zombie")){
			placeEnemy(row,col, new Enemy("zombie", 5, 1, row, col, this));
		}
			
	}

	public ArrayList<ArrayList<Npc>> getGrid() {
		return grid;
	}
	
	
}
