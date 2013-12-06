package model;

import java.util.ArrayList;
import java.util.Observer;

/**
 * The data fo the PnZModel packaged together to save and restore states
 * 
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.7
 */
public class PnZModelData {
	
	private ArrayList<ArrayList<Npc>> grid;							//the play grid
	private int sunPoints;											//the sunPoints available to the player

	/**
	 * Create a new PnZModelData
	 */
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
	
	/**
	 * Create a new PnZModelData that is a copy of another one, so saved objects will be unaffected
	 * 
	 * @param d the PnZModelData to make a copy of
	 */
	public PnZModelData(PnZModelData d) {
		grid = new ArrayList<ArrayList<Npc>>();
		for (ArrayList<Npc> n : d.getGrid()){
			grid.add((ArrayList<Npc>) n.clone());				//create a clone of the arraylists
			//System.out.println(""+grid.get(grid.size()-1));
		}
		//System.out.println("\n");
		sunPoints = d.getSunPoints();							//store the sunpoints as well
	}

	/**
	 * return the npc grid
	 * 
	 * @return The current grid
	 */
	public ArrayList<ArrayList<Npc>> getGrid() {
		return grid;
	}

	/**
	 * set a new npc grid
	 * 
	 * @param grid the grid that will be the new current grid
	 */
	public void setGrid(ArrayList<ArrayList<Npc>> grid) {
		this.grid = grid;
	}

	/**
	 * get the sunpoints
	 * 
	 * @return the current sun points
	 */
	public int getSunPoints() {
		return sunPoints;
	}

	/**
	 * set new amount of sunpoints
	 * 
	 * @param sunPoints new sun point value
	 */
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