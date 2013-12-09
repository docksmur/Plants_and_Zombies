package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PeaShooterTest {

	PnZModel pnzm;
	PeaShooter ps;
	
	@Before
	public void setUp() throws Exception {
		pnzm = new PnZModel();
		ps = new PeaShooter(pnzm, 0, 0);
		pnzm.placePlant(0, 0, ps);
	}

	@Test
	public void testDamaged() {
		assertEquals(-1, ps.damaged(0));
		assert(0 <= ps.damaged(10000));
	}

	@Test
	public void testUpdate() {
		pnzm.startWave();
		int i = pnzm.getGrid().get(0).get(4).getHealth();
		pnzm.startWave();
		int j = pnzm.getGrid().get(0).get(3).getHealth();
		assertNotEquals(i,j);
	}

	@Test
	public void testUndo() {
		pnzm.undo();
		assertEquals(0,ps.getDamage());
	}

	@Test
	public void testRedo() {
		pnzm.undo();
		assertEquals(0,ps.getDamage());
		pnzm.redo();
		assertNotEquals(0,ps.getDamage());
	}

	@Test
	public void testPeaShooter() {
		assertNotNull(ps);
	}

}
