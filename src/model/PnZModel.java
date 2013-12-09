package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import view.PnZView;

/**
 * 
 * The model class of Plants and Zombies. Controls the actual gameplay of the game.
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.4.1
 * 
 *
 */

public class PnZModel extends Observable implements Serializable {

	public static final int PASS=1;
	public static final int FAIL=0;
	private ArrayList<String> upcoming;						//list of upcoming enemies for user
	private transient ArrayList<Observer> observers;		//list of observers
	private ArrayList<Observer> observers2;					//non transient copy of observers without view cuz well the view doesn't need to be stored and also it breaks things
	private int remaining;									// number of remaining enemies
	private boolean running;								//make sure game is still running
	private PnZModelData data = new PnZModelData();			//the state of the data of the game
	private Stack<PnZModelData> undoStack;					//former game states
	private Stack<PnZModelData> redoStack;					//game states that have been undone
	
	
	/**
	 * constructor of the model
	 */
	public PnZModel(){
		undoStack = new Stack<PnZModelData>();						//init the undo and redo stakcs
		redoStack = new Stack<PnZModelData>();
		upcoming = new ArrayList<String>(); 						//initialize list of coming up
		remaining=0;												//currently no enemies remaining
		running=true;												//currently running
		observers = new ArrayList<Observer>();						//list of observers
		observers2 = new ArrayList<Observer>();
		for (int i=0; i<4;i++){ 									//add 5 zombies both grid and list of upcomings
			upcoming.add("zombie");									//add zombie to list of upcoming
			data.getGrid().get(i).set(5, new Enemy("zombie", 5, 1, i, 5, this));	//add zombie to grid
			remaining+=1;											//there is now one more enemy
		}
		data.getGrid().get(4).set(5, new BigZombie( 4, 5, this));
		remaining+=1;
		data.getGrid().get(0).set(6, new Enemy("zombie", 5, 1, 0, 6, this));
		remaining+=1;
	}
	
	/**
	 * Places the specified plant at the specified location
	 * 
	 * @param row the row to place the plant in
	 * @param col the row to place the plant in
	 * @param plant the plant that should be placed
	 * @return boolean whether the plant was succesfully placed
	 */
	public boolean placePlant(int row, int col, Plant plant){
		if (this.data.getSunPoints() - plant.getCost()>=0){
			moved();															//the board is changing so store the current state
			data.getGrid().get(row).set(col, plant);							//place plant at specific row and column
			this.data.setSunPoints(this.data.getSunPoints() - plant.getCost());	//subtract the cost of the plant
			setChanged();
			notifyObservers("update");
			return true;														//return true becaue the plant was placed
		}
		return false;
	}
	
	/** 
	 * Places the specified plant at the specified location
	 * 
	 * @param row the row to place the plant in
	 * @param col the row to place the plant in
	 * @param type string representing the type of plant that should be placed
	 * @return boolean whether the plant was succesfully placed
	 */
	public boolean placePlant(int row, int col, String type){
		if (type.equalsIgnoreCase("Sunflower")){			//convert a string to the coresponding type of plant
			Plant sf = new Sunflower(this, row, col);
			return this.placePlant(row, col, sf);			//place the created plant and return its successfullness
		}else if (type.equalsIgnoreCase("Pea Shooter")){
			Plant ps = new PeaShooter(this,row,col);
			return this.placePlant(row, col, ps);
		}else if (type.equalsIgnoreCase("Potato Mine")){
			Plant pm = new PotatoMine(this,row,col);
			return this.placePlant(row, col, pm);
		}
		return false;										//plant was not placed because the type couldn't be found
	}
	
	/**
	 * Plays one turn of the game
	 * 
	 * @return PASS if you won, FAIL if you lost or are continuing 
	 */
	public int startWave(){
		moved();												//game is changing so store the state
		//System.out.println("notified: "+observers);			
		this.setChanged();
		notifyObservers("move");								//update to move zombies
		this.setChanged();
		notifyObservers("plants");								//update to make plants damage
		this.setChanged();
		notifyObservers("update");								//update view
		//System.out.println(""+this);
		if(remaining==0  || running == false){					//if there are no enemies by the end the game is over or something is over
			running = false;									//it's over!
			this.setChanged();
			notifyObservers("over");							//update the board to show it's over
			return PASS;										//return that it;s over
		}else{
			this.setChanged();
			notifyObservers("money");							//update moneys
			return FAIL;
		}
	}

	private void moved() {
		//System.out.println("moved");
		undoStack.push(new PnZModelData(data));				//add state to stack
		redoStack.clear();									//if a new move is performed redo moves are overwriten
	}

	/**
	 * Finds the first enemy in the row that can be damaged
	 * 
	 * @param row the row to find the first enemy of
	 * @return the column the enemy is in
	 */
	public int firstInRow(int row){					//find the first zombie in a given row
		for (int i=0;i<10;i++){						//for all columns
			if (data.getGrid().get(row).get(i)!=null && data.getGrid().get(row).get(i) instanceof Enemy){				//if there is an entity
				if (data.getGrid().get(row).get(i).getHealth()>0){	//and it isn't dead
					return i;						//return the column it is in
				}
			}
		}
		return -1;									//no valid enemies were found
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
	public String toString() {						//make a string of these things
		String show="";
		for(int row=0;row<5;row++){
			for(Npc n:data.getGrid().get(row)){
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
		return data.getSunPoints();
	}

	/**
	 * Adds to the total sun points
	 * 
	 * @param sunPoints the amount to add to sun points
	 */
	public void addSunPoints(int sunPoints) {
		this.data.setSunPoints(this.data.getSunPoints() + sunPoints);
	}

	/**
	 * Get the grid of npcs
	 * 
	 * @return the grid of npcs
	 */
	public ArrayList<ArrayList<Npc>> getGrid() {
		return data.getGrid();
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
	public void addObserver(Observer o){				//overridden because a second observers is need for storage
		if(o != null && !(o instanceof PnZView)){		//add everything except the view to the storage observer list
			observers2.add(o);
		}
		observers.add(o);
	}
	
	public void setObservers(ArrayList<Observer> o){	//overridden for duplicate list
		observers = o;
	}
	
	@Override
	public void deleteObserver(Observer o){				//overridden for duplicate list
		if(o != null && !(o instanceof PnZView)){
			observers2.remove(o);
		}
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
	
	@Override
	public void notifyObservers(Object obj){
		if (hasChanged()){
			for (Observer o:observers){
				o.update(this, obj);
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

	/**
	 * @param numDest
	 */
	public void lessRemaining(int numDest) {
		this.remaining -= numDest;
	}

	/**
	 * @return
	 */
	public Stack<PnZModelData> getRedoStack() {
		return redoStack;
	}

	/**
	 * @param redoStack
	 */
	public void setRedoStack(Stack<PnZModelData> redoStack) {
		this.redoStack = redoStack;
	}

	/**
	 * @return
	 */
	public Stack<PnZModelData> getUndoStack() {
		return undoStack;
	}

	/**
	 * @param undoStack
	 */
	public void setUndoStack(Stack<PnZModelData> undoStack) {
		this.undoStack = undoStack;
	}

	/**
	 * undo the last change to the game state
	 */
	public void undo() {
		if (!undoStack.empty()){
			//System.out.println("undo!\n"+data);
			redoStack.push(data);							//add the state to the redo stack
			data = new PnZModelData( undoStack.pop());		//get the last state  from the stack
			setChanged();
			notifyObservers("undo");						//do a undo update
			setChanged();
			notifyObservers("update");						//do a view update
			setChanged();
			notifyObservers("update");						//do ...another view update...? not sure about this.
			//System.out.println("undo!\n"+data);
		}
	}

	/**
	 * redo changes to the latest game state
	 */
	public void redo() {
		if (!redoStack.empty()){
			undoStack.push(data);					//store the old state
			data = redoStack.pop();					//get the next state
			setChanged();
			notifyObservers("redo");				//do a redo update
			setChanged();
			notifyObservers("update");				//do a view update
		}
	}

	/**save the state of the game to the disk
	 * 
	 * @param file the file the game should save to
	 */
	public void save(File file) {
		try {													//try writing the current state to disk
			FileOutputStream fo = new FileOutputStream(file);	//file output stream for the user selected file
			ObjectOutputStream oo = new ObjectOutputStream(fo);	//object output stream from the file output stream
			//System.out.println(this.observers);
			oo.writeObject(this);								//write this object which has the state
		} catch (FileNotFoundException e) {						//if there is an error writing the file print the appropriate response
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
	}

	/**load a game state from the disk
	 * 
	 * @param file the file's location
	 * @param pnzv the view
	 */
	public void load(File file, PnZView pnzv) {
		try {													//try reading from disk
			FileInputStream fi = new FileInputStream(file);		//input stream from user selection
			ObjectInputStream oo = new ObjectInputStream(fi);	//object stream
			PnZModel pnzm = (PnZModel) oo.readObject();			//read an object from the file
			pnzm.setObservers(pnzm.observers2);					//restore the observer list
			pnzm.addObserver(pnzv);								//re-add the view to the observer list (it souldn't be stored for some reason...)
			pnzv.setPnzm(pnzm);									//change the view to look at the new model
			setChanged();
			notifyObservers("update");							//update grid to show loaded game
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	
	}

	
}
