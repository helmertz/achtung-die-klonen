package se.chalmers.tda367.group7.achtung.rendering;

import java.io.IOException;

import se.chalmers.tda367.group7.achtung.model.Color;

public interface RenderService {
	// Called before drawing for a new frame
	void preDraw();

	// Called after drawing for a frame is finished
	void postDraw();

	// Sets the color the screen is filled with after it has been cleared
	void setBackgroundColor(Color color);

	// Draws a line between two points, with specified width and color
	void drawLine(float x1, float y1, float x2, float y2, float width,
			Color color);

	// Sets the bounds of the area on the screen to render on. (0,0) would be
	// the top right corner of the view area and (width,height) the lower right
	// corner.
	void setViewAreaSize(float width, float height);

	// Draws a filled rectangle where x and y is the location of the top left
	// corner
	void drawFilledRect(float x, float y, float width, float height, Color color);

	// Draws the outline of the rectangle, with specified width
	void drawLinedRect(float x, float y, float width, float height,
			float lineWidth, Color color);

	void drawCircleCentered(float x, float y, float radius, int edgeQuality,
			Color color);

	// Draws text on the screen where x and y specifies the top left corner of
	// the first letter
	void drawString(String s, float x, float y, float scale);

	// Should perhaps be removed later on
	boolean isCloseRequested();

	float getViewAreaHeight();

	float getViewAreaWidth();

	void drawStringCentered(String string, float x, float y, float scale);

	void drawStringCentered(String string, float x, float y, float scale,
			Color color);

	boolean isActive();

	// From a path to a PNG file (only supported currently) returns an object
	// that can be used to render the image. Image width and height should be a
	// factor of 2.
	Image getImage(String path) throws IOException;

	void drawString(String s, float x, float y, float scale, Color color);

	void drawCircleOutlinePercent(float x, float y, float radius,
			float percentDur, float outlineWidth, Color color);

	// Toggle whether or not to draw in the fixed view area. If true, what's
	// drawn between 0 and max width/height will be visible and scaled
	// appropriately.
	void setScaled(boolean scaled);

	void drawFourCornered(float x, float y, float x2, float y2, float x3,
			float y3, float x4, float y4, Color color);

	void drawFourCornered(float x1, float y1, float x2, float y2, float x3,
			float y3, float x4, float y4);

	boolean isFullscreen();

	void setFullscreen(boolean fullscreen);
}
