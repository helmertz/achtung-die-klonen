package se.chalmers.tda367.group7.achtung.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.tda367.group7.achtung.model.powerups.FatPowerUp;

public class PowerUpTest {
	
	private PowerUp<PowerUpEffect> p;
	
	@Before
	public void setup() {
		p = new PowerUp<PowerUpEffect>(new FatPowerUp());
	}
	
	@Test
	public void testResetTimer() {
		setup();
		p.resetTimer();
		assertTrue(p.getTimeLeft() == 150);
	}
	
	@Test
	public void testActive() {
		p.resetTimer();
		assertTrue(p.isActive());
		for(int i = 0; i<150; i++) {
			p.update();
		}
		
		assertTrue(!p.isActive());
	}
	
	@Test
	public void testTimeLeft() {
		setup();
		for(int i = 0; i < 75; i++) {
			p.update();
		}
		
		assertTrue(p.getTimeLeft() == 75);
	}
}
