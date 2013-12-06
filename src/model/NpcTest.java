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
		pnzm = new PnZModel();
		npc = new Npc("npc", 5, 2, 0, 0, pnzm);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNpc() {
		assertNotNull(npc);
		assertNotNull(npc.col);
		assertNotNull(npc.row);
		assertNotNull(npc.damage);
		assertNotNull(npc.health);
		assertNotNull(npc.type);
	}

	@Test
	public void testGetType() {
		assert(npc.getType().equalsIgnoreCase("pea shooter"));
	}

	@Test
	public void testGetHealth() {
		assertEquals(npc.getHealth(), 5);
	}

	@Test
	public void testGetDamage() {
		assertEquals(npc.getDamage(), 2);
	}

	@Test
	public void testDamaged() {
		int i = npc.damaged(0);
		assertEquals(i,-1);
		i = npc.damaged(10);
		assert(i>=0);
	}

	@Test
	public void testUpdate() {
		int i = npc.oldHealth.size();
		npc.update(pnzm, null);
		assert(npc.oldHealth.size()>i);
	}

	@Test
	public void testUndo() {
		int i = npc.getHealth();
		npc.damaged(1);
		assertEquals(i-1,npc.getHealth());
		npc.undo(pnzm);
		assertEquals(i, npc.getHealth());		
	}

	@Test
	public void testRedo() {
		int i = npc.getHealth();
		npc.damaged(1);
		assertEquals(i-1,npc.getHealth());
		npc.undo(pnzm);
		assertEquals(i, npc.getHealth());
		npc.redo(pnzm);
		assertEquals(i-1,npc.getHealth());
	}

	@Test
	public void testGetRow() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRow() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCol() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCol() {
		fail("Not yet implemented");
	}

}
