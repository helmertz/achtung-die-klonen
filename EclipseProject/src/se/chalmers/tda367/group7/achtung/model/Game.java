package se.chalmers.tda367.group7.achtung.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing an entire game session, with a number of rounds.
 */
public class Game {

	private final List<Round> rounds;
	private final List<Player> players;
	private Round currentRound;
	private Player gameWinner;
	private int goalPoints;

	private final PropertyChangeSupport pcs;

	public Game() {
		this.rounds = new ArrayList<>();
		this.players = new ArrayList<>();
		this.pcs = new PropertyChangeSupport(this);
	}

	public Player createNewPlayer(String name, Color color) {
		Player player = new Player(name, color);
		this.players.add(player);
		return player;
	}

	public void update() {
		this.currentRound.update();
		sortPlayersByPoints();
		winnerCheck();
	}

	private void sortPlayersByPoints() {
		Collections.sort(this.players, new SortByPointsDescending());
	}

	public void newRound() {
		this.currentRound = new Round(new Map(1337, 1337), this.players);
		this.rounds.add(this.currentRound);
		this.pcs.firePropertyChange("NewRound", false, true);
		this.goalPoints = 10 * (this.players.size() - 1);

		resetPlayerRoundScores();
	}

	private void resetPlayerRoundScores() {
		for (Player p : this.players) {
			p.resetRoundScore();
		}
	}
	
	public void addPlayer(Player p) {
		this.players.add(p);
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	/**
	 * @return true if a player has won.
	 */
	public boolean isOver() {
		if (this.currentRound != null) {
			return !this.currentRound.isRoundActive()
					&& this.gameWinner != null;
		} else {
			return false;
		}
	}

	/**
	 * Looks through the players to find a winner, and sets the winner variable
	 * if found.
	 */
	private void winnerCheck() {

		// Finds the highest score and sets a player with that score
		int highestScore = 0;
		Player possibleWinner = null;
		for (Player player : this.players) {
			if (player.getPoints() > highestScore) {
				highestScore = player.getPoints();
				possibleWinner = player;
			}
		}

		// No need to check further if below goal
		if (highestScore < this.goalPoints) {
			return;
		}

		// Checks again and if another player has a score less than two points
		// below leader, then the leader isn't yet the winner
		for (Player player : this.players) {
			if (player != possibleWinner
					&& highestScore - player.getPoints() < 2) {
				// Sets the goal score to one higher than highestScore
				// so that you need two points more than second highest to win
				this.goalPoints = highestScore + 1;
				return;
			}
		}
		this.gameWinner = possibleWinner;
	}

	/**
	 * Returns the number of points required to win the game.
	 */
	public int getGoalPoints() {
		return this.goalPoints;
	}
	
	public void setGoalPoints(int goalPoints) {
		this.goalPoints = goalPoints;
	}

	public Round getCurrentRound() {
		return this.currentRound;
	}

	public Player getGameWinner() {
		return this.gameWinner;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
}
