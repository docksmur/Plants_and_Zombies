package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PnZModelTest {
	
	PnZModel pnzm;

	@Before
	public void setUp() throws Exception {
		pnzm = new PnZModel();				//make a new game
	}


	@Test
	public void testPnZModel() {
		assert(pnzm!=null);					//check the game was in fact made
	}

	@Test
	public void testPlacePlantIntIntString() {
		assert(pnzm.getGrid().get(0).get(0)==null);	//make sure there's no plant before we place one
		pnzm.placePlant(0, 0, "Sunflower");			//place one
		assert(pnzm.getGrid().get(0).get(0)!=null);	//make sure it's there now
	}

	@Test
	public void testStartWave() {
		pnzm.startWave();
		assert(pnzm.isRunning());		//make sure the game is running 
		assert(pnzm.getRemaining()>0);	//make sure there are still enemies and thus the game is still valid
	}

	@Test
	public void testFirstInRow() {		//find the first in each row
		int fir = pnzm.firstInRow(0);
		assertEquals(fir, 5);
	}

	@Test
	public void testGetSunPoints() {			//test getting sunpoints
		assertEquals(pnzm.getSunPoints(),100);	//confirm starting loc
		pnzm.placePlant(0, 0, "Sunflower");		//assert a sunflower costs 1 sun point
		assertEquals(pnzm.getSunPoints(),99);	
		pnzm.placePlant(0, 0, "Pea Shooter");	//assert a pea shooter costs 5 sun points
		assertEquals(pnzm.getSunPoints(),94);
	}

	@Test
	public void testAddSunPoints() {
		assertEquals(pnzm.getSunPoints(),100);	//confirm start
		pnzm.addSunPoints(100);					//confirm added value is there
		assertEquals(pnzm.getSunPoints(),200);
	}

	@Test
	public void testGetGrid() {
		for (int i=0;i<5;i++){							//confirm the grid has the starting enemies
			assert(pnzm.getGrid().get(i).get(5)!=null);
		}
		pnzm.placePlant(4, 4, "Sunflower");				//make sure the grid that returns changed after placing a flower
		assert(pnzm.getGrid().get(4).get(4)!=null);
		
	}

	@Test
	public void testIsRunning() {
		assert(pnzm.isRunning());						//test the game running returns right
	}

	@Test
	public void testSetRunning() {
		assert(pnzm.isRunning());						//test that you can change the running state
		pnzm.setRunning(false);
		assert(!pnzm.isRunning());
	}

	@Test
	public void testGetRemaining() {					//test that when pea shooters kill all the enemies there are none remaining
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
