package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Player extends Entity implements Paint{
	private final int halfSize = super.size/2;
	private final int fullSize = super.size;
	public int jump = 0;
	public int accel = 0;
	private int player_weight = super.size/10;
	public Player(int size, Image gif) {
		super(size, gif);
		super.x = 50;
		// TODO Auto-generated constructor stub
	}
	
	public int getPlayerWeight() {
		return player_weight;
	}
	public void moveRight() {
		if(accel < 20) {
			accel += 4;
		}else {
			accel++;
		}
	}
	public void moveLeft() {
		if(accel > -20) {
			accel -= 4;
		}else {
			accel--;
		}
	}
	
	public void jump(boolean onGround) {
		if(onGround) {
			super.y -= 1;
			jump = 90;
		}
	}
	
	public void crouch() {
		size = halfSize;
		super.x += halfSize;
		super.y += halfSize;
	}
	public void unCrouch() {
		size = fullSize;
		super.x -= halfSize;
		super.y -= fullSize;
	}
	
	public void paint(Graphics g) {
		//super.x += super.vx;
		super.y += super.vy;
		rect = new Rectangle(super.X(), super.Y(), size, size);
		g.setColor(Color.orange);
		g.fillRect(super.x, super.y, size, size);
		g.setColor(Color.blue);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
	}

}
