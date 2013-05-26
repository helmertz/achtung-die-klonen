package se.chalmers.tda367.group7.achtung.rendering;

import se.chalmers.tda367.group7.achtung.model.Color;

public interface Image {
	/**
	 * Draws the image on a specified location.
	 * 
	 * @param x
	 *            the x-coordinate of the top-left corner of the rectangle the
	 *            image is drawn in
	 * @param y
	 *            the y-coordinate of the top-left corner of the rectangle the
	 *            image is drawn in
	 * @param width
	 *            the width of the rectangle the image is drawn in
	 * @param height
	 *            the height of the rectangle the image is drawn in
	 */
	public void drawImage(float x, float y, float width, float height);

	/**
	 * Draws the image on a specified location, with a color overlay.
	 * 
	 * @param x
	 *            the x-coordinate of the top-left corner of the rectangle the
	 *            image is drawn in
	 * @param y
	 *            the y-coordinate of the top-left corner of the rectangle the
	 *            image is drawn in
	 * @param width
	 *            the width of the rectangle the image is drawn in
	 * @param height
	 *            the height of the rectangle the image is drawn in
	 * @param color
	 *            the color overlay on the image
	 */
	public void drawImage(float x, float y, float width, float height,
			Color color);

	/**
	 * Draws the image on a specified location.
	 * 
	 * @param x
	 *            the center x-coordinate of the rectangle the image is drawn in
	 * @param y
	 *            the center y-coordinate of the rectangle the image is drawn in
	 * @param width
	 *            the width of the rectangle the image is drawn in
	 * @param height
	 *            the height of the rectangle the image is drawn in
	 */
	public void drawImageCentered(float x, float y, float width, float height);

	/**
	 * Draws the image on a specified location, with a color overlay.
	 * 
	 * @param x
	 *            the center x-coordinate of the rectangle the image is drawn in
	 * @param y
	 *            the center y-coordinate of the rectangle the image is drawn in
	 * @param width
	 *            the width of the rectangle the image is drawn in
	 * @param height
	 *            the height of the rectangle the image is drawn in
	 * @param color
	 *            the color overlay on the image
	 */
	public void drawImageCentered(float x, float y, float width, float height,
			Color color);
}
