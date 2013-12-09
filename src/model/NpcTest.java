package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NpcTest {

	Npc npc;
	PnZModel pnzm;
	
	@Before
	public void setUp() throws Exception {
		pnzm = new PnZModel();					//make a new game
		npc = new Npc("npc", 5, 2, 0, 0, pnzm);	//add a random npc somewhere 0,0 it looks like
	}

	
	@Test
	public void testNpc() {
		assertNotNull(npc);			//make sure it created and has stats
		assertNotNull(npc.col);
		assertNotNull(npc.row);
		assertNotNull(npc.damage);
		assertNotNull(npc.health);
		assertNotNull(npc.type);
	}

	@Test
	public void testGetType() {					//make sure it returns its type
		assertEquals(npc.getType(),"npc");
	}

	@Test
	public void testGetHealth() {
		assertEquals(npc.getHealth(), 5);		//make sure it returns its health
	}

	@Test
	public void testGetDamage() {				//make sure it returns its damage
		assertEquals(npc.getDamage(), 2);
	}

	@Test
	public void testDamaged() {					//make sure it handles being damaged properly
		int i = npc.damaged(0);					//make sure that a non lethal hit doesn't return death
		assertEquals(i,-1);
		i = npc.damaged(10);					//make sure a lethal hit does return death
		assert(i>=0);
	}

	@Test
	public void testUpdate() {					//make sure update stores health
		int i = npc.oldHealth.size();
		npc.update(pnzm, null);
		assert(npc.oldHealth.size()>i);
	}

	@Test
	public void testUndo() {					//make sure undo gets old health back
		int i = npc.getHealth();
		npc.damaged(1);
		assertEquals(i-1,npc.getHealth());
		npc.undo(pnzm);
		assertEquals(i, npc.getHealth());		
	}

	@Test
	public void testRedo() {					//make sure redo gets undone health back
		int i = npc.getHealth();
		npc.damaged(1);
		assertEquals(i-1,npc.getHealth());
		npc.undo(pnzm);
		assertEquals(i, npc.getHealth());
		npc.redo(pnzm);
		assertEquals(i-1,npc.getHealth());
	}

	@Test
	public void testGetRow() {					//check gets row
		assertEquals(npc.getRow(),0);
	}

	@Test
	public void testSetRow() {					//check set row
		npc.setRow(1);
		assertEquals(npc.getRow(),1);
	}

	@Test
	public void testGetCol() {					//check get col
		assertEquals(npc.getCol(),0);
	}

	@Test
	public void testSetCol() {					//check set col
		npc.setCol(6);
		assertEquals(npc.getCol(),6);
	}

}
