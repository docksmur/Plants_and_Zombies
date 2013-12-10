package leveleditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.PnZModel;
import view.PnZView;

/**
 * Level editor program for Plants and Zombies
 * Sets zombies that will spawn for this level
 * 
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 2.1
 * 
 */

public class LvlEditController implements ActionListener {
	
	private LevelEdit le;
	private LvlEditView lev;
	
	/**create a controller for the level editor
	 * @param le level editor model
	 * @param lev level editor view
	 */
	public LvlEditController(LevelEdit le, LvlEditView lev){
		this.le = le;
		this.lev = lev;
	}

	/**
	 * add a zombie or save the level depending on which button was pushed
	 */
	@Override
	public void actionPerformed(ActionEvent o) {
		Object o2 = o.getSource();						//get the source of the action event
		JButton b;
		b = (JButton) o2;								//make source a button and get its name
		String name = b.getName();
		if (name.equalsIgnoreCase("save")){
			save(b);
		}else{											//if it is nothing else it must be placing a plant
			enemyPlace(name);							
		}
	}

	/**open a file window for the user to select save name and location
	 * @param b button source for window placement
	 */
	private void save(JButton b) {
		JFileChooser fc = new JFileChooser();					//prompt the user for the file name and location
		FileNameExtensionFilter filter = 
				new FileNameExtensionFilter("Plants and Zombies Level", "lvl");
		fc.setFileFilter(filter);
		fc.showSaveDialog(b);									//open the save dialog
		File file = new File(fc.getSelectedFile()+".lvl");		//make a .ser file object from the user's selection
		le.save(file);
	}

	/**place an enemy
	 * @param name string with info for the zombie
	 */
	private void enemyPlace(String name) {
		String[] things = name.split(",");										//split on commas to get "type", "row#", "col#"
		int row = Integer.parseInt(things[1]);									//name is of the format "type,#,#" where the numbers are row and column respectively
		int col = Integer.parseInt(things[2]);
		Object[] possibilities = {"Zombie", "Big Zombie"};	//get the type of plant
		String s = (String)JOptionPane.showInputDialog(new JFrame(),"What type of plant would you like to add?","Customized Dialog",
		                    JOptionPane.QUESTION_MESSAGE, null, possibilities, "");
		le.placeEnemy(row, col, s);
	}
}
