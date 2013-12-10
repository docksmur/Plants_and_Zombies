package model;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * 
 * an Big Zombie type enemy
 * @author Murdock Walsh
 * @author David Falardeau
 * @version ver 2.1
 * 
 */
public class BigZombie extends Enemy implements Observer, Serializable {

	public BigZombie(int row, int col, Observable pnz) {
		super("Big Zombie", 12, 3, row, col, pnz);
	}

}
