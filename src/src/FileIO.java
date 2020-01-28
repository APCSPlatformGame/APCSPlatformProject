package src;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class FileIO {

	public static void ReadWrite(File file, String s) throws IOException {
		// TODO Auto-generated method stub

		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			String line = null;
			while((line = br.readLine()) != null){
				s += line + "\n";
			}
		}
	}
}
