package view;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Npc;
import model.PnZModel;
import controller.PnZController;

/**
 * 
 * the view of Plants and Zombies to show the user
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.4.1
 *
 */

public class PnZView extends JFrame implements Observer{
	
	private JPanel jp;				//the content pane
	private PnZModel pnzm;			//the model
	private PnZController pnzc;		//the controller
	
	
	/**
	 * create a new view
	 */
	public PnZView(){
		super();								//create a new frame for the game
		jp = new JPanel(new GridLayout(6,5));	//create a grid layout for buttons
		pnzm = new PnZModel();
		pnzm.addObserver(this);					//add view as observer so it gets updated
		pnzc = new PnZController(pnzm,this);	//new controller
		for (int i=0; i<5; i++){
			for (int j=0; j<5; j++){			//add 25 plant placing buttons
				JButton b = new JButton("Place plant here");
				b.setName("button,"+i+","+j);
				b.addActionListener(pnzc);
				jp.add(b);
			}
		}
		JButton b = new JButton("Start Wave");	//add a start button and a text field showing the sun points
		b.setName("start");
		b.addActionListener(pnzc);
		jp.add(b);
		JTextArea ja = new JTextArea("Sun Points: "+ pnzm.getSunPoints());
		ja.setEditable(false);
		jp.add(ja);
		b = new JButton("Undo");	//add a start button and a text field showing the sun points
		b.setName("undo");
		b.addActionListener(pnzc);
		jp.add(b);
		b = new JButton("Redo");	//add a start button and a text field showing the sun points
		b.setName("redo");
		b.addActionListener(pnzc);
		jp.add(b);
		this.setContentPane(jp);				//set the panel as the content pane
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		PnZView pzv = new PnZView();
		pzv.setSize(900, 900);
		pzv.setVisible(true);
	}

	/**
	 * Get the model
	 * 
	 * @return the model
	 */
	public PnZModel getPnzm() {
		return pnzm;
	}

	/**
	 * Set the model
	 * 
	 * @param pnzm the new model
	 */
	public void setPnzm(PnZModel pnzm) {
		this.pnzm = pnzm;
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("update view");
		JTextArea ja = (JTextArea) jp.getComponent(26);	//get the text box and update sun points
		ja.setText("Sun Points: "+pnzm.getSunPoints());
		if(pnzm.isRunning()){							//make sure game is still running
			for(int i=0; i<5; i++){
				for(int j=0; j<5; j++){					//if it is running update the buttons to display whatever may be there in the grid
					JButton b = (JButton)jp.getComponent((5*i)+j);
					Npc type = pnzm.getGrid().get(i).get(j);
					if(type != null){
						b.setText(type.getType());
						b.setEnabled(false);
					}else{
						b.setText("Place plant here");
						b.setEnabled(true);
					}
				}
			}
			
		}else{											//if not running disable all buttons after updating
			for(int i=0; i<5; i++){
				for(int j=0; j<5; j++){
					JButton b = (JButton)jp.getComponent((5*i)+j);
					Npc type = pnzm.getGrid().get(i).get(j);
					if(type != null){
						b.setText(type.getType());
						b.setEnabled(false);
					}else{
						b.setText("Place plant here");
						b.setEnabled(false);
					}
				}
			}
			JButton b = (JButton)jp.getComponent(25);	//set start button disabled
			b.setEnabled(false);
			if(pnzm.getRemaining()==0){					//display if the user won or lost
				JOptionPane.showMessageDialog(this, "Game Over you won");
			}else{
				JOptionPane.showMessageDialog(this, "Game Over you lost");
			}
		}
	}

}
