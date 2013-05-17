package se.chalmers.tda367.group7.achtung;

import java.io.File;

import org.lwjgl.LWJGLUtil;

import se.chalmers.tda367.group7.achtung.controller.MainController;

public class Main {

	public static void main(String[] args) {
		// Sets up native bindings, location depends on platform
		String nativePath = new File(new File(System.getProperty("user.dir"),
				"native"), LWJGLUtil.getPlatformName()).getAbsolutePath();
		System.setProperty("org.lwjgl.librarypath", nativePath);

		// Launches the game
		MainController game = new MainController();
		game.run();
	}
}
