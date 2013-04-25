package se.chalmers.tda367.group7.achtung.sound;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound implements PropertyChangeListener{

	private Audio powerupSelf;
	private Audio powerupEveryoneElse;
	private Audio powerupEveryone;

	public void start() {
		initGL();
		init();
		while (true) {
			update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

			Display.update();
			Display.sync(100);

			if (Display.isCloseRequested()) {
				Display.destroy();
				AL.destroy();
				System.exit(0);
			}
		}

	}

	public void initGL() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClearDepth(1);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0, 0, 800, 600);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 600, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

	}

	public void init() {

		try {

			powerupSelf = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("res/sounds/powerup4.wav"));

			powerupEveryoneElse = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("res/sounds/powerup2.wav"));

			powerupEveryone = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("res/sounds/powerup3.wav"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_1) {
					powerupSelf.playAsSoundEffect(1.0f, 1.0f, false);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_2) {
					powerupEveryone.playAsSoundEffect(1.0f, 1.0f, false);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_3) {
					powerupEveryoneElse.playAsSoundEffect(1.0f, 1.0f, false);
				}
			}
		}
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		
	}
	 
	public static void main(String[] argv) {
		Sound test = new Sound();
		test.start();
	}
}