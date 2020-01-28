package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

//https://stackoverflow.com/questions/20389255/reading-a-resource-file-from-within-jar 
//Use this resource when we complete the project, as we want a runnable jar file instead of an eclipse project.
@SuppressWarnings("serial")
public class World extends JPanel implements KeyListener, ActionListener, Paint, ComponentListener {
	//========================Variables========================//
	//FileIO variables & screen variables
	private Dimension screen = new Dimension(800, 800);
	final int screen_width = (int) screen.getWidth();
	final int screen_height = (int) screen.getHeight();	
	private Toolkit tool = Toolkit.getDefaultToolkit();
	private String imgSrc = new File("").getAbsolutePath().toString() + "\\src\\Images\\";
	private boolean isJar = true; //change for compile
	private Image bg; 
	private Image playerImg; 
	private Image Enemy1; 
	private Image Enemy2; 
	private Image[] plats; 
	private Image scrapImg;
	private JFrame f;
	//Gameplay variables
	private boolean onGround = true;
	private int gravity = 2;
	private int numJumps = 0;
	private boolean canMoveForward = true;
	private boolean canMoveBackward = true;
	private int scrapCount = 0;
	private int bgX = -200, bgY = 0;
	//Game Objects
	private Player player;
	private Scrap scrap;
	private Entity wall1;
	private Entity wall2;
	private Entity[] Blocks;
	private Enemy[] Enemies;

	//========================Constructor========================//
	public World() {
		//Create JFrame
		init(isJar);
		f = new JFrame();
		f.setTitle("Scrap Heap");
		f.setMaximumSize(new Dimension(1600, 800));
		f.setMinimumSize(new Dimension(400, 800));
		f.setPreferredSize(screen);
		f.setSize(screen);
		f.setResizable(true);
		//Adding Listeners
		f.setBackground(Color.black);
		f.addKeyListener(this);
		f.addComponentListener(this);
		f.add(this);
		//Adding ticking timer
		t = new Timer(17,this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

	}
	private void init(boolean isJar) {
		System.out.println(isJar);
		if(!isJar) {
			bg = tool.getImage(imgSrc + "BG.png");
			playerImg =tool.getImage(imgSrc +"Player.png");
			Enemy1 = tool.getImage(imgSrc +"Enemy_tank.png");
			Enemy2 = tool.getImage(imgSrc +"spike.png"); 
			plats = new Image[] { tool.getImage(imgSrc +"long plat.png"), tool.getImage(imgSrc + "Plat_1.png"), tool.getImage(imgSrc + "plat 2.png")};
			scrapImg = tool.getImage(imgSrc +"scrap.png");
		}else {
			try {
				if(getClass().getResourceAsStream("/BG.png") != null) {
					System.out.println("GetClass works");
				}else {
					System.err.println("Error, getClass is null");
				}
				bg = ImageIO.read(getClass().getResourceAsStream("/BG.png"));
				playerImg = ImageIO.read(getClass().getResourceAsStream("/Player.png"));
				Enemy1 = ImageIO.read(getClass().getResourceAsStream("/Enemy_tank.png"));
				Enemy2 = ImageIO.read(getClass().getResourceAsStream("/spike.png"));
				scrapImg = ImageIO.read(getClass().getResourceAsStream("/Scrap.png"));
				plats = new Image[] {ImageIO.read(getClass().getResourceAsStream("/long plat.png")),
						ImageIO.read(getClass().getResourceAsStream("/Plat_1.png")),
						ImageIO.read(getClass().getResourceAsStream("/plat 2.png"))};
			}
			catch (Exception e) {
				System.err.println("Error, probably fileIO");
				e.printStackTrace();
			}
		}


		Blocks = new Entity[] {new Entity(new Dimension(100 ,100), plats, 270, screen_height-170), new Entity(new Dimension(100 ,100), plats, 600, screen_height-170), 
				new Entity(new Dimension(170 ,100), plats, 600, 250+70), new Entity(new Dimension(100 ,200), plats, 825, 500+70), 
				new Entity(new Dimension(170 ,70), plats, 1575, 250+70), new Entity(new Dimension(100 ,100), plats, 1700, screen_height-170), 
				new Entity(new Dimension(170 ,70), plats, 1950, 450+70), new Entity(new Dimension(100 ,200), plats, 2000, 550+70), 
				new Entity(new Dimension(170 ,70), plats, 2250, screen_height-170), new Entity(new Dimension(100 ,100), plats, 2300, 250-70), 
				new Entity(new Dimension(170 ,70), plats, 600, 0+70), new Entity(new Dimension(100 ,200), plats, 600, 5+70), 
				new Entity(new Dimension(170 ,70), plats, 1000, 5+70), new Entity(new Dimension(100 ,100), plats, 2000, 5+70)};
		Enemies = new Enemy[] {new Enemy(new Dimension(100 ,100), Enemy2, 500, screen_height-70), 
				new Enemy(new Dimension(100 ,100), Enemy2, 1250, screen_height-70), 
				new Enemy(new Dimension(100 ,100), Enemy1, 2050,500-70)};
		player = new Player(new Dimension(36 ,55), playerImg);
		scrap = new Scrap(new Dimension(20, 20), scrapImg, 2300, 250-90);
		wall1 = new Entity(new Dimension(200 ,screen_height), plats[0], -200, 0);
		wall2 = new Entity(new Dimension(200 ,screen_height), plats[0], 2600, 0);

	}
	//========================Painting========================//
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bg, bgX, bgY, 2600, 800, null);

		if(player.health > 0) { //Not dead
			if(scrapCount != 0) { //Win condition
				g.fillRect(0, 0, screen_width, screen_height);
				g.setColor(Color.white);
				g.drawString("Congrats! You won this demo!", 100, 100);
				g.drawString("Unfortunetly, we ran out of time for story and levels, so here's the entire thing in one giant text block.", 100, 200);
				g.drawString("Oh, sorry, it's in black ink.", 100, 300);
			}else {
				g.setColor(Color.gray);
				g.fillRect(0, screen_height-70, screen_width, screen_height);
				player.paint(g);
				for(Entity e : Blocks) {
					e.paint(g);
				}
				for(Enemy d : Enemies) {
					d.paint(g);
				}
				wall1.paint(g);
				wall2.paint(g);
				scrap.paint(g);
			}
		}else if (player.health <= 0){
			g.fillRect(0, 0, screen_width, screen_height);
			g.setColor(Color.white);
			g.drawString("You died", screen_width/2, screen_height/2);
		}
	}

	Timer t;


	//========================Player Input========================//
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

	private void jump() {
		if(onGround) {numJumps=0;}
		if(numJumps >= 5) {
			numJumps = 0;
		}
		else if(numJumps <2) {
			player.jump(true); 	
			numJumps++;
		}else {
			numJumps++;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch(arg0.getKeyCode()) {
		case 87: //W
			jump();
			break;
		case 83: //S
			player.unCrouch();
			break;
		case 32: //space
			jump();
			break;
		case 81:
			for(Enemy e : Enemies) {
				player.strike(e);
			}
		}

	}
	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	//========================World Action========================//
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		//Determining if player can move forward/backward
		for(Entity l : Blocks) {
			canMoveForward = !l.rect.intersects(player.getFront());
			canMoveBackward = !l.rect.intersects(player.getBack());
			if(canMoveForward == false || canMoveBackward == false) {
				break;
			}
		}
		//Determining if player is on the ground
		if(player.y == (int) screen.getHeight()-player.size().height-70 ) {
			onGround = true;
		}else {
			onGround = false;
		}

		//Collision on moving world objects
		Collide(player, wall1);
		Collide(player, wall2);
		if(canMoveForward && canMoveBackward) {wall2.setVx(-player.accel); wall1.setVx(-player.accel);}
		else {wall2.setVx(0); wall1.setVx(0);}
		scrapCount = scrap.checkScrap(scrapCount, player); // checking scrap collision
		if(canMoveBackward && canMoveForward) {scrap.setVx(-player.accel);}
		else {scrap.setVx(0);}
		if(canMoveBackward && canMoveForward) {bgX += -player.accel/2;}
		for(Entity r : Blocks) {
			Collide(player, r);
			r.switcher(100);
			r.move(0);
			if(canMoveForward && canMoveBackward) {r.setVx(-player.accel);}
			else {r.setVx(0);}
		}
		for(Enemy f : Enemies) {
			player.damage(f.rect);
			if(canMoveForward && canMoveBackward) {f.setVx(-player.accel);}
			else {f.setVx(0);}
		}
		for(int i = 0; i < Enemies.length-1; i++) {
			Enemies[i].switcher(100);
			Enemies[i].move(1);
		}
		Enemies[2].switcher(100);
		Enemies[2].move(2);
		//Setting player acceleration
		if(player.accel > 0 && onGround) {
			player.accel--;
		}else if(player.accel < 0 && onGround) {
			player.accel++;
		}
		//Player jump functions
		if(!onGround) {
			player.setVy(-player.jump);
			player.jump -= gravity;
		}
		if(player.y > (int) screen.getHeight()-player.size().height-70) {
			player.setY((int) screen.getHeight()-player.size().height-70);
			player.setVy(0);
			player.jump = 0;
		}


	}
	//========================Collision for game Objects========================//
	public void Collide(Player player, Entity entity) {
		if(player.rect.y == entity.getTop().y - player.rect.height && player.rect.x >= entity.rect.x && player.rect.x < entity.rect.x + entity.rect.width ) {
			onGround = true;
		}
		if(player.rect.intersects(entity.rect)) {	
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

	//========================Window Scaling========================//
	@Override
	public void componentHidden(ComponentEvent e) {}
	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentResized(ComponentEvent e) {
		screen = new Dimension(getWidth(), 800);
		setSize(screen);
		player.setScreen(screen);
		player.setY((int) screen.getHeight()-player.size().height-70);
	}
	@Override
	public void componentShown(ComponentEvent e) {}
}
