package se.chalmers.tda367.group7.achtung.model;

import java.util.ArrayList;
import java.util.List;

public class World {
	private List<Player> players = new ArrayList<Player>();

	public World(int playerCount) {
		this.players = new ArrayList<Player>(playerCount);
		for (int i = 0; i < playerCount; i++) {
			this.players.add(new Player());
		}
	}

	public List<Player> getPlayers() {
		return this.players;
	}

}
