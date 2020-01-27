package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Player extends Entity implements Paint{
	private final int halfSize = super.size.height/2;
	private final int fullSize = super.size.height;
	public int jump = 0;
	public int accel = 0;
	public int health = 100000;
	public int deaths = 0;
	private boolean damaged = false;
	private boolean bleedingOver = false;
	private Rectangle futureRectangles[] = new Rectangle[2];
	public Player(Dimension size, Image gif) {
		super(size, gif);
		super.x = 100;
		futureRectangles[0] = new Rectangle(x-1, y, 1, size.height);
		futureRectangles[1] = new Rectangle(x+size.width, y, 1, size.height);
		// TODO Auto-generated constructor stub
	}
	public Rectangle getFront() { return futureRectangles[1];}
	public Rectangle getBack() { return futureRectangles[0];}
	
	public void moveRight() {
		if(accel < 20) {
			accel += 3;
		}else {
			accel++;
		}
	}
	public void moveLeft() {
		if(accel > -20) {
			accel -= 3;
		}else {
			accel--;
		}
	}
	
	public void jump(boolean onGround) {
		if(onGround) {
			super.y -= 1;
			jump = 28;
		}
	}
	
	public void crouch() {
		size.height = halfSize;
		super.y += halfSize;
	}
	public void unCrouch() {
		size.height = fullSize;
		super.y -= fullSize;
	}
	
	public void strike(Enemy e) {
		rect = new Rectangle(x, y, size.width*2, size.height);
		if(e.rect.intersects(rect)) {
			e.size = new Dimension(0, 0);
		}
	}
	
	public void paint(Graphics g) {
		y += vy;
		rect = new Rectangle(x, y, size.width, size.height);
		futureRectangles[0] = new Rectangle(x-1 + accel, y, 1+accel, size.height);
		futureRectangles[1] = new Rectangle(x+size.width + accel, y, 1, size.height);
		g.drawRect(x+size.width + accel, y, 1, size.height);
		g.setColor(Color.orange);
		g.drawImage(gif, super.x, super.y, size.width, size.height, null);
		g.setColor(Color.blue);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
	}
	public void damage(Rectangle EnemyRect){

		damaged = this.rect.intersects(EnemyRect);
		if(damaged==true && bleedingOver == false){
			health--;
			bleedingOver = true;
		} else{
			damaged = false;
			bleedingOver = false;
		}
	}

}
