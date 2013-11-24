package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

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
	
	PnZModel pnzm;
	PnZView pnzv;
	
	/**
	 * Create new controller
	 * 
	 * @param pnzm the model
	 * @param pnzv the view
	 */
	public PnZController(PnZModel pnzm, PnZView pnzv){
		this.pnzm = pnzm;
		this.pnzv = pnzv;
	}

	@Override
	public void actionPerformed(ActionEvent o) {
		Object o2 = o.getSource();
		JButton b;
		b = (JButton) o2;						//make source a button and get its name
		String name = b.getName();
		if (name.equalsIgnoreCase("start")){	//if it's start start the wave
			pnzm.startWave();
		}else if (name.equalsIgnoreCase("undo")){	//if it's start start the wave
			pnzm.undo();
		}else if (name.equalsIgnoreCase("redo")){	//if it's start start the wave
			pnzm.redo();
		}else{
			String[] things = name.split(",");	//otherwise it's a place plant so place a plant
			int row = Integer.parseInt(things[1]);
			int col = Integer.parseInt(things[2]);
			Object[] possibilities = {"Sunflower", "Pea Shooter"};//get the type of plant
			String s = (String)JOptionPane.showInputDialog(new JFrame(),"What type of plant would you like to add?","Customized Dialog",
			                    JOptionPane.QUESTION_MESSAGE, null, possibilities, "");
			if (pnzm.placePlant(row, col, s)){	//only plant it if there is enough money
				b.setText(s);
				b.setEnabled(false);
				JTextArea ja = (JTextArea) pnzv.getContentPane().getComponent(26);
				ja.setText("Sun Points: "+pnzm.getSunPoints());
			}else{								//otherwise tell the user they don't have enough money
				JOptionPane.showMessageDialog(new JFrame(), "Placement Failed: out of money");
			}
		}
	}

}
