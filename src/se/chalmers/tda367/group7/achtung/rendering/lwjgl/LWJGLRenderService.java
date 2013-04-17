package se.chalmers.tda367.group7.achtung.rendering.lwjgl;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import se.chalmers.tda367.group7.achtung.rendering.Color;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class LWJGLRenderService implements RenderService {

	private BitMapFont bitMapFont;
	private LineRenderer lineRenderer;

	// set as screen size by default
	private float viewAreaWidth;
	private float viewAreaHeight;

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
	}

	private void initOpenGL() {
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHTING);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_ALPHA_TEST);
		glEnable(GL_BLEND);
		sizeRefrash();
	}

	private void initDisplay() throws LWJGLException {
		Display.setResizable(true);
		Display.setTitle("Achtung");
		Display.setDisplayMode(new DisplayMode(800, 600));

		// Change 4 to 0 if this causes an exception
		PixelFormat pf = new PixelFormat().withSamples(0);

		Display.create(pf);
	}

	// TODO: make scaling proportional
	private void sizeRefrash() {
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		float xPadding = 0;
		float yPadding = 0;

		// for proportional scaling
		float displayRatio = (float) Display.getWidth() / Display.getHeight();
		float viewRatio = viewAreaWidth / viewAreaHeight;

		if (viewRatio < displayRatio) {
			// Padd on width
			xPadding = (viewAreaHeight * displayRatio - viewAreaWidth) / 2;
		} else {
			// Padd on height
			yPadding = (viewAreaWidth / displayRatio - viewAreaHeight) / 2;
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
			sizeRefrash();
		}

		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
	}

	@Override
	public void postDraw() {
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
		sizeRefrash();
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

}
