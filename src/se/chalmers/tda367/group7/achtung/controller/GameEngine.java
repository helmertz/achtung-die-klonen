package se.chalmers.tda367.group7.achtung.controller;

import se.chalmers.tda367.group7.achtung.model.World;
import se.chalmers.tda367.group7.achtung.view.Renderer;
import se.chalmers.tda367.group7.achtung.view.WorldRenderer;

public class GameEngine {

	private World world;
	private Renderer renderer;
	private WorldController controller;

	public GameEngine(int playerCount) {
		this.world = new World(playerCount);
		this.controller = new WorldController(this.world);
		this.renderer = new WorldRenderer(this.world);
	}

	public void update() {
		this.controller.update();
	}

	public void render() {
		this.renderer.render();
	}
}
