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
		pnzm = new PnZModel();
		pl = new Plant("test", 1, 1, 5, 0, 0, pnzm);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPlant() {
		assertNotNull(pl);
	}

	@Test
	public void testGetCost() {
		assertEquals(5,pl.getCost());
	}

	@Test
	public void testSetCost() {
		pl.setCost(100);
		assertEquals(100, pl.getCost());
	}

}
