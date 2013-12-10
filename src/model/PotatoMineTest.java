package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PotatoMineTest {

	public PotatoMine pm;
	public PnZModel pnzm;
	
	@Before
	public void setUp() throws Exception {
		pnzm = new PnZModel();
		pm = new PotatoMine(pnzm, 0, 3);
		pnzm.placePlant(0, 3, pm);
	}

	@Test
	public void testDamaged() {
		assertEquals(-1, pm.damaged(0));
		assert(0<=pm.damaged(100000000));
	}

	@Test
	public void testUpdate() {
		Npc n = pnzm.getGrid().get(0).get(5);
		int i=n.getHealth();
		pnzm.startWave();
		int j = n.getHealth();
		assertNotEquals(i,j);
	}

	@Test
	public void testUndo() {
		pnzm.undo();
		assertEquals(0,pm.getDamage());
	}

	@Test
	public void testRedo() {
		assertNotEquals(0,pm.getDamage());
		pnzm.undo();
		assertEquals(0,pm.getDamage());
		pnzm.redo();
		assertNotEquals(0,pm.getDamage());
	}

	@Test
	public void testPotatoMine() {
		assertNotNull(pm);
	}

}
