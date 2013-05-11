package se.chalmers.tda367.group7.achtung.input;

public interface MouseInputListener {
	/**
	 * Returns true if it is to consume the event
	 * 
	 * @param event the mouse event
	 * @return true if event is to be consumed
	 */
	public boolean onMouseInputEvent(MouseInputEvent event);
}
