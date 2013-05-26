package se.chalmers.tda367.group7.achtung.view;

import java.util.List;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodyPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.BodySegment;
import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.model.Position;
import se.chalmers.tda367.group7.achtung.model.PowerUp;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class PlayerView implements View {

	private static final float TIMER_LINEWIDTH = 2;
	private final Player player;
	private final Color[] powerUpTimerColors;
	private final Color invertedColor;

	public PlayerView(Player player) {
		this.player = player;
		this.invertedColor = this.player.getColor().getOpposite();
		// Gets four lighter versions of the player's colors, used for drawing
		// the power-up time indicators.
		Color c1 = player.getColor().getLighter();
		Color c2 = c1.getLighter();
		Color c3 = c2.getLighter();
		Color c4 = c3.getLighter();
		this.powerUpTimerColors = new Color[] { c1, c2, c3, c4 };
	}

	@Override
	public void render(RenderService renderService, float interpolation) {
		Body body = this.player.getBody();

		for (BodySegment b : body.getBodySegments()) {
			drawSegment(renderService, b);
		}

		float headX = body.getPosition().getX();
		float headY = body.getPosition().getY();

		// Predict next position to get smoother rendering independent of
		// tick-rate
		if (!body.isDead()) {

			float interpolationX = (float) (interpolation * body.getSpeed() * Math
					.cos(Math.toRadians(body.getRotationAngleDeg())));
			float interpolationY = (float) (interpolation * body.getSpeed() * Math
					.sin(Math.toRadians(body.getRotationAngleDeg())));

			headX += interpolationX;
			headY += interpolationY;
		}

		// Draw line from end of last segment to head position
		// Only if there isn't a hole currently generating
		if (!body.getBodySegments().isEmpty() && !body.isGeneratingHole()) {
			Position endPos = body.getLastPosition();
			renderService.drawLine(endPos.getX(), endPos.getY(), headX, headY,
					body.getWidth(), this.player.getColor());

		}
		// Inverts color of head if controls are inverted
		Color headColor = this.player.getColor();

		if (this.player.getBody().hasInvertedControls()) {
			headColor = this.invertedColor;
		}

		renderService.drawCircleCentered(headX, headY, body.getWidth() / 2, 18,
				headColor);

		if (!this.player.getBody().isDead()) {
			this.drawPowerUpTimer(renderService, interpolation, headX, headY);
		}
	}

	private void drawSegment(RenderService renderService, BodySegment b) {
		Position[] corners = b.getCorners();
		if (corners.length == 4) {
			drawSegmentCorners(renderService, corners);
		} else {
			throw new IllegalArgumentException(
					"Segment doesn't have four corners");
		}
	}

	private void drawSegmentCorners(RenderService renderService,
			Position[] corners) {
		renderService.drawFourCornered(corners[0].getX(), corners[0].getY(),
				corners[1].getX(), corners[1].getY(), corners[2].getX(),
				corners[2].getY(), corners[3].getX(), corners[3].getY(),
				this.player.getColor());
	}

	private void drawPowerUpTimer(RenderService renderService,
			float interpolation, float headX, float headY) {
		Body body = this.player.getBody();
		List<PowerUp<BodyPowerUpEffect>> powerUps = body.getPowerUps();

		float radius = (float) Math.pow(body.getHead().getDiameter(), 1.05) + 8;

		if (powerUps.size() > 0) {
			for (PowerUp<BodyPowerUpEffect> powerUp : powerUps) {
				int powerUpIndex = powerUps.indexOf(powerUp);
				radius += powerUpIndex + (TIMER_LINEWIDTH * 4);

				Color color = getCorrectTimerColor(powerUps.indexOf(powerUp));
				int dur = powerUp.getEffect().getDuration();

				float percentDur = 100 * ((powerUp.getTimeLeft() - interpolation) / dur);
				renderService.drawCircleOutlinePercent(headX, headY, radius,
						percentDur, TIMER_LINEWIDTH, color);
			}
		}
	}

	private Color getCorrectTimerColor(int index) {
		int length = this.powerUpTimerColors.length;
		return this.powerUpTimerColors[index % length];
	}

}
