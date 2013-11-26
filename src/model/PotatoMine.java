package model;

import java.util.Observable;
import java.util.Observer;

/**
 * 
 * a potato mine plant. explodes and destroys zombie next to it once it is next to them
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 1.4.1
 * 
 */

public class PotatoMine extends Plant implements Observer{
	
	
	/**
	 * create new potato mine
	 * 
	 * @param pnz the observable
	 */
	public PotatoMine(Observable pnz, int row, int col){
		super("Potato Mine",1, 5, 5, row, col, pnz);
		pnz.addObserver(this);
	}

	
	@Override
	public void update(Observable o, Object obj){
		PnZModel pnzm = (PnZModel) o;
		if (((String)obj).equalsIgnoreCase("plants")){
			super.update(o, obj);
			damage(pnzm);
		}else if (((String)obj).equalsIgnoreCase("undo")){
			undo(pnzm);
		}else if (((String)obj).equalsIgnoreCase("redo")){
			redo(pnzm);
		}
	}

	private void damage(PnZModel pnzm) {
		int i = pnzm.firstInRow(row);
		if (i<5 && i>col && col==i-1){
			System.out.println("damaging zombie");
			if(pnzm.getGrid().get(row).get(i).damaged(this.damage)!=-1){
				pnzm.getGrid().get(row).set(i, null);
				pnzm.getGrid().get(row).set(col, null);
				pnzm.lessRemaining(1);
			}
		}
	}
	
	@Override
	public void undo(PnZModel pnzm){
		super.undo(pnzm);
		if (pnzm.getGrid().get(row).get(col)==null){
			damage=0;
		}
	}
	
	@Override
	public void redo(PnZModel pnzm){
		super.redo(pnzm);
		if (pnzm.getGrid().get(row).get(col)!=null){
			if (pnzm.getGrid().get(row).get(col) instanceof PotatoMine){
				damage=5;
			}
		}
	}
	
	
	@Override
	public int damaged(int damage){
		int i = super.damaged(damage);
		if (i == -1){
			damage = 0;
		}
		return 0;
	}
	
}
