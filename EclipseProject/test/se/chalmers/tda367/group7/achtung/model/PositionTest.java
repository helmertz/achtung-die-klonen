package se.chalmers.tda367.group7.achtung.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PositionTest {

	private World world;
	private int worldWidth;
	private int worldHeight;
	
	@Before
	public void setUpWorld() {
		this.world = new World(1000,1000);
		this.worldHeight = world.getHeight();
		this.worldWidth = world.getWidth();
	}
	
	// Currently only tests x position, but y is generated 
	// same way so should be correct as well
	@Test
	public void randomPositionsGeneratedEvenlyDistrubuted() {
		// Since the positions are random, we can never test
		// complete randomness, have to decide on a number
		int testIterations = 1000000;
		double marginError = testIterations * 0.001;
		List<Position> randomPositions = new ArrayList<Position>();
		int segmentSplits = 15;

		// Generate a bunch of positions
		for(int i = 0; i < testIterations; i++) {
			randomPositions.add(Position.getRandomPosition(0, worldWidth, 0, worldHeight));
		}
		
		int[] amountPositionsInSplits = new int[segmentSplits];
		double segmentWidth = worldWidth / segmentSplits;
		
		// Count the amount of positions in each segment
		for(Position pos : randomPositions) {
			for(int i = 0; i < segmentSplits; i++) {
				if(pos.getX() >= segmentWidth * i && 
						pos.getX() <= segmentWidth * i + segmentWidth) {
					amountPositionsInSplits[i]++;
				}
			}
		}
		
		// Check if difference between amount of positions in
		// each segment is lower than margin of error
		int initialElementNumber = amountPositionsInSplits[0];
		boolean loopPassed = true;
		for(int i = 1; i < amountPositionsInSplits.length; i++) {
			if(Math.abs(initialElementNumber - amountPositionsInSplits[i]) >= marginError) {
				loopPassed = false;
				break;
			}
		}
		
		assertTrue(loopPassed);
	
	}
	
}
