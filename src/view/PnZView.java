package view;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.PnZModel;

import controller.PnZController;

public class PnZView extends JFrame{
	
	private JPanel jp;
	private PnZModel pnzm;
	private PnZController pnzc;
	
	public PnZView(){
		super();
		jp = new JPanel(new GridLayout(6,5));
		pnzm = new PnZModel();
		pnzc = new PnZController(pnzm);
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

}
