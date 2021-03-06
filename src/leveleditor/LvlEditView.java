  package leveleditor;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import view.PnZView;
import model.Npc;
import model.PnZModel;
import controller.PnZController;

/**
 * Level editor program for Plants and Zombies
 * Sets zombies that will spawn for this level
 * 
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 2.1
 * 
 */

public class LvlEditView extends JFrame implements Observer {
	
	private transient JPanel jp;				//the content pane
	private transient LevelEdit le;			//the model
	private transient LvlEditController lec;

	/**
	 * create a new level edit view
	 */
	public LvlEditView(){
		super();								//create a new frame for the game
		jp = new JPanel(new GridLayout(6,5));	//create a grid layout for buttons
		le = new LevelEdit();
		le.addObserver(this);
		lec = new LvlEditController(le, this);	//new controller
		for (int i=0; i<5; i++){
			for (int j=5; j<10; j++){			//add 25 plant placing buttons
				JButton b = new JButton("Place enemy here");
				b.setName("button,"+i+","+j);
				b.addActionListener(lec);
				jp.add(b);
			}
		}
		JButton b = new JButton("Save Level");	//add a start button and a text field showing the sun points
		b.setName("save");
		b.addActionListener(lec);
		jp.add(b);
		this.setContentPane(jp);				//set the panel as the content pane
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//le.addObserver(this);					//add view as observer so it gets updated
	}
	
	
	
	/** I mean it's a main function
	 * it makes the view load and thus everything load
	 * @param args
	 */
	public static void main(String[] args) {
		LvlEditView lev = new LvlEditView();
		lev.setSize(900, 900);
		lev.setVisible(true);
	}

	/**
	 * update the display with new zombies that have been added
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		for(int i=0; i<5; i++){
			for(int j=0; j<5; j++){					//if it is running update the buttons to display whatever may be there in the grid
				JButton b = (JButton)jp.getComponent((5*i)+j);
				Npc type =  le.getGrid().get(i).get(j+5);
				if(type != null){
					b.setText(type.getType());
					b.setEnabled(false);
				}else{
					b.setText("Place enemy here");
					b.setEnabled(true);
				}
			}
		}
	}
	
}
