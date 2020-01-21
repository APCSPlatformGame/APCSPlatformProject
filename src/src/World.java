package src;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


//https://stackoverflow.com/questions/20389255/reading-a-resource-file-from-within-jar 
//Use this resource when we complete the project, as we want a runnable jar file instead of an eclipse project.
@SuppressWarnings("serial")
public class World extends JPanel implements KeyListener, ActionListener, Paint, ComponentListener {
	private Dimension screen = new Dimension(800, 800);
	final int screen_width = (int) screen.getWidth();
	final int screen_height = (int) screen.getHeight();	
	private Image bg;
	private Player player = new Player(50, bg);
	private Entity testBlock = new Entity(100, null, 500, screen_height-300);
	private JFrame f;
	private boolean onGround = true;
	private int gravity = 10;
	private int scrapCount = 0;
	
	public World(Image bg2) {
		f = new JFrame();
		f.setTitle("Scrap Heap");
		f.setMaximumSize(new Dimension(1600, 800));
		f.setMinimumSize(new Dimension(400, 800));
		f.setPreferredSize(screen);
		f.setSize(screen);
		
		f.setBackground(Color.black);
		f.setResizable(true);
		f.addKeyListener(this);
		f.addComponentListener(this);
		f.add(this);
		
		
		
		
		t = new Timer(17,this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		bg = bg2;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bg, 0, 0, screen_width, screen_height, null);
		player.paint(g);
		testBlock.paint(g);
		
	}
	
	Timer t;

	
	
	
	public void keyPressed(KeyEvent arg0) {
		switch(arg0.getKeyCode()) {
		case 65: //A
			if(player.accel == 0) {
				player.accel = -5;
			}
			player.moveLeft();
			break;
		case 83: //S
			player.crouch();
			break;
		case 68: //D
			if(player.accel == 0) {
				player.accel = 5;
			}
			player.moveRight();
			break;
		case 37: //left
			testBlock.x--;
			break;
		case 39: //right
			testBlock.x++;
			break;
		case 38:
			testBlock.y--;
			break;
		case 40: //down
			testBlock.y++;
		}
		
	}

	
	@Override
	public void keyReleased(KeyEvent arg0) {
		switch(arg0.getKeyCode()) {
		case 65: //A
			player.moveLeft();
			break;
		case 68: //D
			player.moveRight();
			break;
		case 87:
			player.jump(onGround);
			break;
		case 83: //S
			player.unCrouch();
			break;

		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		
		
		
		if(player.y == (int) screen.getHeight()-player.size()-70 ) {
			onGround = true;
		}else {
			onGround = false;
		}
		
		Collide(player, testBlock);
		
		
		
		if(!onGround) {
			player.setVy(-player.jump);
			player.jump -= gravity;
		}
		
		if(player.Y() > (int) screen.getHeight()-player.size()-70) {
			player.setY((int) screen.getHeight()-player.size()-70);
			player.setVy(0);
			player.jump = 0;
		}
		
		if(player.accel > 0 && onGround) {
			player.accel--;
		}else if(player.accel < 0 && onGround) {
			player.accel++;
		}
		
		testBlock.setVx(-player.accel);
		
	}
	
	public void Collide(Player player, Entity entity) {
		if(player.rect.y + player.rect.height == entity.rect.y && player.rect.x - player.rect.width/2 >= entity.rect.x && player.rect.x + player.rect.width/2 <= entity.rect.x + entity.rect.width ) {
			onGround = true;
		}
		if(player.rect.intersects(entity.rect)) {
//			// player.rect.x + player.rect.width > entity.rect.x && player.rect.x + player.rect.width < entity.rect.x + entity.rect.width/2 ;
//			// && player.rect.y < entity.rect.y + entity.rect.height + player.rect.height
//			boolean isBelow = player.rect.y < entity.rect.y + entity.rect.height;
//			boolean onTop = player.rect.y + player.rect.height > entity.rect.y && player.rect. y <= entity.rect.y + player.rect.height ;
//			boolean isInfront = player.rect.x + player.rect.width > entity.rect.x && player.rect.x + player.rect.width <= entity.rect.x + player.rect.width;
//			boolean isBehind = player.rect.x  < entity.rect.x + entity.rect.width && player.rect.x >= entity.rect.x + entity.rect.width/2 - player.rect.width;
//			
			boolean onTop = player.rect.intersects(entity.getTop());
			boolean isBelow = player.rect.intersects(entity.getBottom());
			boolean isBehind = player.rect.intersects(entity.getRight());
			boolean isInfront = player.rect.intersects(entity.getLeft());
			
			System.out.println("onTop:" +  onTop);
			
			
			if(isInfront && !onTop) {
				player.accel = 0;
				entity.x = player.rect.x + player.rect.width;
			}
			if(isBehind && !onTop) {
				player.accel = 0;
				entity.x = player.rect.x - entity.rect.width;
			}
			if(isBelow && !onTop) {
				player.y = entity.rect.y+entity.rect.height;
				player.jump = 0;
			}
			if(onTop) {
				player.y = entity.rect.y - player.rect.height;
				player.jump = 0;
				player.setVy(0);
			}

		}
	}
	@Override
	public void componentHidden(ComponentEvent e) {
		
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void componentResized(ComponentEvent e) {
		screen = new Dimension(getWidth(), 800);
		setSize(screen);
		player.setScreen(screen);
		player.setY((int) screen.getHeight()-player.size()-70);

		
	}
	
	public Dimension getScreen() {return screen;}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}}
