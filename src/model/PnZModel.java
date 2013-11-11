package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class PnZModel extends Observable {

	public static final int PASS=1;
	public static final int FAIL=0;
	private ArrayList<ArrayList<Npc>> grid; 						//user placed plants
	private ArrayList<String> upcoming;								//list of upcoming enemies for user
	private ArrayList<Observer> observers;
	private Enemy enemies[][];										//grid of enemies where they will be before game start
	private int remaining;											// number of remaining enemies
	private boolean running;
	private int level; 												//current level
	private int turn=0;												//current turn
	private int sunPoints;
	
	public PnZModel(){
		grid = new ArrayList<ArrayList<Npc>>(5);					//init plant grid
		upcoming = new ArrayList<String>(); 						//init list of coming up
		remaining=0;
		running=true;
		observers = new ArrayList<Observer>();
		for (int i=0; i<5;i++){ 									//add 5 zombies both grid and list of upcomings
			grid.add(new ArrayList<Npc>(10));
			for (int j=0; j<10;j++){
				grid.get(i).add(null);
			}
			upcoming.add("zombie");
			grid.get(i).set(5, new Enemy("zombie", 5, 1, i, this));
			remaining+=1;
		}
		sunPoints = 100;
		level=1; 													//set level to 1
		
	}
	
	public void placePlant(int row, int col, Plant plant){
		grid.get(row).set(col, plant);										//place plant at specific row and column
	}
	
	public int startWave(){
		ArrayList<Integer> validRows = new ArrayList<Integer>();		//list of rows with remaining enemies
		Integer[] temp={0,1,2,3,4};										//all rows initially are active
		validRows.addAll(Arrays.asList(temp));
		//while (running && remaining>0){								//turn is the number of columns enemy grid overlaps the play grid
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
		//}
		//this.setRunning(false);
		//this.setChanged();
		//notifyObservers();
		//System.out.println(""+this);
		turn++;
		if(remaining==0){					//if there are no enemies by the end pass
			running = false;
			this.setChanged();
			notifyObservers();
			return PASS;
		}else{										//otherwise the user has lost
			this.setChanged();
			notifyObservers();
			return FAIL;
		}
	}

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
	
	public int getRowDamage(int row){				//get the total damage a row will do to the first zombie
		int damage = 0;
		for (int col=0; col<5; col++){				//each plant in a row adds its damage
			if (grid.get(row).get(col)!=null && grid.get(row).get(col) instanceof Plant){
				damage += grid.get(row).get(col).getDamage();
			}
		}
		return damage;
	}
	
	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
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

	public int getSunPoints() {
		return sunPoints;
	}

	public void addSunPoints(int sunPoints) {
		this.sunPoints += sunPoints;
	}

	public ArrayList<ArrayList<Npc>> getGrid() {
		return grid;
	}

	public void setGrid(ArrayList<ArrayList<Npc>> grid) {
		this.grid = grid;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void placePlant(int row, int col, String s) {
		if (s.equalsIgnoreCase("Sunflower")){
			Plant sf = new Sunflower(this);
			this.placePlant(row, col, sf);
			this.sunPoints -= sf.getCost();
		}else if (s.equalsIgnoreCase("Pea Shooter")){
			Plant ps = new PeaShooter(this);
			this.placePlant(row, col, ps);
			this.sunPoints -= ps.getCost();
		}
		
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
				//System.out.println(o.getType()+" "+this.getSunPoints());
			}
		}
	}

	public int getRemaining() {
		return remaining;
	}
	
}
