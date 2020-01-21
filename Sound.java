import  sun.audio.*;    //import the sun.audio package

import java.applet.AudioClip;
import  java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class SoundFile {
	
	public static void main(String args[]) throws FileNotFoundException{
		String cDir = Paths.get("").toAbsolutePath().toString();
		File sound = new File(cDir + "\\src\\0267.wav");
		InputStream is = new FileInputStream(sound);
		InputStream bis = new BufferedInputStream(is);
		playSound(bis);
	}
	
	public static synchronized void playSound(final InputStream is) {
		new Thread(new Runnable() {
		  public void run() {
		    try {
		      Clip clip = AudioSystem.getClip();
		      AudioInputStream inputStream = AudioSystem.getAudioInputStream(is);
		      clip.open(inputStream);
		      clip.loop(2);
		      clip.start(); 
		    } catch (Exception e) {
		      System.err.println(e.getMessage());
		    }
		  }
		}).start();
	}
}
//https://stackoverflow.com/questions/5724068/how-to-have-the-user-choose-an-audio-file-and-play-it-in-java/5724541#5724541
//https://stackoverflow.com/questions/5529754/java-io-ioexception-mark-reset-not-supported/9324190
