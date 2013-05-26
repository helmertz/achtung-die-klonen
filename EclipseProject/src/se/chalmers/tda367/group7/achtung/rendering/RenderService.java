package se.chalmers.tda367.group7.achtung.rendering;

import java.io.IOException;

import se.chalmers.tda367.group7.achtung.model.Color;

public interface RenderService {
	/**
	 * Should called be before a new frame is drawn.
	 */
	void preDraw();

	/**
	 * Should called be after all drawing methods has been done for a frame.
	 */
	void postDraw();

	/**
	 * Sets the color the screen is filled when it's cleared.
	 * 
	 * @param color
	 *            the background color
	 */
	void setBackgroundColor(Color color);

	/**
	 * Draws a line between two points.
	 * 
	 * @param x1
	 *            the x-coordinate of the first end point
	 * @param y1
	 *            the y-coordinate of the first end point
	 * @param x2
	 *            the x-coordinate of the second end point
	 * @param y2
	 *            the y-coordinate of the second end point
	 * @param width
	 *            the width of the line
	 * @param color
	 *            the color of the line
	 */
	void drawLine(float x1, float y1, float x2, float y2, float width,
			Color color);

	/**
	 * Sets the bounds of the area on the screen to render on.
	 * 
	 * This can be used to simplify drawing if there is a "world" of a certain
	 * size, things inside could then be drawn at the x- and y-coordinates in
	 * the world, without considering scaling.
	 * 
	 * (0,0) would be the top left corner of the view area and (width,height)
	 * the lower right corner.
	 * 
	 * @param width
	 *            the width of the view area
	 * @param height
	 *            the height of the view area
	 */
	void setViewAreaSize(float width, float height);

	/**
	 * Draws a filled rectangle.
	 * 
	 * @param x
	 *            the x-coordinate of the top-left corner
	 * @param y
	 *            the y-coordinate of the top-left corner
	 * @param width
	 *            the width of the rectangle
	 * @param height
	 *            the height of the rectangle
	 * @param color
	 *            the color of the rectangle
	 */
	void drawFilledRect(float x, float y, float width, float height, Color color);

	/**
	 * Draws an outlined rectangle.
	 * 
	 * @param x
	 *            the x-coordinate of the top-left corner
	 * @param y
	 *            the y-coordinate of the top-left corner
	 * @param width
	 *            the width of the rectangle
	 * @param height
	 *            the height of the rectangle
	 * @param color
	 *            the color of the rectangle
	 * @param lineWidth
	 *            the width of the line
	 */
	void drawLinedRect(float x, float y, float width, float height,
			float lineWidth, Color color);

	/**
	 * Draws a circle.
	 * 
	 * @param x
	 *            the center x-coordinate
	 * @param y
	 *            the center y-coordinate
	 * @param radius
	 *            the radius
	 * @param edgeQuality
	 *            the number of triangles used to draw it
	 * @param color
	 *            the color
	 */
	void drawCircleCentered(float x, float y, float radius, int edgeQuality,
			Color color);

	/**
	 * Draws text on the screen.
	 * 
	 * @param string
	 *            the string to be drawn
	 * @param x
	 *            the x-coordinate of the top-left corner of the first letter
	 * @param y
	 *            the y-coordinate of the top-left corner of the first letter
	 * @param scale
	 *            the scale of the string
	 */
	void drawString(String string, float x, float y, float scale);

	void drawString(String string, float x, float y, float scale, Color color);

	void drawStringCentered(String string, float x, float y, float scale);

	void drawStringCentered(String string, float x, float y, float scale,
			Color color);

	/**
	 * Checks if user has requested closing the window.
	 * 
	 * @return true if closing has been requested through the window
	 */
	boolean isCloseRequested();

	float getViewAreaHeight();

	float getViewAreaWidth();

	/**
	 * Checks if the window is in the foreground.
	 * 
	 * @return true if the window is in the foreground
	 */
	boolean isActive();

	/**
	 * Gets an image object used for rendering it.
	 * 
	 * From a path to a PNG file (only supported currently) returns an object
	 * that can be used to render the image. Image width and height should be a
	 * factor of 2.
	 * 
	 * @param path
	 *            the path to the image file
	 * @return a representation of an image
	 * @throws IOException
	 *             if there's an error reading or the file can't be found
	 */
	Image getImage(String path) throws IOException;

	/**
	 * Draws part of the outline of a circle, could be considered an arc.
	 * 
	 * The arc is drawn clockwise from the top.
	 * 
	 * @param x
	 *            the center x-coordinate
	 * @param y
	 *            the center y-coordinate
	 * @param radius
	 *            the radius
	 * @param percent
	 *            the percentage (0-100) of the outline that's shown
	 * @param outlineWidth
	 *            the width of the outline
	 * @param color
	 *            the color
	 */
	void drawCircleOutlinePercent(float x, float y, float radius,
			float percent, float outlineWidth, Color color);

	/**
	 * Set whether or not what's drawn is adjusted to the view area.
	 * 
	 * Toggle whether or not to draw in the fixed view area. If true, what's
	 * drawn between 0 and max width/height will be visible and scaled
	 * appropriately.
	 * 
	 * @param scaled
	 *            whether scaling is to be enabled
	 */
	void setScaled(boolean scaled);

	/**
	 * Draws any four-cornered shape.
	 */
	void drawFourCornered(float x, float y, float x2, float y2, float x3,
			float y3, float x4, float y4, Color color);

	void drawFourCornered(float x1, float y1, float x2, float y2, float x3,
			float y3, float x4, float y4);

	boolean isFullscreen();

	/**
	 * Set whether the application should be shown in fullscreen or not.
	 * 
	 * The resolution for fullscreen will be the same as the current desktop
	 * resolution.
	 * 
	 * @param fullscreen
	 *            if fullscreen should be enabled
	 */
	void setFullscreen(boolean fullscreen);

	/**
	 * Gets the current desktop refresh rate.
	 * 
	 * @return the current desktop refresh rate
	 */
	double getRefreshRate();
}
