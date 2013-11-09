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
	private Enemy enemies[][];										//grid of enemies where they will be before game start
	private int remaining;											// number of remaining enemies
	private boolean running;
	private int level; 												//current level
	private int turn;												//current turn
	private int sunPoints;
	
	public PnZModel(){
		grid = new ArrayList<ArrayList<Npc>>(5);					//init plant grid
		upcoming = new ArrayList<String>(); 						//init list of coming up
		remaining=0;
		running=true;
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
		while (running && remaining>0){								//turn is the number of columns enemy grid overlaps the play grid
			System.out.println(this);
			int damage = 0;
			for (Integer row=0;row<5;row++){							//damage is calculated for each row
				if (validRows.contains(row)){							//if this row is valid get the damage of plants in this row
					damage = getRowDamage(row);
					if (firstInRow(row)!=-1){							//if there is a monster find the first one
						int col=firstInRow(row);
						//System.out.println("row: "+row+" col: "+col);
						damagePlants(turn,row);							//zombie damages to plants
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
			this.setChanged();
			notifyObservers();
			//System.out.println("notified");
		}
		System.out.println(""+this);
		if(remaining==0){					//if there are no valid rows by the end pass
			return PASS;
		}else{										//otherwise the user has lost
			return FAIL;
		}
	}
	
	public void damagePlants(int turn, int row) {			//find where zombies are overlapping plants to damage them
		int fir = firstInRow(row);
		if(grid.get(row).get(fir-1) != null){				//if the space next to a zombie has a plant make the zombie damage it
			if(grid.get(row).get(firstInRow(row)-1).getClass()==Plant.class){
				grid.get(row).get(firstInRow(row)-1).damaged(grid.get(row).get(firstInRow(row)).getDamage());
			}
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
			if (grid.get(row).get(col)!=null){
				damage += grid.get(row).get(col).getDamage();
			}
		}
		return damage;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PnZModel pz = new PnZModel();    				//new game
		Plant ps = new PeaShooter(pz);     			//define what plant
		Plant sf = new Sunflower(pz);
		boolean cont=true;
		Scanner sc = new Scanner(System.in);
		while (pz.sunPoints!=0 && cont){							//place 1 plant in each row in the first column
			System.out.println("Please type a row number, column number, and plant type or type -1 to continue");
			int row = sc.nextInt();
			if (row==-1){
				cont=false;
			}else{
				int col = sc.nextInt();
				String type = sc.next();
				if (type.equals("Sunflower")){
					pz.placePlant(row, col, sf);
					pz.sunPoints -= sf.getCost();
				}else if (type.equals("Peashooter")){
					pz.placePlant(row, col, ps);
					pz.sunPoints -= ps.getCost();
				}else{
					System.out.println("couldn't find plant type please try again");
				}
				System.out.println("Sun Points: "+pz.sunPoints);
				
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

	@Override
	public String toString() {
		String show="";
		for(int row=0;row<5;row++){
			for(Npc n:grid.get(row)){
				if(n!=null){
					show+="   "+n.getType();
				}else{
					show+="   Empty";
				}
			}
			show=show+"\n";
		}
		
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
	
}
