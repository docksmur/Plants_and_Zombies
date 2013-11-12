package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PnZModelTest {
	
	PnZModel pnzm;

	@Before
	public void setUp() throws Exception {
		pnzm = new PnZModel();
	}


	@Test
	public void testPnZModel() {
		assert(pnzm!=null);
	}

	@Test
	public void testPlacePlantIntIntString() {
		assert(pnzm.getGrid().get(0).get(0)==null);
		pnzm.placePlant(0, 0, "Sunflower");
		assert(pnzm.getGrid().get(0).get(0)!=null);
	}

	@Test
	public void testStartWave() {
		pnzm.startWave();
		assert(pnzm.isRunning());
		assert(pnzm.getRemaining()>0);
	}

	@Test
	public void testFirstInRow() {
		int fir = pnzm.firstInRow(0);
		assertEquals(fir, 5);
	}

	@Test
	public void testGetRowDamage() {
		assertEquals(pnzm.getRowDamage(0),0);
		pnzm.placePlant(0, 0, "Sunflower");
		assert(pnzm.getRowDamage(0)>0);
	}

	@Test
	public void testGetSunPoints() {
		assertEquals(pnzm.getSunPoints(),100);
		pnzm.placePlant(0, 0, "Sunflower");
		assertEquals(pnzm.getSunPoints(),99);
		pnzm.placePlant(0, 0, "Pea Shooter");
		assertEquals(pnzm.getSunPoints(),94);
	}

	@Test
	public void testAddSunPoints() {
		assertEquals(pnzm.getSunPoints(),100);
		pnzm.addSunPoints(100);
		assertEquals(pnzm.getSunPoints(),200);
	}

	@Test
	public void testGetGrid() {
		for (int i=0;i<5;i++){
			assert(pnzm.getGrid().get(i).get(5)!=null);
		}
		pnzm.placePlant(4, 4, "Sunflower");
		assert(pnzm.getGrid().get(4).get(4)!=null);
		
	}

	@Test
	public void testIsRunning() {
		assert(pnzm.isRunning());
	}

	@Test
	public void testSetRunning() {
		assert(pnzm.isRunning());
		pnzm.setRunning(false);
		assert(!pnzm.isRunning());
	}

	@Test
	public void testGetRemaining() {
		assert(pnzm.getRemaining()>0);
		pnzm.placePlant(0, 0, "Pea Shooter");
		pnzm.placePlant(1, 0, "Pea Shooter");
		pnzm.placePlant(2, 0, "Pea Shooter");
		pnzm.placePlant(3, 0, "Pea Shooter");
		pnzm.placePlant(4, 0, "Pea Shooter");
		pnzm.startWave();
		pnzm.startWave();
		pnzm.startWave();
		assert(pnzm.getRemaining()==0);
	}

}
