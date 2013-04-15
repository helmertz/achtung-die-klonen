package se.chalmers.tda367.group7.achtung.rendering;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

/**
 * Very basic font renderer.
 */
public class BitMapFont {
	
	// defines the characters that should be in the texture
	public static final String CHARACTERS = 
			"0123456789abcdef" + 
			"ghijklmnopqrstuv" + 
			"wxyzåäöABCDEFGHI" + 
			"JKLMNOPQRSTUVWXY" + 
			"Zåäö., -'*!:()[]" + 
			"{}#@&_?/\\\"";

	private static final int CHARS_PER_ROW = 16;
	private static final int CHAR_WIDTH = 16;
	private static final int CHAR_HEIGHT = 26;
	
	private static final float TEX_CHAR_WIDTH = 1f/16f;
	private static final float TEX_CHAR_HEIGHT = 26f/256f;
	
	private int texID;

	/**
	 * Creates a BitMapFont from a given png file.
	 * 
	 * @param filePath the path to the png file with the characters rasterized
	 * @throws IOException
	 */
	public BitMapFont(String filePath) throws IOException {
		loadTexture(filePath);
	}

	public void render(String s, float x, float y, float scale) {
		glPushMatrix();
		glLoadIdentity();
		glTranslatef(x, y, 0);
		glScalef(scale, scale, 1);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);
		glColor3f(1, 1, 1);

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

	// based on code from LWJGL wiki page http://www.lwjgl.org/wiki/index.php?title=The_Quad_textured
	private void loadTexture(String fileName) throws IOException {		
		ByteBuffer buf = null;

		// Open the PNG file as an InputStream
		InputStream in = BitMapFont.class.getResourceAsStream("/" + fileName);
		// Link the PNG decoder to this stream
		PNGDecoder decoder = new PNGDecoder(in);

		// Get the width and height of the texture
		int textureWidth = decoder.getWidth();
		int textureHeight = decoder.getHeight();

		// Decode the PNG file in a ByteBuffer
		buf = ByteBuffer.allocateDirect(4 * decoder.getWidth()
				* decoder.getHeight());
		decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
		buf.flip();

		in.close();

		// Create a new texture object in memory and bind it
		texID = glGenTextures();
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texID);

		// All RGB bytes are aligned to each other and each component is 1 byte
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

		// Upload the texture data and generate mip maps (for scaling)
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, textureWidth, textureHeight,
				0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
		glGenerateMipmap(GL_TEXTURE_2D);

		// Setup what to do when the texture has to be scaled
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

	}
}
