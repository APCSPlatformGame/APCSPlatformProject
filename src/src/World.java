package src;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JPanel;
import jdk.internal.dynalink.beans.StaticClass;

//https://stackoverflow.com/questions/20389255/reading-a-resource-file-from-within-jar 
//Use this resource when we complete the project, as we want a runnable jar file instead of an eclipse project.
public class World extends JPanel implements KeyListener {
	static String src = new File("").getAbsolutePath() +   "/src/"; //Uncomment for production code
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}}
