package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Scrap extends Entity implements Paint{

	public Scrap(Dimension size, Image gif, int x, int y) {
		super(size, gif, x, y);
		rect = new Rectangle(x, y, size.width, size.height);
	}
	public int checkScrap(int scrap, Player p) {
		if(rect.intersects(p.rect)) {
			scrap++;
			size = new Dimension(0,0);
		}
		return scrap;
	}
	public void paint(Graphics g) {
		x += vx;
		g.setColor(Color.green);
		g.fillRect(x, y, size.width, size.height);
		rect = new Rectangle(x, y, size.width, size.height);
	}
}
