package src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Enemy extends Entity implements Paint{
	public Enemy(Dimension size, Image gif, int x, int y) {
		super(size, gif, x, y);
	}
	
	
	@Override
	public void paint(Graphics g) {
		x += vx;
		this.rect = new Rectangle(x, y, super.size().width, super.size().height);
		faces[0] = new Rectangle(x, y, super.size().width, 5); // Top Face
		faces[1] = new Rectangle(x, y-5, 5, super.size().height-5); // Left Face
		faces[2] = new Rectangle(x + super.size().width, y-5, 5, super.size().height-5); // Right Face
		faces[3] = new Rectangle(x, y + super.size().height, super.size().width, 5); // Bottom Face
		g.drawImage(super.getGif(), x, y, super.size().width, super.size().height, null, null );
		
	}

}
