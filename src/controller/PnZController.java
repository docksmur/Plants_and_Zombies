package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.PnZModel;

import view.PnZView;

public class PnZController implements ActionListener {
	
	PnZModel pnzm;
	
	public PnZController(PnZModel pnzm){
		this.pnzm = pnzm;
	}

	@Override
	public void actionPerformed(ActionEvent o) {
		Object o2 = o.getSource();
		//PnZModel pnzm = ((PnZView)o).getPnzm();
		JButton b;
		b = (JButton) o2;
		String name = b.getName();
		if (name.equalsIgnoreCase("start")){
			pnzm.startWave();
		}else{
			String[] things = name.split(",");
			System.out.println(things[1]+" "+things[2]);
			int row = Integer.parseInt(things[1]);
			int col = Integer.parseInt(things[2]);
			System.out.println(row+" "+col);
			Object[] possibilities = {"Sunflower", "Pea Shooter"};
			String s = (String)JOptionPane.showInputDialog(new JFrame(),"What type of plant would you like to add?","Customized Dialog",
			                    JOptionPane.QUESTION_MESSAGE, null, possibilities, "");
			pnzm.placePlant(row, col, s);
			b.setText(s);
			b.setEnabled(false);
		}
	}

}
