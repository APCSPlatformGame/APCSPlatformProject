package src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class Npc extends Entity{
	public Npc(Dimension size, Image gif, int x, int y)  {
		super(size, gif, x, y);
	}
	
	public void paint(Graphics g, int Scrap) {
		super.paint(g);
		if(Scrap != 1) {
			g.drawString(str, Scrap, Scrap);
		}
	}
	

}
