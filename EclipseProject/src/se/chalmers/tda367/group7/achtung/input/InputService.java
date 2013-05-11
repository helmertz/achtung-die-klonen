package se.chalmers.tda367.group7.achtung.input;

public interface InputService {

	public void addKeyListener(KeyInputListener listener);

	public void removeKeyListener(KeyInputListener listener);

	public void addMouseListener(MouseInputListener listener);

	public void removeMouseListener(MouseInputListener listener);

	public void update();
}
