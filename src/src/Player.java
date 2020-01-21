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
	public void gameOver(Graphics g, Dimension screen){
		if(health==0){
			g.fillRect(0, 0, screen.width, screen.height);
			health = 3;
			super.x = 50;
			deaths++;
		}
		if(deaths == 1){
			g.drawString("Oh... oh you... died... oh no... I need you not to do that next time okay?", 40, 40);
		}
		if(deaths == 2){
			g.drawString("You know, you really aren't getting the message here... Get the scrap and get out, it isn't hard.", 40, 40);
		}
		if(deaths == 3){
			g.drawString("I've been more than patient with you, any more deaths and Undertale is going to sue us.", 40, 40);
		}
		if(deaths == 4){
			g.drawString("Have you considered getting someone who doesn't suck at video games to help you get out of here? Please? For me? I have things to do too.", 40, 40);
		}
		if(deaths == 5){
			g.drawString("WHAT THE HELL ARE YOU DOING!!! JUST JUMP!!! IT ISN'T HARD!!! FOR THE LOVE OF MOTHER F... (KNOCK KNOCK KNOCK)", 40, 40);
		}
		if(deaths >= 6){
			g.drawString("NOTICE: ANGRY DROID HAS BEEN REPOSSESSED BY TOBY FOX AND CO. PLEASE PROCEED WITH CAUTION", 40, 40);
		}
	}
	
	public void damage(){
		//collision detection
		if(damaged==true){
			health--;
		}
	}

}
