package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlantTest {
	
	Plant pl;
	PnZModel pnzm;

	@Before
	public void setUp() throws Exception {
		pnzm = new PnZModel();								//make a new game and add a plant
		pl = new Plant("test", 1, 1, 5, 0, 0, pnzm);
	}

	@Test
	public void testPlant() {								//test to make sure the plant isn't null
		assertNotNull(pl);
	}

	@Test
	public void testGetCost() {								//test the cost is correct
		assertEquals(5,pl.getCost());
	}

	@Test
	public void testSetCost() {								//test setting cost works
		pl.setCost(100);
		assertEquals(100, pl.getCost());
	}

}
