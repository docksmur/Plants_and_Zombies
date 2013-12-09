package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BigZombieTest {
	
	BigZombie bz;

	@Before
	public void setUp() throws Exception {
		bz = new BigZombie(1, 1, new PnZModel());
	}

	@Test
	public void testBigZombie() {
		assertNotNull(bz);
	}

}
