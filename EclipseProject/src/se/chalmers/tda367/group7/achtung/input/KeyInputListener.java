package se.chalmers.tda367.group7.achtung.input;

public interface KeyInputListener {
	/**
	 * Returns true if it is to consume the event
	 * 
	 * @param event
	 *            the keyboard event
	 * @return true if event is to be consumed
	 */
	public boolean onKeyInputEvent(KeyInputEvent event);
}
