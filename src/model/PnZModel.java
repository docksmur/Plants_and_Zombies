package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * 
 * The model class of Plants and Zombies. Controls the actual gameplay of the game.
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.4.1
 * 
 *
 */

public class PnZModel extends Observable {

	public static final int PASS=1;
	public static final int FAIL=0;
	private ArrayList<ArrayList<Npc>> grid; 				//grid of plants(col 0-4) and zombies(col 5-9)
	private ArrayList<String> upcoming;						//list of upcoming enemies for user
	private ArrayList<Observer> observers;					//list of observers
	private int remaining;									// number of remaining enemies
	private boolean running;								//make sure game is still running
	private int sunPoints;									//amount of sun points to place plants with
	
	
	/**
	 * constructor of the model
	 */
	public PnZModel(){
		grid = new ArrayList<ArrayList<Npc>>(5);					//initialize npc grid
		upcoming = new ArrayList<String>(); 						//initialize list of coming up
		remaining=0;												//currently no enemies remaining
		running=true;												//currently running
		observers = new ArrayList<Observer>();						//list of observers
		for (int i=0; i<5;i++){ 									//add 5 zombies both grid and list of upcomings
			grid.add(new ArrayList<Npc>(10));
			for (int j=0; j<10;j++){								//fill the grid with nulls so there can be empty spaces between entities
				grid.get(i).add(null);
			}
			upcoming.add("zombie");									//add zombie to list of upcoming
			grid.get(i).set(5, new Enemy("zombie", 5, 1, i, this));	//add zombie to grid
			remaining+=1;											//there is now one more enemy
		}
		sunPoints = 100;											//start with 100 sun points
	}
	
	/**
	 * Places the specified plant at the specified location
	 * 
	 * @param row the row to place the plant in
	 * @param col the row to place the plant in
	 * @param plant the plant that should be placed
	 */
	public boolean placePlant(int row, int col, Plant plant){
		if (this.sunPoints - plant.getCost()>=0){
			grid.get(row).set(col, plant);						//place plant at specific row and column
			this.sunPoints -= plant.getCost();					//subtract the cost of the plant
			return true;
		}
		return false;
	}
	
	/** 
	 * Places the specified plant at the specified location
	 * 
	 * @param row the row to place the plant in
	 * @param col the row to place the plant in
	 * @param type string representing the type of plant that should be placed
	 */
	public boolean placePlant(int row, int col, String type){
		if (type.equalsIgnoreCase("Sunflower")){
			Plant sf = new Sunflower(this);
			return this.placePlant(row, col, sf);
		}else if (type.equalsIgnoreCase("Pea Shooter")){
			Plant ps = new PeaShooter(this);
			return this.placePlant(row, col, ps);
		}
		return false;
	}
	
	/**
	 * Plays one turn of the game
	 * 
	 * @return PASS if you won, FAIL if you lost or are continuing 
	 */
	public int startWave(){
		ArrayList<Integer> validRows = new ArrayList<Integer>();		//list of rows with remaining enemies
		Integer[] temp={0,1,2,3,4};										//all rows initially are active
		validRows.addAll(Arrays.asList(temp));								//turn is the number of columns enemy grid overlaps the play grid
		int damage = 0;
		for (Integer row=0;row<5;row++){							//damage is calculated for each row
			//System.out.println(""+this);
			if (validRows.contains(row)){							//if this row is valid get the damage of plants in this row
				damage = getRowDamage(row);
				if (firstInRow(row)!=-1){							//if there is a monster find the first one
					int col=firstInRow(row);
					//System.out.println("row: "+row+" col: "+col);
					if (grid.get(row).get(col).getClass()==Enemy.class){
						if(col<5 && grid.get(row).get(col).damaged(damage)!=-1){		//damage the zombie
							grid.get(row).set(col,null);
							remaining-=1;
						}
					}
				}else{											  	//all monsters in row defeated remove row from play
					validRows.remove(row);
												//System.out.println(" "+validRows+" "+row);
				}
			}
		}
		//System.out.println("notified");
		this.setChanged();
		notifyObservers();
		//System.out.println(""+this);
		if(remaining==0){					//if there are no enemies by the end the game is over
			running = false;
			return PASS;
		}else{										//otherwise the user has lost
			return FAIL;
		}
	}

	/**
	 * Finds the first enemy in the row that can be damaged
	 * 
	 * @param row the row to find the first enemy of
	 * @return the column the enemy is in
	 */
	public int firstInRow(int row){					//find the first zombie in a given row
		for (int i=0;i<10;i++){						//for all columns
			if (grid.get(row).get(i)!=null && grid.get(row).get(i).getClass()==Enemy.class){				//if there is an entity
				if (grid.get(row).get(i).getHealth()>0){	//and it isn't dead
					return i;						//return the column it is in
				}
			}
		}
		return -1;									//no valid enemies were found
	}
	
	/**
	 * Gets the damage of all the plants in a row
	 * 
	 * @param row the row to get the damage from
	 * @return total damage of the row
	 */
	public int getRowDamage(int row){				//get the total damage a row will do to the first zombie
		int damage = 0;
		for (int col=0; col<5; col++){				//each plant in a row adds its damage
			if (grid.get(row).get(col)!=null && grid.get(row).get(col) instanceof Plant){
				damage += grid.get(row).get(col).getDamage();
			}
		}
		return damage;
	}
	

	/*		Main function for text based. 
	 * 		Removed because it kept running this as opposed to the other main function.
	 * 		It was really annoying.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	  public static void main(String[] args) {
		PnZModel pz = new PnZModel();    				//new game
		Plant ps = new PeaShooter(pz);     			//define what plant
		Plant sf = new Sunflower(pz);
		boolean cont=true;
		Scanner sc = new Scanner(System.in);
		while (pz.sunPoints!=0 && cont){							//place 1 plant in each row in the first column
			System.out.println("Please type a row number, column number, and plant type (p for Peashooter or s for Sunflower) or type -1 to continue");
			int row=-10;
			boolean int_test=false;
			try {
				sc.reset();
				row = Integer.parseInt(sc.next());
				int_test=true;
			} catch (NumberFormatException e) {
				System.out.println("not a number. please try again");
			}
			if (row==-1){
				cont=false;
			}else if(int_test){
				int col=-10;
				int_test=false;
				try {
					col = sc.nextInt();
					int_test=true;
				} catch (Exception e) {
					System.out.println("not a number. please try again");
				}
				if(int_test){
					String type = sc.next();
					if (type.equalsIgnoreCase("S")){
						pz.placePlant(row, col, sf);
						pz.sunPoints -= sf.getCost();
					}else if (type.equalsIgnoreCase("P")){
						pz.placePlant(row, col, ps);
						pz.sunPoints -= ps.getCost();
					}else{
						System.out.println("couldn't find plant type please try again");
					}
					System.out.println(""+pz);
				}
			}
		}
		
		System.out.println(pz.upcoming);
		System.out.println("Wave Starting");
		int res = pz.startWave(); 
		if (res>0){
			System.out.println("Wave Passed");
		}else if (res<1){
			System.out.println("Wave Failed");
		}

	}
*/
	@Override
	public String toString() {
		String show="";
		for(int row=0;row<5;row++){
			for(Npc n:grid.get(row)){
				if(n!=null){
					if (n instanceof Enemy){
						show+=""+n.getType()+"\t\t";
					}else{
						show+=""+n.getType()+"\t";
					}
				}else{
					show+="Empty\t\t";
				}
			}
			show=show+"\n";
		}
		show=show+"\nSun Points: "+this.getSunPoints();
		return show;
	}

	/**
	 * Gets sun points
	 * 
	 * @return the amount of sun points
	 */
	public int getSunPoints() {
		return sunPoints;
	}

	/**
	 * Adds to the total sun points
	 * 
	 * @param sunPoints the amount to add to sun points
	 */
	public void addSunPoints(int sunPoints) {
		this.sunPoints += sunPoints;
	}

	/**
	 * Get the grid of npcs
	 * 
	 * @return the grid of npcs
	 */
	public ArrayList<ArrayList<Npc>> getGrid() {
		return grid;
	}

	/**
	 * Check if the game is running
	 * 
	 * @return whether the game is running
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Set whether the game is running
	 * 
	 * @param running start or stop the game
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@Override
	public void addObserver(Observer o){
		observers.add(o);
	}
	
	@Override
	public void deleteObserver(Observer o){
		observers.remove(o);
	}
	
	@Override
	public void notifyObservers(){
		if (hasChanged()){
			for (Observer o:observers){
				o.update(this, null);
			}
		}
	}

	/**
	 * Get the number of enemies left
	 * 
	 * @return the number of remaining enemies
	 */
	public int getRemaining() {
		return remaining;
	}
	
}
