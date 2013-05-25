package se.chalmers.tda367.group7.achtung.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;
import se.chalmers.tda367.group7.achtung.model.powerups.NoTailPowerUp;

/**
 * Class containing the data for a round in the game.
 */
public class Round {

	private boolean wallsAreActive;

	private final List<Player> players;
	private final List<PowerUpEntity> powerUpEntities;
	private final List<PowerUp<RoundPowerUpEffect>> activeRoundEffects;
	private final CollisionHelper collisionHelper;
	private final Map map;

	private int deadPlayers;

	private final PropertyChangeSupport pcs;

	private float powerUpChance;

	private Player winner;

	public Round(Map map, List<Player> players) {
		this.map = map;
		this.players = players;
		this.powerUpEntities = new ArrayList<PowerUpEntity>();
		this.activeRoundEffects = new ArrayList<PowerUp<RoundPowerUpEffect>>();
		this.deadPlayers = 0;

		this.pcs = new PropertyChangeSupport(this);

		this.powerUpChance = Settings.getInstance().getPowerUpChance();
		this.setWallsActive(true);

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

			updateRoundPowerUps();
		}
	}

	private void updateRoundPowerUps() {
		Iterator<PowerUp<RoundPowerUpEffect>> iter = this.activeRoundEffects
				.iterator();

		while (iter.hasNext()) {
			PowerUp<RoundPowerUpEffect> p = iter.next();
			p.update();
			if (!p.isActive()) {
				p.getEffect().removeEffect(this);
				iter.remove();
			}
		}
	}

	private void updatePlayers() {
		for (Player p : this.players) {
			if (!p.getBody().isDead()) {
				// Updates movement only
				p.update();

				// Checks for collisions, kills and distributes points
				handleCollisions(p);

				// Prevent continuing and killing the last player here
				if (isOnePlayerLeft()) {
					break;
				}
			}
		}
		// Goes through the players to find who won
		if (isOnePlayerLeft()) {
			for (Player p : this.players) {
				if (!p.getBody().isDead()) {
					this.winner = p;

					// Kills to prevent rendering problem
					p.getBody().setImmortal(false);
					p.getBody().kill();
					this.deadPlayers++;

					this.pcs.firePropertyChange("RoundOver", false, true);
					break;
				}
			}
		}
	}

	private void handleCollisions(Player player) {
		if (!player.getBody().isImmortal()
				&& this.collisionHelper.hasCollidedWithOthers(player)) {
			killPlayer(player);
		} else if (this.collisionHelper.isPlayerOutOfBounds(player)) {
			if (this.wallsAreActive) {
				if (!player.getBody().isGeneratingHole()) {
					killPlayer(player);
				}
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
			}
		}
	}

	private void killPlayer(Player player) {
		player.getBody().kill();
		if (player.getBody().isDead()) {
			this.pcs.firePropertyChange("PlayerDied", false, true);
			this.deadPlayers++;
			distributePoints();
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
		return this.deadPlayers == this.players.size() - 1;
	}

	private void distributePowerUp(Player pickedUpByPlayer,
			PowerUpEntity powerUp) {
		PowerUpEffect effect = powerUp.getPowerUpEffect();

		// TODO: this is probably not the best way of doing this
		if (effect instanceof BodyPowerUpEffect) {
			distributePlayerEffect(pickedUpByPlayer,
					(BodyPowerUpEffect) effect, powerUp.getType());
		}
		if (effect instanceof RoundPowerUpEffect) {
			distributeRoundEffect((RoundPowerUpEffect) effect);
		}
	}

	private void distributeRoundEffect(RoundPowerUpEffect effect) {
		if (!effect.isStackable()) {
			for (PowerUp<RoundPowerUpEffect> powerUp : this.activeRoundEffects) {
				if (powerUp.getEffect().getClass() == effect.getClass()) {
					powerUp.resetTimer();
					return;
				}
			}
		}
		
		effect.applyEffect(this);
		this.activeRoundEffects.add(new PowerUp<>(effect));
	}

	private void distributePlayerEffect(Player pickedUpByPlayer,
			BodyPowerUpEffect effect, Type type) {
		if (type == PowerUpEntity.Type.SELF) {
			addPowerUpToSelf(pickedUpByPlayer, effect);
		} else if (type == PowerUpEntity.Type.EVERYONE) {
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
		return this.deadPlayers != this.players.size();
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
		this.pcs.firePropertyChange("Map", false, true);
	}

	public List<PowerUpEntity> getPowerUpEntities() {
		return this.powerUpEntities;
	}

	public void setWallsActive(boolean wallsActive) {
		this.wallsAreActive = wallsActive;
		this.map.setWallsActive(wallsActive);
		this.pcs.firePropertyChange("Map", false, true);
	}

	public boolean isWallsActive() {
		return this.wallsAreActive;
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

	public float getPowerUpChance() {
		return this.powerUpChance;
	}

}
