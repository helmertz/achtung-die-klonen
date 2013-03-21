package se.chalmers.tda367.group7.achtung.model;

import java.util.LinkedList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector2f;

public class Player {
	private String name;
	
	private LinkedList<PlayerSegment> body = new LinkedList<PlayerSegment>();
	private Vector2f pos;
	private Vector2f lastPos;
	
	private ReadableColor color;

	private float rotStep = 0.1f;
	private float rotAngle;
	private float speed = 6f;

	private int leftKeyCode;
	private int rightKeyCode;
	
	private int updateCounter;
	private int updateRate = 1;

	private boolean nextBlank;
	private float nextBlankChance = 0.02f;
	
	public Player() {
		this.color = ReadableColor.WHITE;
		this.pos = new Vector2f((float) ((Math.random() - 0.5) * 100),
				(float) ((Math.random() - 0.5) * 100));
		this.body.add(new PlayerSegment(this.pos, this.pos, 10));
		this.lastPos = this.pos;
		this.leftKeyCode = Keyboard.KEY_LEFT;
		this.rightKeyCode = Keyboard.KEY_RIGHT;
	}

	public void update() {
		this.updateCounter++;
		
		float newX = this.pos.x
				+ (float) (this.speed * Math.cos(this.rotAngle));
		float newY = this.pos.y
				+ (float) (this.speed * Math.sin(this.rotAngle));
		this.pos = new Vector2f(newX, newY);
		
		if (this.updateCounter > this.updateRate) {
			this.updateCounter = 0;
			if (!nextBlank) {
				this.body.add(new PlayerSegment(this.lastPos, this.pos, 10));
				nextBlankChance = 0.02f;
			} else {
				nextBlankChance = 0.5f;
			}
			this.lastPos = this.pos;
			nextBlank = nextBlankChance > Math.random();
		}
	}

	public void turnLeft() {
		this.rotAngle -= this.rotStep;
	}

	public void turnRight() {
		this.rotAngle += this.rotStep;
	}

	public int getLeftKeyCode() {
		return this.leftKeyCode;
	}

	public void setLeftKeyCode(int leftKeyCode) {
		this.leftKeyCode = leftKeyCode;
	}

	public int getRightKeyCode() {
		return this.rightKeyCode;
	}

	public void setRightKeyCode(int rightKeyCode) {
		this.rightKeyCode = rightKeyCode;
	}

	public float getRotAngle() {
		return this.rotAngle;
	}

	public void setRotAngle(float rotAngle) {
		this.rotAngle = rotAngle;
	}

	public ReadableColor getColor() {
		return this.color;
	}

	public LinkedList<PlayerSegment> getBody() {
		return this.body;
	}

	public Vector2f getPosition() {
		return this.pos;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isNextBlank() {
		return nextBlank;
	}

	public Vector2f getLastPosition() {
		return lastPos;
	}
}
