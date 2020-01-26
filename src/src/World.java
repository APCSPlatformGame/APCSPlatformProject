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

import javafx.scene.shape.TriangleMesh;


//https://stackoverflow.com/questions/20389255/reading-a-resource-file-from-within-jar 
//Use this resource when we complete the project, as we want a runnable jar file instead of an eclipse project.
@SuppressWarnings("serial")
public class World extends JPanel implements KeyListener, ActionListener, Paint, ComponentListener {
	private Dimension screen = new Dimension(800, 800);
	final int screen_width = (int) screen.getWidth();
	final int screen_height = (int) screen.getHeight();	
	private Toolkit tool = Toolkit.getDefaultToolkit();
	private Image bg;
	private Image playerImg = tool.getImage("C:\\Users\\Tom\\git\\APCSPlatformProject\\Images\\4xuYrtpE7YSLfvaX.gif") ;
	private Player player = new Player(new Dimension(30 ,70), playerImg);
	private JFrame f;
	private boolean onGround = true;
	private int gravity = 2;
	private int scrapCount = 0;
	private int numJumps = 0;
	private boolean canMoveForward = true;
	private boolean canMoveBackward = true;

	private Entity wall1 = new Entity(new Dimension(70 ,screen_height), null, 0, 0);
	private Entity wall2 = new Entity(new Dimension(70 ,screen_height), null, 2600, 0);
	final Entity[] startPos = new Entity[] {new Entity(new Dimension(100 ,100), null, 270, screen_height-170), new Entity(new Dimension(100 ,100), null, 600, screen_height-170), 
			new Entity(new Dimension(170 ,100), null, 600, 250+70), new Entity(new Dimension(100 ,100), null, 825, 500+70), 
			new Entity(new Dimension(170 ,70), null, 1575, 250+70), new Entity(new Dimension(100 ,100), null, 1700, screen_height-170), 
			new Entity(new Dimension(170 ,70), null, 1950, 450+70), new Entity(new Dimension(100 ,100), null, 2000, 550+70), 
			new Entity(new Dimension(170 ,70), null, 2250, screen_height-170), new Entity(new Dimension(100 ,100), null, 2300, 250-70), 
			new Entity(new Dimension(170 ,70), null, 600, 0+70), new Entity(new Dimension(100 ,100), null, 600, 5+70), 
			new Entity(new Dimension(170 ,70), null, 1000, 5+70), new Entity(new Dimension(100 ,100), null, 2000, 5+70)};
	final Enemy[] enemyPos = new Enemy[] {new Enemy(new Dimension(100 ,100), null, 500, screen_height-70, 1), new Enemy(new Dimension(100 ,100), null, 1250, screen_height-70, 1), new Enemy(new Dimension(100 ,100), null, 2050,500-70, 1)};

	Enemy[] Enemies = enemyPos;
	Entity[] Blocks = startPos;

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
		if(player.health > 0) {
			player.paint(g);
			for(Entity e : Blocks) {
				e.paint(g);
			}
			for(Enemy d : Enemies) {
				d.paint(g);
			}
			wall1.paint(g);
			wall2.paint(g);
			//g.drawString("You have died " + player.deaths + " times.", 700, 700);
			//g.drawString("You have " + player.health + " hearts left.", 700, 600);
			//g.drawString("You have " + scrapCount + " scrap in your inventory.", 700, 500);
		}else if (player.health <= 0){
			g.fillRect(0, 0, screen_width, screen_height);
			g.setColor(Color.white);
			g.drawString("You ded", screen_width/2, screen_height/2);
		}
	}

	Timer t;




	public void keyPressed(KeyEvent arg0) {
		switch(arg0.getKeyCode()) {
		case 65: //A
			if(canMoveBackward) {
				if(player.accel == 0) {
					player.accel = -2;
				}
				player.moveLeft();
			}else {
				player.accel++;;
			}
			break;
		case 83: //S
			player.crouch();
			break;
		case 68: //D
			if(canMoveForward) {
				if(player.accel == 0) {
					player.accel = 2;
				}
				player.moveRight();
			}else {
				player.accel--;
			}
			break;
		}

	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		switch(arg0.getKeyCode()) {
		case 65: //A
			break;
		case 68: //D
			break;
		case 87:
			if(numJumps >= 2) {
				numJumps = 0;
			}
			else {
				player.jump(true);
				numJumps++;
			}
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

		for(Entity l : Blocks) {
			canMoveForward = !l.rect.intersects(player.getFront());
			canMoveBackward = !l.rect.intersects(player.getBack());
			if(canMoveForward == false || canMoveBackward == false) {
				break;
			}
		}
		if(player.y == (int) screen.getHeight()-player.size().height-70 ) {
			onGround = true;
		}else {
			onGround = false;
		}
	
		Collide(player, wall1);
		Collide(player, wall2);
		if(canMoveForward && canMoveBackward) {wall2.setVx(-player.accel); wall1.setVx(-player.accel);}
		else {
			wall2.setVx(0); wall1.setVx(0);
		}
		
		
		for(Entity r : Blocks) {
			Collide(player, r);
			int num = (int) (Math.random()*3);
			r.switcher(100);
			r.move(2);
			if(canMoveForward && canMoveBackward) {r.setVx(-player.accel);}
			else {r.setVx(0);}
		}
	
		for(Enemy f : Enemies) {
			player.damage(f.rect);
			if(canMoveForward && canMoveBackward) {f.setVx(-player.accel);}
			else {f.setVx(0);}
		}
		if(player.accel > 0 && onGround) {
			player.accel--;
		}else if(player.accel < 0 && onGround) {
			player.accel++;
		}
		if(!onGround) {
			player.setVy(-player.jump);
			player.jump -= gravity;
		}

		if(player.Y() > (int) screen.getHeight()-player.size().height-70) {
			player.setY((int) screen.getHeight()-player.size().height-70);
			player.setVy(0);
			player.jump = 0;
		}

	
	}

	public void Collide(Player player, Entity entity) {
		//player.rect.y + player.rect.height == entity.rect.y && player.rect.x - player.rect.width/2 >= entity.rect.x && player.rect.x + player.rect.width/2 <= entity.rect.x + entity.rect.width 
		if(player.rect.y == entity.getTop().y - player.rect.height && player.rect.x >= entity.rect.x && player.rect.x < entity.rect.x + entity.rect.width ) {
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

			if(onTop) {
				player.y = entity.getTop().y - player.rect.height;
				player.jump = 0;
				player.setVy(0);
			}
			if(isInfront && !onTop && !isBelow) {
				player.accel = 0;
				entity.x = player.rect.x + player.rect.width;
			}
			if(isBehind && !onTop && !isBelow) {
				player.accel = 0;
				entity.x = player.rect.x - entity.rect.width;
			}
			if(isBelow && !onTop) {
				player.y = entity.rect.y+entity.rect.height;
				player.jump = 0;
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
		player.setY((int) screen.getHeight()-player.size().height-70);


	}

	public Dimension getScreen() {return screen;}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}}
