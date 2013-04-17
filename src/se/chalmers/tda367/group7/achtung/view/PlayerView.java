package se.chalmers.tda367.group7.achtung.view;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodySegment;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.model.Position;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class PlayerView implements View {

	private Player player;
	
	public PlayerView(Player player) {
		this.player = player;
	}
	
	@Override
	public void render(RenderService renderService, float interpolation) {
		
		Body body = player.getBody();

		renderService.drawStringCentered(player.getName(), body.getPosition().getX(), body.getPosition().getY(), 1f);
		
		for (BodySegment b : body.getBodySegments()) {

			renderService.drawLine(b.getStart().getX(), b.getStart().getY(), b
					.getEnd().getX(), b.getEnd().getY(), b.getWidth(), player
					.getColor());			
		}

		float headX = body.getPosition().getX();
		float headY = body.getPosition().getY();
		
		// Predict next position to get smoother rendering independent of
		// tick-rate
		if (!body.isDead()) {

			float interpolationX = (float) (interpolation * body.getSpeed() * Math
					.cos(body.getRotationAngleDeg()));
			float interpolationY = (float) (interpolation * body.getSpeed() * Math
					.sin(body.getRotationAngleDeg()));

			headX += interpolationX;
			headY += interpolationY;
		}

		// Draw line from end of last segment to head position
		if (!body.getBodySegments().isEmpty()) {
			BodySegment lastSegment = body.getBodySegments().get(
					body.getBodySegments().size() - 1);
			Position endPos = lastSegment.getEnd();
			renderService.drawLine(endPos.getX(), endPos.getY(), headX, headY,
					body.getWidth(), player.getColor());
		}
	}

}
