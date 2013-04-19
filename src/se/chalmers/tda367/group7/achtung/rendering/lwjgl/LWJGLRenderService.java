package se.chalmers.tda367.group7.achtung.rendering.lwjgl;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.rendering.Image;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class LWJGLRenderService implements RenderService {

	private BitMapFont bitMapFont;
	private LineRenderer lineRenderer;

	// set as screen size by default
	private float viewAreaWidth;
	private float viewAreaHeight;
	private float xPadding;
	private float yPadding;
	
	private Color backColor;

	public LWJGLRenderService() throws LWJGLException {
		init();
	}

	private void init() throws LWJGLException {
		initDisplay();
		initOpenGL();
		viewAreaWidth = Display.getWidth();
		viewAreaHeight = Display.getHeight();
		try {
			bitMapFont = new BitMapFont("courier-new.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		lineRenderer = new QuadLineRenderer();
		backColor = Color.BLACK;
	}

	private void initOpenGL() {
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHTING);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_ALPHA_TEST);
		glEnable(GL_BLEND);
		sizeRefresh();
	}

	private void initDisplay() throws LWJGLException {
		Display.setResizable(true);
		Display.setTitle("Achtung");

		// Used to determine anti-aliasing capabilities
		int maxSamples = 0;

		PixelFormat format = new PixelFormat(32, 0, 24, 8, 0);
		Pbuffer pb = new Pbuffer(800, 600, format, null);
		pb.makeCurrent();
		boolean supported = GLContext.getCapabilities().GL_ARB_multisample;
		if (supported) {
			maxSamples = glGetInteger(GL30.GL_MAX_SAMPLES);
		}
		pb.destroy();

		// Ugly way to make this work on some setups
		if (maxSamples <= 1) {
			maxSamples = 0;
		} else if (maxSamples >= 4) {
			maxSamples = 4;
		}

		Display.setDisplayMode(new DisplayMode(800, 600));

		Display.create(new PixelFormat().withSamples(maxSamples));
	}

	// TODO: make scaling proportional
	private void sizeRefresh() {
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		this.xPadding = 0;
		this.yPadding = 0;

		// for proportional scaling
		float displayRatio = (float) Display.getWidth() / Display.getHeight();
		float viewRatio = viewAreaWidth / viewAreaHeight;

		if (viewRatio < displayRatio) {
			// Padd on width
			this.xPadding = (viewAreaHeight * displayRatio - viewAreaWidth) / 2;
		} else {
			// Padd on height
			this.yPadding = (viewAreaWidth / displayRatio - viewAreaHeight) / 2;
		}

		glOrtho(-xPadding, viewAreaWidth + xPadding, viewAreaHeight + yPadding,
				-yPadding, 0, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	@Override
	public void preDraw() {
		// Some recalculations must be made when screen is resized
		if (Display.wasResized()) {
			sizeRefresh();
		}

		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
	}

	@Override
	public void postDraw() {
		bindColor(backColor);

		if (xPadding > 0) {
			drawRect(-xPadding, 0, xPadding, viewAreaHeight);
			drawRect(viewAreaWidth, 0, xPadding, viewAreaHeight);
		} else {
			drawRect(0, -yPadding, viewAreaWidth, yPadding);
			drawRect(0, viewAreaHeight, viewAreaWidth, yPadding);
		}

		Display.update();
	}

	@Override
	public void drawString(String s, float x, float y, float scale) {
		bitMapFont.render(s, x, y, scale);
	}

	@Override
	public void drawLine(float x1, float y1, float x2, float y2, float width,
			Color color) {
		bindColor(color);
		lineRenderer.drawLine(x1, y1, x2, y2, width);
	}

	private void bindColor(Color color) {
		glColor4f(color.getRed(), color.getGreen(), color.getBlue(),
				color.getAlpha());
	}

	@Override
	public void setViewAreaSize(float width, float height) {
		this.viewAreaWidth = width;
		this.viewAreaHeight = height;
		sizeRefresh();
	}

	@Override
	public void drawFilledRect(float x, float y, float width, float height,
			Color color) {
		bindColor(color);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		drawRect(x, y, width, height);
	}

	@Override
	public void drawLinedRect(float x, float y, float width, float height,
			float lineWidth, Color color) {
		bindColor(color);
		drawRect(x, y, width, lineWidth);
		drawRect(x + width - lineWidth, y, lineWidth, height);
		drawRect(x, y + height - lineWidth, width, lineWidth);
		drawRect(x, y, lineWidth, height);
	}

	private void drawRect(float x, float y, float width, float height) {
		glBegin(GL_QUADS);
		glVertex2f(x, y);
		glVertex2f(x + width, y);
		glVertex2f(x + width, y + height);
		glVertex2f(x, y + height);
		glEnd();
	}

	@Override
	public void setBackgroundColor(Color color) {
		this.backColor = color;
		glClearColor(color.getRed(), color.getGreen(), color.getBlue(),
				color.getAlpha());
	}

	@Override
	public boolean isCloseRequested() {
		return Display.isCloseRequested();
	}

	@Override
	public float getViewAreaWidth() {
		return viewAreaWidth;
	}

	@Override
	public float getViewAreaHeight() {
		return viewAreaHeight;
	}

	@Override
	public void drawStringCentered(String string, float x, float y, float scale) {
		bitMapFont.renderCentered(string, x, y, scale);
	}

	@Override
	public boolean isActive() {
		return Display.isActive();
	}

	@Override
	public Image getImage(String path) throws IOException {
		return new LWJGLImage(path);
	}

	// Draws a circle, with the center coordinates x and y, as a polygon with
	// the number of corners defined by edgeQuality
	@Override
	public void drawCircleCentered(float x, float y, float radius, int edgeQuality,
			Color color) {
		// Lifted from http://www.java-gaming.org/index.php?topic=25245.msg217089#msg217089
		bindColor(color);
		glPushMatrix();
		glTranslatef(x, y, 0);
		glScalef(radius, radius, 1);

		glBegin(GL_TRIANGLE_FAN);
		glVertex2f(0, 0);
		
		// TODO: Determine how round circle should be, parameter?
		for(int i = 0; i <= edgeQuality; i++){ // edgeQuality decides how round the circle looks.
		    double angle = Math.PI * 2 * i / edgeQuality;
		    glVertex2f((float)Math.cos(angle), (float)Math.sin(angle));
		}
		glEnd();
		glPopMatrix();
	}

}
