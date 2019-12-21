package src;

import java.awt.Graphics;

import com.sun.prism.Image;

public class Entity implements Paint {
	protected Image gif; //icon needed to load the 
	protected int size;
	protected int x, y, vx=0, vy=0;
	
	public Entity(int size, Image gif) {
		this.size = size;
		this.gif = gif;
		x = 0;
		y = 0;
	}
	
	public Entity(int size,Image gif, int x, int y) {
		this(size, gif);
		this.x = x;
		this.y = y;
	}
	
	public int X() {return x;}
	public int Y() {return y;}
	public void setVx(int x) {this.vx = x;};
	public void setVy(int y) {this.vy = y;}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	};
}
