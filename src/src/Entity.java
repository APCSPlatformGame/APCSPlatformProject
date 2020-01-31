package src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class Entity implements Paint {
//========================Variables========================//
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private Image gif; //icon 
	protected int x, y, vx=0;
	private Dimension size;
	protected Rectangle rect;
	protected Rectangle[] faces = new Rectangle[4];
	public boolean switcher = false; //Boolean to determine when to change direction
	private int numTicks = 0; //tick for how long to move
	protected  Point startPoint;
	
//========================Constructors========================//
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
		startPoint = new Point(x, y);
	}
	
	public Entity(Dimension size,Image gif, int x, int y) {
		this(size, gif);
		this.x = x;
		this.y = y;
		startPoint = new Point(x, y);
	}
	public Entity(Dimension size,Image[] gifs, int x, int y) {
		if(size.width > size.height) {
			gif = gifs[1];
		}else if(size.height > size.width) {
			gif = gifs[0];
		}else {
			gif = gifs[2];
		}
		this.size = size;
		this.x = x;
		this.y = y;
		rect = new Rectangle(x, y, screen.width, screen.height);
		faces[0] = new Rectangle(x, y, size.width, 5); // Top Face
		faces[1] = new Rectangle(x, y+5, 5, size.height-5); // Left Face
		faces[2] = new Rectangle(x + size.width -5, y+5, 5, size.height-5); // Right Face
		faces[3] = new Rectangle(x, y + size.height, size.width, 5); // Bottom Face
		startPoint = new Point(x, y);
	}
	
//========================Getters and Setters========================//
	public void setScreen(Dimension screen) {this.screen = screen;}
	public Dimension size() {return size;}
	public void setSize(Dimension size) {this.size = size;}
	public int x() {return x;}
	public int y() {return y;}
	public void setY(int y) {this.y = y;}
	public Rectangle getTop() {return faces[0];}
	public Rectangle getBottom(){return faces[3];}
	public Rectangle getRight() {return faces[2];}
	public Rectangle getLeft() {return faces[1];}
	public void setVx(int vx) {this.vx = vx;}
	public void setGif(Image gif) {this.gif = gif;}
	public Image getGif() { return gif;}
//========================Movement========================//
	public void switcher(int max) {
		max = Math.abs(max);
		if(numTicks == max) {
			numTicks = 0;
		}else if( numTicks < max/2){
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
	public void resetPos() {
		x = startPoint.x;
		y = startPoint.y;
		numTicks = 0;
	}
//========================Painting========================//	
	@Override
	public void paint(Graphics g) {
		x += vx;
		this.rect = new Rectangle(x, y, size.width, size.height);
		faces[0] = new Rectangle(x, y, size.width, 5); // Top Face
		faces[1] = new Rectangle(x, y+5, 5, size.height-5); // Left Face
		faces[2] = new Rectangle(x + size.width -5, y+5, 5, size.height-5); // Right Face
		faces[3] = new Rectangle(x, y + size.height, size.width, 5); // Bottom Face
		g.drawImage(gif, x, y, rect.width, rect.height, null, null);

	};
}
