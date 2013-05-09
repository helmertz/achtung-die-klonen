package se.chalmers.tda367.group7.achtung.view;

import java.util.List;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodySegment;
import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.model.Position;
import se.chalmers.tda367.group7.achtung.model.PowerUp;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class PlayerView implements View {

	private final Player player;
	private final boolean drawHitBox = false; // used for debug
	private final Color[] powerUpTimerColors;

	public PlayerView(Player player) {
		this.player = player;

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

			renderService.drawLine(b.getStart().getX(), b.getStart().getY(), b
					.getEnd().getX(), b.getEnd().getY(), b.getWidth(),
					this.player.getColor());

			if (this.drawHitBox) {
				int[] xpoints = b.getHitBox().xpoints;
				int[] ypoints = b.getHitBox().ypoints;

				if (xpoints.length == 4 && ypoints.length == 4) {
					renderService.drawLine(xpoints[0], ypoints[0], xpoints[1],
							ypoints[1], 1, Color.WHITE);
					renderService.drawLine(xpoints[1], ypoints[1], xpoints[2],
							ypoints[2], 1, Color.WHITE);
					renderService.drawLine(xpoints[2], ypoints[2], xpoints[3],
							ypoints[3], 1, Color.WHITE);
					renderService.drawLine(xpoints[3], ypoints[3], xpoints[0],
							ypoints[0], 1, Color.WHITE);
				}
			}

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
		renderService.drawCircleCentered(headX, headY, body.getWidth() / 2, 10,
				this.player.getColor());

		if (!this.player.getBody().isDead()) {
			this.drawPowerUpTimer(renderService, interpolation, headX, headY);
		}
	}

	private void drawPowerUpTimer(RenderService renderService,
			float interpolation, float headX, float headY) {
		Body body = this.player.getBody();
		List<PowerUp> powerUps = body.getPowerUps();

		float lineWidth = 2;
		float radius = (float) Math.pow(body.getHead().getDiameter(), 1.05) + 8;

		if (powerUps.size() > 0) {
			for (PowerUp powerUp : powerUps) {
				int powerUpIndex = powerUps.indexOf(powerUp);
				radius += powerUpIndex + (lineWidth * 4);

				Color color = getCorrectTimerColor(powerUps.indexOf(powerUp));
				int dur = powerUp.getEffect().getDuration();

				float percentDur = 100 * ((powerUp.getTimeLeft() - interpolation) / dur);
				renderService.drawCircleOutlinePercent(headX, headY, radius,
						percentDur, lineWidth, color);
			}
		}
	}

	private Color getCorrectTimerColor(int index) {
		int length = this.powerUpTimerColors.length;
		return this.powerUpTimerColors[index % length];
	}

}
