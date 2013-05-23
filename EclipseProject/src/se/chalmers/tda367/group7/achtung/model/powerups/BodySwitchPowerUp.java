package se.chalmers.tda367.group7.achtung.model.powerups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.Body.TurnMode;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.model.RoundPowerUpEffect;

public class BodySwitchPowerUp implements RoundPowerUpEffect {

	private static final String NAME = "body-switch";
	private static final int DURATION = 0;
	private static final boolean STACKABLE = false;

	@Override
	public int getDuration() {
		return DURATION;
	}

	@Override
	public void applyEffect(Round round) {
		// Shuffles the living player's bodies, but keeps the turnmode
		// TODO could probably be optimized
		List<Body> bodies = new ArrayList<Body>();
		List<Player> players = new ArrayList<Player>();
		List<TurnMode> turnModes = new ArrayList<TurnMode>();
		for (Player p : round.getPlayers()) {
			Body b = p.getBody();
			if (!b.isDead()) {
				players.add(p);
				bodies.add(p.getBody());
				turnModes.add(p.getBody().getTurnMode());
			}
		}
		Collections.shuffle(bodies);
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			Body newBody = bodies.get(i);
			p.setBody(newBody);
			newBody.setTurnMode(turnModes.get(i));
		}
	}

	@Override
	public void removeEffect(Round round) {
	}

	@Override
	public Type[] getAllowedTypes() {
		return new Type[] { Type.EVERYONE };
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isStackable() {
		return STACKABLE;
	}

	@Override
	public String toString() {
		return NAME;
	}

}
