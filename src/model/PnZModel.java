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
	private Plant grid[][]; 										//user placed plants
	private ArrayList<String> upcoming;								//list of upcoming enemies for user
	private Enemy enemies[][];										//grid of enemies where they will be before game start
	private int remaining;											// number of remaining enemies
	private int level; 												//current level
	private int turn;												//current turn
	private int sunPoints;
	
	public PnZModel(){
		grid = new Plant[5][5]; 									//init plant grid
		upcoming = new ArrayList<String>(); 						//init list of coming up
		enemies = new Enemy[5][10];									//init enemy grid
		for (int i=0; i<5;i++){ 									//add 5 zombies both grid and list of upcomings
			upcoming.add("zombie");
			enemies[i][5]= new Enemy("zombie", 5, 1);
		}
		sunPoints = 100;
		level=1; 													//set level to 1
		
	}
	
	public void placePlant(int row, int col, Plant plant){
		grid[row][col] = plant;										//place plant at specific row and column
	}
	
	public int startWave(){
		ArrayList<Integer> validRows = new ArrayList<Integer>();		//list of rows with remaining enemies
		Integer[] temp={0,1,2,3,4};										//all rows initially are active
		validRows.addAll(Arrays.asList(temp));
		for (turn=0;turn<=5;turn++){								//turn is the number of columns enemy grid overlaps the play grid
			System.out.println(this);
			int damage = 0;
			for (Integer row=0;row<5;row++){							//damage is calculated for each row
																		//System.out.println(" "+validRows+" "+row);
				if (validRows.contains(row)){							//if this row is valid get the damage of plants in this row
					damage = getRowDamage(row);
					if (firstInRow(row)!=-1){							//if there is a monster find the first one
						int col=firstInRow(row);
						System.out.println("test: "+col);
						damagePlants(turn,row);							//zombie damages to plants
						if(col-turn<5 && enemies[row][col].damaged(damage)!=-1){		//damage the zombie
							enemies[row][col]=null;
						}
					}else{											  	//all monsters in row defeated remove row from play
						validRows.remove(row);
													//System.out.println(" "+validRows+" "+row);
					}
				}
			}
		}
		if(validRows.isEmpty()){					//if there are no valid rows by the end pass
			return PASS;
		}else{										//otherwise the user has lost
			return FAIL;
		}
	}
	
	public void damagePlants(int turn, int row) {			//find where zombies are overlapping plants to damage them
		if (turn>firstInRow(row)){							//if the first in row is behind overlapped columns they can't interact
			int over = turn-firstInRow(row);				//number of possible overlapped columns 
			for (int i=over;i!=0;i--){						//scan possible columns
				if(grid[row][5-over]!=null){				//if there is a plant damage it
					grid[row][5-over].damaged(enemies[row][firstInRow(row)].getDamage());
					System.out.println("Damage to plant! "+ enemies[row][firstInRow(row)].getDamage());
				}else{										//otherwise do nothing
					System.out.println("No damage to plant!");
				}
			}
		}
	}

	public int firstInRow(int row){					//find the first zombie in a given row
		for (int i=0;i<10;i++){						//for all columns
			if (enemies[row][i]!=null){				//if there is an entity
				if (enemies[row][i].getHealth()>0){	//and it isn't dead
					return i;						//return the column it is in
				}
			}
		}
		return -1;									//no valid enemies were found
	}
	
	public int getRowDamage(int row){				//get the total damage a row will do to the first zombie
		int damage = 0;
		for (int col=0; col<5; col++){				//each plant in a row adds its damage
			if (grid[row][col]!=null){
				damage += grid[row][col].getDamage();
			}
		}
		return damage;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PnZModel pz = new PnZModel();    				//new game
		Plant ps = new PeaShooter();     				//define what plant
		Plant sf = new Sunflower();
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
			for(int i=0; i<5;i++){
				if(grid[row][i]!=null){
					show+="   "+grid[row][i].getType();
				}else{
					show+="   Pempty";
				}
			
				if(enemies[row][i+turn]!=null){
					show+=""+enemies[row][i+turn].getType();
				}else{
					show+="Zempty";
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

}
