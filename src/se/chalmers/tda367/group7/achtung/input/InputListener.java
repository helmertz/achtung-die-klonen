package se.chalmers.tda367.group7.achtung.input;

public interface InputListener {
	/**
	 *  Returns true if it is to consume the event
	 * @param event
	 * @return
	 */
	public boolean onInputEvent(InputEvent event);
}
