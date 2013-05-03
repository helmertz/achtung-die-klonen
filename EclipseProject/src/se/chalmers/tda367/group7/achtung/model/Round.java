package se.chalmers.tda367.group7.achtung.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

/**
 * Class containing the data for a game currently playing.
 */
public class Round {
	
	private boolean wallsAreActive;
	
	private List<Player> players;
	private List<PowerUpEntity> powerUpEntities;
	private List<PowerUp> activeWorldEffects;
	private CollisionHelper collisionHelper;
	private Map map;
	
	private int deadPlayers;
	
	private PropertyChangeSupport pcs;
	
	
	// TODO figure out a good constant here
	private static final float DEFAULT_POWERUP_CHANCE = 0.01f;

	private float powerUpChance;

	public Round(Map map, List<Player> players) {
		this.map = map;
		this.players = players;
		powerUpEntities = new ArrayList<PowerUpEntity>();
		activeWorldEffects = new ArrayList<PowerUp>();
		deadPlayers = 0;
		
		this.pcs = new PropertyChangeSupport(this);

		
		powerUpChance = DEFAULT_POWERUP_CHANCE;
		wallsAreActive = false;
		
		createPlayerBodiesAtRandomPos();
		
		collisionHelper = new CollisionHelper(map, players);
	}

	public void update() {
		if (isRoundActive()) {
			updatePlayers();
			if (Math.random() <= powerUpChance) {
				powerUpEntities.add(PowerUpFactory.getRandomEntity(map));
				pcs.firePropertyChange("PowerUp", false, true);
			}
			
			updateWorldPowerUps();
		}
	}

	private void updateWorldPowerUps() {
		Iterator<PowerUp> iter = activeWorldEffects.iterator();
		
		while(iter.hasNext()) {
			PowerUp p = iter.next();
			p.update();
			if(!p.isActive()) {
				p.removeEffect(this);
				iter.remove();
			}
		}
	}

	private void updatePlayers() {
		for (Player player : players) {
			player.update();
			
			if(player.getBody().isDead()) {
				continue;
			}
			// TODO: don't do this if player is immortal, 
			// but must still be able to go through walls
			// (which is done in CollisionHelper as of writing this)
			handleCollisions(player);
		}
		
		if(isOnePlayerLeft()) {
			killRemainingPlayers();
			pcs.firePropertyChange("RoundOver", false, true);
		}
	}

	private void handleCollisions(Player player) {
		if (collisionHelper.collidesWithOthers(player)) {
			killPlayer(player);
			distributePoints();
		} else if (collisionHelper.isPlayerOutOfBounds(player)) {
			if (wallsAreActive) {
				killPlayer(player);
				distributePoints();
			} else {
				collisionHelper.mirrorPlayerPosition(player);
			}
		} else {
			Iterator<PowerUpEntity> iterator = powerUpEntities.iterator();
			
			while (iterator.hasNext()) {
				PowerUpEntity powerUp = iterator.next();

				
				if (collisionHelper.playerCollidedWithPowerUp(player, powerUp)) {
					iterator.remove();
					distributePowerUp(player, powerUp);
					pcs.firePropertyChange("PowerUp" + powerUp.getType().toString(), false, true);
					pcs.firePropertyChange("PowerUp", false, true);
				}
			}
		}
	}
	
	private void killPlayer(Player player) {
		player.getBody().kill();
		pcs.firePropertyChange("PlayerDied", false, true);
		if (player.getBody().isDead()) {
			deadPlayers++;
		}
	}

	private void distributePoints() {
		for (Player p : players) {
			if (!p.getBody().isDead()) {
				p.addPoints(1);
			}
		}
	}

	private boolean isOnePlayerLeft() {
		return players.size() - deadPlayers < 2;
	}
	
	private void killRemainingPlayers() {
		for (Player p : players) {
			if (!p.getBody().isDead()) {
				p.getBody().kill();
			}
		}
	}

	private void distributePowerUp(Player pickedUpByPlayer, PowerUpEntity powerUp) {
		PowerUpEffect effect = powerUp.getPowerUpEffect();
		
		// TODO: this is probably not the best way of doing this
		if(effect instanceof PlayerPowerUpEffect) {
			distributePlayerEffect(pickedUpByPlayer, powerUp);
		} else if(effect instanceof WorldPowerUpEffect) {
			distributeWorldEffect(powerUp);
		}
	}

	private void distributeWorldEffect(PowerUpEntity powerUp) {
		WorldPowerUpEffect effect = (WorldPowerUpEffect)powerUp.getPowerUpEffect();
		effect.applyEffect(this);
		activeWorldEffects.add(new PowerUp(effect));
	}

	/**
	 * @pre The effect in powerUp is a PlayerPowerUpEffect.
	 * @param powerUp The effect to be applied.
	 */
	private void distributePlayerEffect(Player pickedUpByPlayer,
			PowerUpEntity powerUp) {
		Type powerUpType = powerUp.getType();
		PlayerPowerUpEffect effect = (PlayerPowerUpEffect)powerUp.getPowerUpEffect();
		
		if (powerUpType == PowerUpEntity.Type.SELF) {
			addPowerUpToSelf(pickedUpByPlayer, effect);
		} else if (powerUpType == PowerUpEntity.Type.EVERYONE) {
			addPowerUpToEveryone(effect);
		} else {
			addPowerUpToOthers(pickedUpByPlayer, effect);
		}
	}

	private void addPowerUpToSelf(Player player, PlayerPowerUpEffect effect) {
		player.getBody().addPowerUp(effect);
	}

	private void addPowerUpToEveryone(PlayerPowerUpEffect effect) {
		for (Player p : players) {
			p.getBody().addPowerUp(effect);
		}
	}

	private void addPowerUpToOthers(Player pickedUpByPlayer,
			PlayerPowerUpEffect effect) {
		for (Player p : players) {
			if (p != pickedUpByPlayer) {
				p.getBody().addPowerUp(effect);
			}
		}
	}
	
	/**
	 * @return true if there are players still alive
	 */
	public boolean isRoundActive() {
		return deadPlayers < players.size() - 1;
	}


	private void createPlayerBodiesAtRandomPos() {
		for (Player player : players) {

			player.setBody(BodyFactory.getBody(map));
		}
	}


	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}
	
	public Map getMap() {
		return map;
	}

	public List<PowerUpEntity> getPowerUpEntities() {
		return powerUpEntities;
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

}
