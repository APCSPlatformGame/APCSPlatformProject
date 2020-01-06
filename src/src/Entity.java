package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class Entity implements Paint {
	protected Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	protected Image gif; //icon 
	protected int size;
	protected int x, y, vx=0, vy=0;
	protected Rectangle rect;
	private Rectangle[] faces = new Rectangle[4];
	
	public Entity(int size, Image gif) {
		this.size = size;
		this.gif = gif;
		y = (int) screen.getHeight() - size-70;
		x = 0;
		this.rect = new Rectangle(size, size);
		faces[0] = new Rectangle(x, y-5, size, 5); // Top Face
		faces[1] = new Rectangle(x, y, 5, size); // Left Face
		faces[2] = new Rectangle(x + size, y, 5, size); // Right Face
		faces[3] = new Rectangle(x, y + size, size, 5); // Bottom Face
	}
	
	public Entity(int size,Image gif, int x, int y) {
		this(size, gif);
		this.x = x;
		this.y = y;
	}
	
	public int size() {return size;}
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
	
	@Override
	public void paint(Graphics g) {
		x += vx;
		rect = new Rectangle(x, y, size, size);
		faces[0] = new Rectangle(x, y-5, size, 5); // Top Face
		faces[1] = new Rectangle(x, y, 5, size); // Left Face
		faces[2] = new Rectangle(x + size, y, 5, size); // Right Face
		faces[3] = new Rectangle(x, y + size, size, 5); // Bottom Face
		g.setColor(Color.orange);
		g.fillRect(x, y, size, size);
		g.setColor(Color.blue);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		g.setColor(Color.GREEN);
		g.drawRect(x, y-5, size, 5);
		
	};
}
