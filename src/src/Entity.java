package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class Entity implements Paint {
	protected Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	protected Image gif; //icon 
	protected Dimension size;
	protected int x, y, vx=0, vy=0;
	protected Rectangle rect;
	protected Rectangle[] faces = new Rectangle[4];
	public boolean switcher = false;
	private int numTicks = 0;
	
	public Entity(Dimension size, Image gif) {
		this.size = size;
		this.gif = gif;
		y = (int) screen.getHeight() - size.height-70;
		x = 0;
		rect = new Rectangle(x, y, screen.width, screen.height);
		faces[0] = new Rectangle(x, y, size.width, 5); // Top Face
		faces[1] = new Rectangle(x, y+5, 5, size.height-5); // Left Face
		faces[2] = new Rectangle(x + size.width -5, y+5, 5, size.height-5); // Right Face
		faces[3] = new Rectangle(x, y + size.height, size.width, 5); // Bottom Face
	}
	
	public Entity(Dimension size,Image gif, int x, int y) {
		this(size, gif);
		this.x = x;
		this.y = y;
	}
	
	public Point getPos() {
		return new Point(x, y);
	}
	public void setPos(Point newPos) {
		x = newPos.x;
		y = newPos.y;
	}
	public Dimension size() {return size;}
	public int X() {return x;}
	public int Y() {return y;}
	public void setY(int y) {
		this.y = y;
	}
	public Rectangle getTop() {
		return faces[0];
	}
	public Rectangle getBottom(){
		return faces[3];
	}
	public Rectangle getRight() {
		return faces[2];
	}
	public Rectangle getLeft() {
		return faces[1];
	}
	public void setVx(int vx) {this.vx = vx;}
	public void setVy(int vy) {this.vy = vy;}
	public void setScreen(Dimension screen) {this.screen = screen;}
	public void setGif(Image gif) {
		this.gif = gif;
	}
	public void switcher(int max) {
		max = Math.abs(max);
		if(numTicks == max) {
			numTicks = 0;
		}else if( numTicks <= max/2){
			switcher = false;
			numTicks++;
		}else {
			switcher = true;
			numTicks++;
		}
	}
	public void move(int id) {
		switch(id) {
			case 1:
				if(switcher == false) {y--;}
				else {y++;}
				break;
			case 2:
				if(!switcher) { x--; }
				else { x++; }
				break;
			case 3:
				break;
		}
			
	}
	
	@Override
	public void paint(Graphics g) {
		x += vx;
		this.rect = new Rectangle(x, y, size.width, size.height);
		faces[0] = new Rectangle(x, y, size.width, 5); // Top Face
		faces[1] = new Rectangle(x, y+5, 5, size.height-5); // Left Face
		faces[2] = new Rectangle(x + size.width -5, y+5, 5, size.height-5); // Right Face
		faces[3] = new Rectangle(x, y + size.height, size.width, 5); // Bottom Face
		g.setColor(Color.orange);
		g.fillRect(x, y, size.width, size.height);
		g.setColor(Color.blue);
		g.drawRect(x + size.width -5, y+5, 5, size.height-5);
		g.drawRect(x, y+5, 5, size.height-5);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		g.setColor(Color.GREEN);
		g.drawRect(x, y, size.width, 5);
		g.drawRect(x, y + size.height, size.width, 5);
	
	};
}
