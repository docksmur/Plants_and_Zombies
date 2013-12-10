package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.PnZModel;
import view.PnZView;

/**
 * 
 * The controller to interpret and apply user input
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.4.1
 * 
 */


public class PnZController implements ActionListener {
	
	private PnZModel pnzm;		//model of the game
	private PnZView pnzv;		//view for the game
	
	/**
	 * Create new controller
	 * 
	 * @param pnzm the model
	 * @param pnzv the view
	 */
	public PnZController(PnZModel pnzm, PnZView pnzv){
		this.setPnzm(pnzm);
		this.pnzv = pnzv;
	}

	
	/**
	 * Performs one of the many possible actions based on what button was clicked
	 * 
	 * @param o the action event that was performed
	 */
	@Override
	public void actionPerformed(ActionEvent o) {
		Object o2 = o.getSource();						//get the source of the action event
		JButton b;
		b = (JButton) o2;								//make source a button and get its name
		String name = b.getName();
		if (name.equalsIgnoreCase("start")){			//if the name is start, start the wave
			getPnzm().startWave();
		}else if (name.equalsIgnoreCase("undo")){		//if the its name is undo perform an undo
			getPnzm().undo();
		}else if (name.equalsIgnoreCase("redo")){		//if it's name is redo perform a redo
			getPnzm().redo();	
		}else if (name.equalsIgnoreCase("save")){		//if it's the save button save the game
			save(b);									
		}else if (name.equalsIgnoreCase("load")){		//if it's load load the game from memory
			load(b);
		}else if (name.equalsIgnoreCase("restart")){	//if it's restart play again
			pnzv.playAgain();
		}else if (name.equalsIgnoreCase("level")){	//if it's restart play again
			level(b);
		}else{											//if it is nothing else it must be placing a plant
			plantPlace(name);							
		}
	}
	
	private void level(JButton b) {
		JFileChooser fc = new JFileChooser();	//create the file selection prompt
		FileNameExtensionFilter filter = 		//create a filter so only ser files appear
				new FileNameExtensionFilter("Plants and Zombies Level", "lvl");
		fc.setFileFilter(filter);				//add the filter to the prompt
		fc.showOpenDialog(b);					//display the prompt
		File file = fc.getSelectedFile();		//create file from user selection
		getPnzm().loadLevel(file, pnzv);				//load the new game
	}


	/**
	 * Handles the save functionality in this class i.e. getting the file
	 * 
	 * @param b the button source for location
	 */
	public void save(JButton b){
		JFileChooser fc = new JFileChooser();					//prompt the user for the file name and location
		FileNameExtensionFilter filter = 
				new FileNameExtensionFilter("Java Serial file", "ser");
		fc.setFileFilter(filter);
		fc.showSaveDialog(b);									//open the save dialog
		File file = new File(fc.getSelectedFile()+".ser");		//make a .ser file object from the user's selection
		getPnzm().save(file);
	}
	
	/**
	 * Handles the load functionality in this class i.e. getting the file
	 * 
	 * @param b the button source for location
	 */
	public void load(JButton b){
		JFileChooser fc = new JFileChooser();	//create the file selection prompt
		FileNameExtensionFilter filter = 		//create a filter so only ser files appear
				new FileNameExtensionFilter("Java Serial file", "ser");
		fc.setFileFilter(filter);				//add the filter to the prompt
		fc.showOpenDialog(b);					//display the prompt
		File file = fc.getSelectedFile();		//create file from user selection
		getPnzm().load(file, pnzv);				//load the new game
	}
	
	/**
	 * Handles plant placement parsing by seperating out the row, column, and type from the name string 
	 * 
	 * @param name the name of the button containing all needed information to place a plant
	 */
	public void plantPlace(String name){
		String[] things = name.split(",");										//split on commas to get "type", "row#", "col#"
		int row = Integer.parseInt(things[1]);									//name is of the format "type,#,#" where the numbers are row and column respectively
		int col = Integer.parseInt(things[2]);
		Object[] possibilities = {"Sunflower", "Pea Shooter", "Potato Mine"};	//get the type of plant
		String s = (String)JOptionPane.showInputDialog(new JFrame(),"What type of plant would you like to add?","Customized Dialog",
		                    JOptionPane.QUESTION_MESSAGE, null, possibilities, "");
		if (!getPnzm().placePlant(row, col, s)){									//only plant it if there is enough money
			JOptionPane.showMessageDialog(new JFrame(), 
					"Placement Failed: out of money");								//otherwise tell the user they don't have enough money
		}
	}


	/**get the model
	 * @return the model
	 */
	public PnZModel getPnzm() {					//get the model
		return pnzm;
	}


	/**set the model
	 * @param pnzm the new model
	 */
	public void setPnzm(PnZModel pnzm) {		//set a new model
		this.pnzm = pnzm;
	}

}
