package se.chalmers.tda367.group7.achtung.input;

public interface InputService {

	public void addListener(InputListener listener);

	public void removeListener(InputListener listener);

	public void update();
}
