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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Point;


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
	
	Point[] startPos = new Point[13];
	Point[] enemyPos = new Point[3];
	
	
	
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
		
		startPos[0] = new Point(270, 700);
		startPos = new Point[] {new Point(600, 700), new Point(600, 250), new Point(825, 500), new Point(1575, 250), new Point(1700, 700), new Point(1950, 450), new Point(2000, 550), new Point(2250, 700), new Point(2300, 250), new Point(600, 0), new Point(600, 5), new Point(1000, 5), new Point(2000, 5),};
		enemyPos = new Point[] {new Point(500, 750), new Point(1250, 750), new Point(2050,500)};
		
		
		
		
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
		g.drawString("You have died " + player.deaths + " times.", 700, 700);
		g.drawString("You have " + player.health + " hearts left.", 700, 600);
		g.drawString("You have " + scrapCount + " scrap in your inventory.", 700, 500);
		
	}
	
	Timer t;

	public void rip(Graphics g){
		if(player.deaths == 1){
			g.drawString("Oh... oh you... died... oh no... I need you not to do that next time okay?", 40, 40);
		}
		if(player.deaths == 2 && player.deaths < 1){
			g.drawString("You know, you really aren't getting the message here... Get the scrap and get out, it isn't hard.", 40, 40);
		}
		if(player.deaths == 3 && player.deaths < 2){
			g.drawString("I've been more than patient with you, any more deaths and Undertale is going to sue us.", 40, 40);
		}
		if(player.deaths == 4 && player.deaths < 3){
			g.drawString("Have you considered getting someone who doesn't suck at video games to help you get out of here? Please? For me? I have things to do too.", 40, 40);
		}
		if(player.deaths == 5 && player.deaths < 4){
			g.drawString("WHAT THE HELL ARE YOU DOING!!! JUST JUMP!!! IT ISN'T HARD!!! FOR THE LOVE OF MOTHER F... (KNOCK KNOCK KNOCK)", 40, 40);
		}
		if(player.deaths >= 6 && player.deaths < 5){
			g.drawString("NOTICE: ANGRY DROID HAS BEEN REPOSSESSED BY TOBY FOX AND CO. PLEASE PROCEED WITH CAUTION", 40, 40);
		}
	}
	
	public int currentLevel(){
		int level = 1;
		boolean enteredDoor = false;
		boolean stop = false;
		if(enteredDoor == true && stop == false){
			level++;
			stop = true;
		}else{
			stop = false;
		}
		return level;
	}
	
	public static void ReadLevel(File file) throws IOException{
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			String line = null;
			while((line = br.readLine()) != null){
				System.out.println(line);
			}	
		}
			
	}
	
	public static void ReadWrite(File file) throws IOException {
		// TODO Auto-generated method stub

		try(FileWriter fileWriter = new FileWriter(file)){
			String fileContent = "Insert Here";
			fileWriter.write(fileContent);
		}
		
	}
	
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
