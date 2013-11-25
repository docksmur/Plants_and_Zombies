package model;

import java.util.ArrayList;
import java.util.Observer;

public class PnZModelData {
	private ArrayList<ArrayList<Npc>> grid;
	private int sunPoints;

	public PnZModelData() {
		setGrid(new ArrayList<ArrayList<Npc>>(5));					//initialize npc grid
		setSunPoints(100);											//start with 100 sun points
		for (int i=0; i<5;i++){ 									//add 5 zombies both grid and list of upcomings
			grid.add(new ArrayList<Npc>(10));
			for (int j=0; j<10;j++){								//fill the grid with nulls so there can be empty spaces between entities
				grid.get(i).add(null);
			}
		}
		
	}
	
	public PnZModelData(PnZModelData d) {
		grid = new ArrayList<ArrayList<Npc>>();
		for (ArrayList<Npc> n : d.getGrid()){
			grid.add((ArrayList<Npc>) n.clone());
			System.out.println(""+grid.get(grid.size()-1));
		}
		System.out.println("\n");
		sunPoints = d.getSunPoints();
	}

	public ArrayList<ArrayList<Npc>> getGrid() {
		return grid;
	}

	public void setGrid(ArrayList<ArrayList<Npc>> grid) {
		this.grid = grid;
	}

	public int getSunPoints() {
		return sunPoints;
	}

	public void setSunPoints(int sunPoints) {
		this.sunPoints = sunPoints;
	}

	@Override
	public String toString() {
		String s = "PnZModelData [grid=";
		for (ArrayList<Npc> n : getGrid()){
			s+="\n" + n;
		}
		
		return s;
	}

}