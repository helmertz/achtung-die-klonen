package se.chalmers.tda367.group7.achtung.rendering;

import java.io.IOException;

import se.chalmers.tda367.group7.achtung.model.Color;
import static org.lwjgl.opengl.GL11.*;

/**
 * Very basic font renderer.
 */
class BitMapFont {

	// defines the characters that should be in the texture
	public static final String CHARACTERS = "0123456789abcdef"
			+ "ghijklmnopqrstuv" + "wxyz\u00E5\u00E4\u00F6ABCDEFGHI"
			+ "JKLMNOPQRSTUVWXY" + "Z\u00C5\u00C4\u00D6., -'*!:()[]"
			+ "{}#@&_?/\\\"+<>$£€~^¤|%";

	private static final int CHARS_PER_ROW = 16;
	private static final int CHAR_WIDTH = 16;
	private static final int CHAR_HEIGHT = 26;

	private static final float TEX_CHAR_WIDTH = 1f / 16f;
	private static final float TEX_CHAR_HEIGHT = 26f / 256f;

	private final int texID;

	/**
	 * Creates a BitMapFont from a given png file.
	 * 
	 * @param filePath
	 *            the path to the png file with the characters rasterized
	 * @throws IOException
	 */
	public BitMapFont(String filePath) throws IOException {
		this.texID = Utils.loadTexture(filePath);
	}

	public float getWidth(String string, float scale) {
		return string.length() * CHAR_WIDTH * scale;
	}

	public float getHeight(String string, float scale) {
		return (1 + string.split("/n").length) * CHAR_HEIGHT * scale;
	}

	public void render(String s, float x, float y, float scale) {
		render(s, x, y, scale, Color.WHITE);
	}

	public void renderCentered(String string, float x, float y, float scale) {
		render(string, x - getWidth(string, scale) / 2,
				y - getHeight(string, scale) / 2, scale);
	}

	public void renderCentered(String string, float x, float y, float scale,
			Color color) {
		render(string, x - getWidth(string, scale) / 2,
				y - getHeight(string, scale) / 2, scale, color);
	}

	public void render(String s, float x, float y, float scale, Color color) {
		glPushMatrix();
		glTranslatef(x, y, 0);
		glScalef(scale, scale, 1);

		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, this.texID);
		glColor4f(color.getRed(), color.getGreen(), color.getBlue(),
				color.getAlpha());

		int xadd = 0;
		int yadd = 0;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			int charIndex = CHARACTERS.indexOf(c);

			// if the character isn't found, replace with "?"
			if (charIndex < 0) {
				charIndex = CHARACTERS.indexOf('?');
			}

			// Checks for newline made with /n
			if (i < s.length() - 1 && c == '/' && s.charAt(i + 1) == 'n') {
				i++;
				xadd = 0;
				yadd += CHAR_HEIGHT;
				continue;
			}

			int row = charIndex / CHARS_PER_ROW;
			int col = charIndex % CHARS_PER_ROW;

			float texX = col * TEX_CHAR_WIDTH;
			float texY = row * TEX_CHAR_HEIGHT;
			glBegin(GL_QUADS);
			glTexCoord2f(texX, texY);
			glVertex2f(xadd, yadd);
			glTexCoord2f(texX + TEX_CHAR_WIDTH, texY);
			glVertex2f(xadd + CHAR_WIDTH, yadd);
			glTexCoord2f(texX + TEX_CHAR_WIDTH, texY + TEX_CHAR_HEIGHT);
			glVertex2f(xadd + CHAR_WIDTH, yadd + CHAR_HEIGHT);
			glTexCoord2f(texX, texY + TEX_CHAR_HEIGHT);
			glVertex2f(xadd, yadd + CHAR_HEIGHT);
			glEnd();

			xadd += CHAR_WIDTH;
		}
		glDisable(GL_TEXTURE_2D);

		glPopMatrix();
	}
}
