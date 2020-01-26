package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Enemy extends Entity implements Paint{
	private int damage;
	public Enemy(Dimension size, Image gif, int x, int y, int damage) {
		super(size, gif, x, y);
		this.damage = damage;
	}
	
	
	@Override
	public void paint(Graphics g) {
		x += vx;
		this.rect = new Rectangle(x, y, size.width, size.height);
		faces[0] = new Rectangle(x, y, size.width, 5); // Top Face
		faces[1] = new Rectangle(x, y-5, 5, size.height-5); // Left Face
		faces[2] = new Rectangle(x + size.width, y-5, 5, size.height-5); // Right Face
		faces[3] = new Rectangle(x, y + size.height, size.width, 5); // Bottom Face
		g.setColor(Color.red);
		g.fillRect(x, y, size.width, size.height);
		g.drawImage(gif, x, y, size.width, size.height, null, null );
		
	}

}
