package se.chalmers.tda367.group7.achtung.rendering;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

class IconLoader {

	public static ByteBuffer loadIcon(String filename) throws IOException {
		InputStream is = IconLoader.class.getResourceAsStream("/" + filename);
		if (is != null) {
			ByteBuffer buf = null;
			PNGDecoder decoder = new PNGDecoder(is);
			buf = ByteBuffer.allocateDirect(4 * decoder.getWidth()
					* decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
			buf.flip();
			is.close();
			
			return buf;
		} else {
			throw new IOException(filename + " not found");
		}
	}

}
