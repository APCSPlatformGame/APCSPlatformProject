package src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Scrap extends Entity implements Paint{
	//========================Constructor========================//
	public Scrap(Dimension size, Image gif, int x, int y) {
		super(size, gif, x, y);
		rect = new Rectangle(x, y, size.width, size.height);
	}
	
	//========================Scrap Collision========================//
	public int checkScrap(int scrap, Player p) {
		if(rect.intersects(p.rect)) {
			scrap++;
			super.setSize(new Dimension(0,0));
		}
		return scrap;
	}

	//========================Painting========================//
	public void paint(Graphics g) {
		x += vx;
		g.drawImage(super.getGif(), x, y, super.size().width, super.size().height, null);
		rect = new Rectangle(x, y, super.size().width, super.size().height);
	}
}
