package se.chalmers.tda367.group7.achtung.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.tda367.group7.achtung.model.powerups.FatPowerUp;

public class PowerUpTest {

	private PowerUp<PowerUpEffect> p;

	@Before
	public void setup() {
		this.p = new PowerUp<PowerUpEffect>(new FatPowerUp());
	}

	@Test
	public void testResetTimer() {
		setup();
		this.p.resetTimer();
		assertTrue(this.p.getTimeLeft() == 150);
	}

	@Test
	public void testActive() {
		this.p.resetTimer();
		assertTrue(this.p.isActive());
		for (int i = 0; i < 150; i++) {
			this.p.update();
		}

		assertTrue(!this.p.isActive());
	}

	@Test
	public void testTimeLeft() {
		setup();
		for (int i = 0; i < 75; i++) {
			this.p.update();
		}

		assertTrue(this.p.getTimeLeft() == 75);
	}
}
