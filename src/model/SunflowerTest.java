package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SunflowerTest {

	private PnZModel pnzm;
	private Sunflower sf;
	
	@Before
	public void setUp() throws Exception {
		pnzm = new PnZModel();
		sf = new Sunflower(pnzm, 0, 0);
		pnzm.placePlant(0, 0, sf);
	}

	@Test
	public void testDamaged() {
		assertEquals(-1, sf.damaged(0));
		assert(0 <= sf.damaged(10000));
	}

	@Test
	public void testUpdate() {
		pnzm.startWave();
		int i = pnzm.getSunPoints();
		pnzm.startWave();
		int j = pnzm.getSunPoints();
		assertNotEquals(i,j);
	}

	@Test
	public void testUndo() {
		pnzm.undo();
		assertEquals(0,sf.getSunPoints());
	}

	@Test
	public void testRedo() {
		pnzm.undo();
		assertEquals(0,sf.getSunPoints());
		pnzm.redo();
		assertNotEquals(0,sf.getSunPoints());
	}

	@Test
	public void testSunflower() {
		assertNotNull(sf);
	}

	@Test
	public void testPlay() {
		int i = pnzm.getSunPoints();
		sf.play(pnzm);
		int j = pnzm.getSunPoints();
		assertNotEquals(i,j);
	}

	@Test
	public void testSetDisabled() {
		sf.damaged(0);
		assertEquals(5, sf.getSunPoints());
		sf.damaged(10000);
		assertEquals(0, sf.getSunPoints());
	}

}
