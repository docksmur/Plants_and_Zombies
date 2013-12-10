package leveleditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import view.PnZView;
import model.Enemy;
import model.Npc;

/**
 * Level editor program for Plants and Zombies
 * Sets zombies that will spawn for this level
 * 
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 2.1
 * 
 */

public class LevelEdit extends Observable implements Serializable{
	
	private ArrayList<ArrayList<Npc>> grid;
	private transient ArrayList<Observer> observers;		//list of observers
	private ArrayList<Observer> observers2;					//non transient copy of observers without view cuz well the view doesn't need to be stored and also it breaks things

	/**
	 * create a new model for level editor
	 */
	public LevelEdit(){
		observers = new ArrayList<Observer>();
		observers2 = new ArrayList<Observer>();
		grid = new ArrayList<ArrayList<Npc>>(5);
		for (int i=0; i<5;i++){ 									//add 5 zombies both grid and list of upcomings
			grid.add(new ArrayList<Npc>(10));
			for (int j=0; j<10;j++){								//fill the grid with nulls so there can be empty spaces between entities
				grid.get(i).add(null);
			}
		}
	}

	/**place a new enemy on the grid
	 * 
	 * @param row row the zombie should be on
	 * @param col column the zombie should be placed
	 * @param enemy the enemy that will be placed
	 */
	public void placeEnemy(int row, int col, Enemy enemy) {
		grid.get(row).set(col, enemy);
		setChanged();
		notifyObservers();
	}
	
	/**place enemy again but with a string instead of a enemy object
	 * @param row row the zombie is placed in
	 * @param col column the zombie will be placed
	 * @param s name of the type of enemy to be placed
	 */
	public void placeEnemy(int row, int col, String s) {
		if (s.equalsIgnoreCase("zombie")){
			placeEnemy(row,col, new Enemy("zombie", 5, 1, row, col, this));
		}
			
	}

	/**return the grid of enemies
	 * @return the grid of enemies
	 */
	public ArrayList<ArrayList<Npc>> getGrid() {
		return grid;
	}

	/**saves the current set up as a level to be played in plants and zombies
	 * @param file
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
	
	/**get the (non-view) observers of this class
	 * @return all non-view observers
	 */
	public ArrayList<Observer> getObservers(){
		return observers2;
	}
	
	@Override
	public void addObserver(Observer o){				//overridden because a second observers is need for storage
		if(o != null && !(o instanceof LvlEditView)){		//add everything except the view to the storage observer list
			observers2.add(o);
		}
		observers.add(o);
	}
	
	/**set the observers of this class
	 * @param o the new list of observers
	 */
	public void setObservers(ArrayList<Observer> o){	//overridden for duplicate list
		observers = o;
	}
	
	@Override
	public void deleteObserver(Observer o){				//overridden for duplicate list
		if(o != null && !(o instanceof LvlEditView)){
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
	
	
}
