package se.chalmers.tda367.group7.achtung.input;

public interface InputService {

	void addKeyListener(KeyInputListener listener);

	void removeKeyListener(KeyInputListener listener);

	void addMouseListener(MouseInputListener listener);

	void removeMouseListener(MouseInputListener listener);

	void update();
}
