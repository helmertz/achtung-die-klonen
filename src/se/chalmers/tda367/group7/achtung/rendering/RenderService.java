package se.chalmers.tda367.group7.achtung.rendering;


public interface RenderService {
	// Called before drawing for a new frame
	public void preDraw();

	// Called after drawing for a frame is finished
	public void postDraw();

	// Sets the color the screen is filled with after it has been cleared
	public void setBackgroundColor(Color color);

	// Draws a line between two points, with specified width and color
	public void drawLine(float x1, float y1, float x2, float y2, float width,
			Color color);

	// Sets the bounds of the area on the screen to render on. (0,0) would be
	// the top right corner of the view area and (width,height) the lower right
	// corner.
	public void setViewAreaSize(float width, float height);

	// Draws a filled rectangle where x and y is the location of the top left
	// corner
	public void drawFilledRect(float x, float y, float width, float height,
			Color color);

	// Draws the outline of the rectangle, with specified width
	public void drawLinedRect(float x, float y, float width, float height,
			float lineWidth, Color color);

	// Draws text on the screen where x and y specifies the top left corner of
	// the first letter
	public void drawString(String s, float x, float y, float scale);

	// Should perhaps be removed later on
	public boolean isCloseRequested();
}
