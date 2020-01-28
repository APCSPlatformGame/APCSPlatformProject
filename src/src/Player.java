package src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Player extends Entity implements Paint{
	//========================Variables========================//
	private final int halfSize = super.size().height/2;
	private final int fullSize = super.size().height;
	private int vy = 0;
	public int jump = 0;
	public int accel = 0;
	public int health = 10;
	public int deaths = 0;
	private boolean damaged = false;
	private boolean bleedingOver = false;
	private Rectangle futureRectangles[] = new Rectangle[2];

	//========================Constructor========================//
	public Player(Dimension size, Image gif) {
		super(size, gif);
		super.x = 100;
		futureRectangles[0] = new Rectangle(x-1, y, 1, size.height);
		futureRectangles[1] = new Rectangle(x+size.width, y, 1, size.height);
	}

	//========================Getters/setters========================//	
	public void setVy(int vy) {this.vy = vy;}
	public Rectangle getFront() { return futureRectangles[1];}
	public Rectangle getBack() { return futureRectangles[0];}

	//========================Player Movement========================//
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
		super.setSize(new Dimension(super.size().width, halfSize));
		super.y += halfSize;
	}
	public void unCrouch() {
		super.setSize(new Dimension(super.size().width, fullSize));
		super.y -= fullSize;
	}

	public void strike(Enemy e) {
		rect = new Rectangle(x, y, super.size().width*2, super.size().height);
		if(e.rect.intersects(rect)) {
			e.setSize(new Dimension(0, 0)); 
			}
	}
	//========================Damage control========================//	
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
	//========================Painting========================//
	public void paint(Graphics g) {
		y += vy;
		rect = new Rectangle(x, y, super.size().width, super.size().height);
		futureRectangles[0] = new Rectangle(x-1 + accel, y, 1+accel, super.size().height);
		futureRectangles[1] = new Rectangle(x+super.size().width + accel, y, 1, super.size().height);
		g.drawImage(super.getGif(), super.x, super.y, super.size().width, super.size().height, null);
	}



}
