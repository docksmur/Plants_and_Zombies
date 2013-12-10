package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EnemyTest {

	private Enemy en;
	private PnZModel pnzm;
	
	@Before
	public void setUp() throws Exception {
		pnzm = new PnZModel();						//set up a new game
		en = (Enemy) pnzm.getGrid().get(0).get(5);	//make this enemy the enemy at row:0 col:5
	}

	@Test
	public void testUpdate() {
		int i = en.getCol();			//get the current location
		en.update(pnzm, "move");		//call a move update which should move him one forward
		assertEquals(i-1, en.getCol());	//if his new location is one less than the old it worked
	}

	@Test
	public void testUndo() {
		int initLoc = en.getCol();		//get the current column
		en.update(pnzm, "move");		//move him
		int newLoc = en.getCol();		//get new location
		assertEquals(initLoc-1,newLoc);	//make sure he did indeed move
		en.undo(pnzm);					//undo the move
		int undoLoc = en.getCol();		//get the old location and ensure it's the same as the first
		assertEquals(initLoc,undoLoc);	
	}

	@Test
	public void testRedo() {
		en.update(pnzm, "move");		//pretty much the same as undo just reversed
		int initLoc = en.getCol();		
		en.undo(pnzm);					
		int undoLoc = en.getCol();		
		assertEquals(initLoc+1,undoLoc);
		en.redo(pnzm);
		int redoLoc = en.getCol();
		assertEquals(initLoc, redoLoc);
	}

	@Test
	public void testEnemy() {
		assertNotNull(en);				//make sure it was created
		assertEquals(en.health, 5);		//and all if stats are what they should be
		assertEquals(en.getDamage(), 1);
		assertEquals(en.getCol(), 5);
	}

	@Test
	public void testMove() {			//make sure he moves forward
		int initLoc = en.getCol();		//I just realised this and update are basically
		en.update(pnzm, "move");		//duplicate tests..... oh well
		int newLoc = en.getCol();
		assertEquals(initLoc-1,newLoc);
	}

}
