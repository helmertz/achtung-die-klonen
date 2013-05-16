package se.chalmers.tda367.group7.achtung.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.tda367.group7.achtung.model.powerups.ClearPowerUp;
import se.chalmers.tda367.group7.achtung.model.powerups.FatPowerUp;
import se.chalmers.tda367.group7.achtung.model.powerups.InvertedControlsPowerUp;
import se.chalmers.tda367.group7.achtung.model.powerups.MorePowerUp;
import se.chalmers.tda367.group7.achtung.model.powerups.NoTailPowerUp;
import se.chalmers.tda367.group7.achtung.model.powerups.RemoveWallsPowerUp;
import se.chalmers.tda367.group7.achtung.model.powerups.SlowPowerUp;
import se.chalmers.tda367.group7.achtung.model.powerups.SpeedPowerUp;
import se.chalmers.tda367.group7.achtung.model.powerups.ThinPowerUp;
import se.chalmers.tda367.group7.achtung.model.powerups.TurnPowerUp;

public class PowerUpTest {

	private PowerUp<PowerUpEffect> p;
	private Body b;

	// private Round r;

	@Before
	public void createBody() {
		this.b = new Body(new Position(0f, 0f), 0);
	}

	// @Before
	// public void createRound() {
	// this.r = new Round(map, players, powerUpChance);
	// }

	public void createPowerUp(String powerUp) {
		switch (powerUp) {
		case "Clear":
			this.p = new PowerUp<PowerUpEffect>(new ClearPowerUp());
			break;
		case "Fat":
			this.p = new PowerUp<PowerUpEffect>(new FatPowerUp());
			break;
		case "InvertedControls":
			this.p = new PowerUp<PowerUpEffect>(new InvertedControlsPowerUp());
			break;
		case "More":
			this.p = new PowerUp<PowerUpEffect>(new MorePowerUp());
			break;
		case "NoTail":
			this.p = new PowerUp<PowerUpEffect>(new NoTailPowerUp());
			break;
		case "RemoveWalls":
			this.p = new PowerUp<PowerUpEffect>(new RemoveWallsPowerUp());
			break;
		case "Slow":
			this.p = new PowerUp<PowerUpEffect>(new SlowPowerUp());
			break;
		case "Speed":
			this.p = new PowerUp<PowerUpEffect>(new SpeedPowerUp());
			break;
		case "Thin":
			this.p = new PowerUp<PowerUpEffect>(new ThinPowerUp());
			break;
		case "Turn":
			this.p = new PowerUp<PowerUpEffect>(new TurnPowerUp());
			break;
		}
	}

	@Test
	public void testResetTimer() {
		createPowerUp("Fat");
		this.p.resetTimer();
		assertTrue(this.p.getTimeLeft() == 150);
	}

	@Test
	public void testActive() {
		createPowerUp("Fat");
		this.p.resetTimer();
		assertTrue(this.p.isActive());
		for (int i = 0; i < 150; i++) {
			this.p.update();
		}

		assertTrue(!this.p.isActive());
	}

	@Test
	public void testTimeLeft() {
		createPowerUp("Fat");
		for (int i = 0; i < 75; i++) {
			this.p.update();
		}

		assertTrue(this.p.getTimeLeft() == 75);
	}

	@Test
	public void testClearPowerUp() {
		createBody();
		createPowerUp("Clear");
		for (int i = 0; i < 100; i++) {
			this.b.update();
		}
		if (this.b.getBodySegments().size() != 0) {
			this.b.addPowerUp((BodyPowerUpEffect) this.p.getEffect());
			assertTrue(this.b.getBodySegments().size() == 0);
		}
	}

	@Test
	public void testFatPowerUp() {
		createBody();
		createPowerUp("Fat");
		float f = this.b.getWidth();
		this.b.addPowerUp((BodyPowerUpEffect) this.p.getEffect());
		assertTrue(this.b.getWidth() == 2 * f);
		for (int i = 0; i < this.p.getEffect().getDuration(); i++) {
			this.b.update();
		}
		assertTrue(this.b.getWidth() == f);
	}

}
