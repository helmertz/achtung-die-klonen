package se.chalmers.tda367.group7.achtung.rendering.lwjgl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import se.chalmers.tda367.group7.achtung.model.Color;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Utils {
	
	// based on code from LWJGL wiki page
	// http://www.lwjgl.org/wiki/index.php?title=The_Quad_textured
	static int loadTexture(String fileName) throws IOException {
		ByteBuffer buf = null;

		glEnable(GL_TEXTURE_2D);
		
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
		int texID = glGenTextures();
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texID);

		// All RGB bytes are aligned to each other and each component is 1 byte
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

		// Upload the texture data and generate mip maps (for scaling)
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, textureWidth, textureHeight, 0,
				GL_RGBA, GL_UNSIGNED_BYTE, buf);

		// Setup what to do when the texture has to be scaled
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		
		glDisable(GL_TEXTURE_2D);

		return texID;
	}

	// draws the texture, where x and y is the location of the top right corner
	static void drawTexture(int texID, float x, float y, float width,
			float height) {
		drawTexture(texID, x, y, width, height, Color.WHITE);
	}
	
	// draws the texture, where x and y is the location of the top right corner
	static void drawTexture(int texID, float x, float y, float width,
			float height, Color color) {
		glEnable(GL_TEXTURE_2D);

		glBindTexture(GL_TEXTURE_2D, texID);

		glColor4f(color.getRed(), color.getGreen(), color.getBlue(),
				color.getAlpha());

		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(x, y);
		glTexCoord2f(0, 1);
		glVertex2f(x, y + height);
		glTexCoord2f(1, 1);
		glVertex2f(x + width, y + height);
		glTexCoord2f(1, 0);
		glVertex2f(x + width, y);
		glEnd();

		glDisable(GL_TEXTURE_2D);
	}
}
