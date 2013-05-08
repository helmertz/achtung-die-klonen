package se.chalmers.tda367.group7.achtung.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;
import se.chalmers.tda367.group7.achtung.model.powerups.NoTailPowerUp;

/**
 * Class containing the data for a game currently playing.
 */
public class Round {

	private boolean wallsAreActive;

	private final List<Player> players;
	private final List<PowerUpEntity> powerUpEntities;
	private final List<PowerUp> activeWorldEffects;
	private final CollisionHelper collisionHelper;
	private final Map map;

	private int deadPlayers;

	private final PropertyChangeSupport pcs;

	// TODO figure out a good constant here
	private static final float DEFAULT_POWERUP_CHANCE = 0.01f;

	private float powerUpChance;

	private Player winner;

	public Round(Map map, List<Player> players) {
		this.map = map;
		this.players = players;
		this.powerUpEntities = new ArrayList<PowerUpEntity>();
		this.activeWorldEffects = new ArrayList<PowerUp>();
		this.deadPlayers = 0;

		this.pcs = new PropertyChangeSupport(this);

		this.powerUpChance = DEFAULT_POWERUP_CHANCE;
		this.wallsAreActive = true;

		createPlayerBodiesAtRandomPos();

		BodyPowerUpEffect noTail = new NoTailPowerUp();

		for (Player p : players) {
			p.getBody().addPowerUp(noTail);
		}

		this.collisionHelper = new CollisionHelper(map, players);
	}

	public void update() {
		if (isRoundActive()) {
			updatePlayers();
			if (Math.random() <= this.powerUpChance) {
				this.powerUpEntities.add(PowerUpFactory
						.getRandomEntity(this.map));
				this.pcs.firePropertyChange("PowerUp", false, true);
			}

			updateWorldPowerUps();
		}
	}

	private void updateWorldPowerUps() {
		Iterator<PowerUp> iter = this.activeWorldEffects.iterator();

		while (iter.hasNext()) {
			PowerUp p = iter.next();
			p.update();
			if (!p.isActive()) {
				p.removeEffect(this);
				iter.remove();
			}
		}
	}

	private void updatePlayers() {
		// Used for checking who won, could probably be done better
		Player lastAlive = null;
		for (Player player : this.players) {
			player.update();
			if (player.getBody().isDead()) {
				continue;
			}
			// TODO: don't do this if player is immortal,
			// but must still be able to go through walls
			// (which is done in CollisionHelper as of writing this)
			handleCollisions(player);

			// Allows null to prevent problem with remaining players dying at
			// the same time
			if (lastAlive == null || !player.getBody().isDead()) {
				lastAlive = player;
			}
		}

		if (isOnePlayerLeft()) {
			this.winner = lastAlive;

			// Kills to prevent rendering problem
			lastAlive.getBody().kill();

			this.pcs.firePropertyChange("RoundOver", false, true);
		}
	}

	private void handleCollisions(Player player) {
		if (this.collisionHelper.hasCollidedWithOthers(player)) {
			killPlayer(player);
			distributePoints();
		} else if (this.collisionHelper.isPlayerOutOfBounds(player)) {
			if (this.wallsAreActive) {
				killPlayer(player);
				distributePoints();
			} else {
				this.collisionHelper.mirrorPlayerPosition(player);
			}
		} else {
			handleCollisionWithPowerUp(player);
		}
	}

	private void handleCollisionWithPowerUp(Player player) {
		Iterator<PowerUpEntity> iterator = this.powerUpEntities.iterator();

		while (iterator.hasNext()) {
			PowerUpEntity powerUp = iterator.next();

			if (this.collisionHelper.hasCollidedWithPowerUp(player, powerUp)) {
				iterator.remove();
				distributePowerUp(player, powerUp);
				this.pcs.firePropertyChange("PowerUp"
						+ powerUp.getType().toString(), false, true);
				this.pcs.firePropertyChange("PowerUp", false, true);
			}
		}
	}

	private void killPlayer(Player player) {
		player.getBody().kill();
		this.pcs.firePropertyChange("PlayerDied", false, true);
		if (player.getBody().isDead()) {
			this.deadPlayers++;
		}
	}

	private void distributePoints() {
		for (Player p : this.players) {
			if (!p.getBody().isDead()) {
				p.addPoints(1);
			}
		}
	}

	private boolean isOnePlayerLeft() {
		return this.players.size() - this.deadPlayers < 2;
	}

	private void distributePowerUp(Player pickedUpByPlayer,
			PowerUpEntity powerUp) {
		PowerUpEffect effect = powerUp.getPowerUpEffect();

		// TODO: this is probably not the best way of doing this
		if (effect instanceof BodyPowerUpEffect) {
			distributePlayerEffect(pickedUpByPlayer, powerUp);
		}
		if (effect instanceof WorldPowerUpEffect) {
			distributeWorldEffect(powerUp);
		}
	}

	private void distributeWorldEffect(PowerUpEntity powerUp) {
		WorldPowerUpEffect effect = (WorldPowerUpEffect) powerUp
				.getPowerUpEffect();
		effect.applyEffect(this);
		this.activeWorldEffects.add(new PowerUp(effect));
	}

	/**
	 * @pre The effect in powerUp is a PlayerPowerUpEffect.
	 * @param powerUp
	 *            The effect to be applied.
	 */
	private void distributePlayerEffect(Player pickedUpByPlayer,
			PowerUpEntity powerUp) {
		Type powerUpType = powerUp.getType();
		BodyPowerUpEffect effect = (BodyPowerUpEffect) powerUp
				.getPowerUpEffect();

		if (powerUpType == PowerUpEntity.Type.SELF) {
			addPowerUpToSelf(pickedUpByPlayer, effect);
		} else if (powerUpType == PowerUpEntity.Type.EVERYONE) {
			addPowerUpToEveryone(effect);
		} else {
			addPowerUpToOthers(pickedUpByPlayer, effect);
		}
	}

	private void addPowerUpToSelf(Player player, BodyPowerUpEffect effect) {
		player.getBody().addPowerUp(effect);
	}

	private void addPowerUpToEveryone(BodyPowerUpEffect effect) {
		for (Player p : this.players) {
			p.getBody().addPowerUp(effect);
		}
	}

	private void addPowerUpToOthers(Player pickedUpByPlayer,
			BodyPowerUpEffect effect) {
		for (Player p : this.players) {
			if (p != pickedUpByPlayer && !p.getBody().isDead()) {
				p.getBody().addPowerUp(effect);
			}
		}
	}

	/**
	 * @return true if there are players still alive
	 */
	public boolean isRoundActive() {
		return this.deadPlayers < this.players.size() - 1;
	}

	private void createPlayerBodiesAtRandomPos() {
		for (Player player : this.players) {

			player.setBody(BodyFactory.getBody(this.map));
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		this.pcs.addPropertyChangeListener(pcl);
	}

	public Map getMap() {
		return this.map;
	}
	
	public void setMapColor(Color color) {
		this.map.setColor(color);
		pcs.firePropertyChange("Map", false, true);
	}

	public List<PowerUpEntity> getPowerUpEntities() {
		return this.powerUpEntities;
	}

	public void setWallsActive(boolean wallsActive) {
		this.wallsAreActive = wallsActive;
	}

	public float getDefaultPowerUpChance() {
		return DEFAULT_POWERUP_CHANCE;
	}

	public void setPowerUpChance(float powerUpChance) {
		this.powerUpChance = powerUpChance;
	}

	public Player getWinner() {
		return this.winner;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

}
