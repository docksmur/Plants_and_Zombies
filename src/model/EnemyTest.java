package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EnemyTest {

	Enemy en;
	PnZModel pnzm;
	
	@Before
	public void setUp() throws Exception {
		pnzm = new PnZModel();
		en = (Enemy) pnzm.getGrid().get(0).get(5);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUpdate() {
		int i = en.getCol();
		en.update(pnzm, "move");
		assertEquals(i-1, en.getCol());
	}

	@Test
	public void testUndo() {
		int initLoc = en.getCol();
		en.update(pnzm, "move");
		int newLoc = en.getCol();
		assertEquals(initLoc-1,newLoc);
		en.undo(pnzm);
		int undoLoc = en.getCol();
		assertEquals(initLoc,undoLoc);
	}

	@Test
	public void testRedo() {
		en.update(pnzm, "move");
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
		assertNotNull(en);
		assertEquals(en.health, 5);
		assertEquals(en.getDamage(), 1);
		assertEquals(en.getCol(), 5);
	}

	@Test
	public void testMove() {
		int initLoc = en.getCol();
		en.update(pnzm, "move");
		int newLoc = en.getCol();
		assertEquals(initLoc-1,newLoc);
	}

}
