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

public class PnZView extends JFrame implements Observer{
	
	private JPanel jp;
	private PnZModel pnzm;
	private PnZController pnzc;
	
	public PnZView(){
		super();
		jp = new JPanel(new GridLayout(6,5));
		pnzm = new PnZModel();
		pnzm.addObserver(this);
		pnzc = new PnZController(pnzm,this);
		for (int i=0; i<5; i++){
			for (int j=0; j<5; j++){
				JButton b = new JButton("Place plant here");
				b.setName("button,"+i+","+j);
				b.addActionListener(pnzc);
				jp.add(b);
			}
		}
		JButton b = new JButton("Start Wave");
		b.setName("start");
		b.addActionListener(pnzc);
		jp.add(b);
		JTextArea ja = new JTextArea("Sun Points: "+ pnzm.getSunPoints());
		ja.setEditable(false);
		jp.add(ja);
		this.setContentPane(jp);
	}
	
	public static void main(String[] args) {
		PnZView pzv = new PnZView();
		pzv.setSize(900, 900);
		pzv.setVisible(true);
	}

	public PnZModel getPnzm() {
		return pnzm;
	}

	public void setPnzm(PnZModel pnzm) {
		this.pnzm = pnzm;
	}

	@Override
	public void update(Observable o, Object arg) {
		JTextArea ja = (JTextArea) jp.getComponent(26);
		ja.setText("Sun Points: "+pnzm.getSunPoints());
		if(pnzm.isRunning()){
			for(int i=0; i<5; i++){
				for(int j=0; j<5; j++){
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
			
		}else{
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
			JButton b = (JButton)jp.getComponent(25);
			b.setEnabled(false);
			if(pnzm.getRemaining()==0){
				JOptionPane.showMessageDialog(this, "Game Over you won");
			}else{
				JOptionPane.showMessageDialog(this, "Game Over you lost");
			}
		}
	}

}
