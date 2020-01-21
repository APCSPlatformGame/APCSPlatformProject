import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class ReadingAndWritingCodeFromFiles {

	public static void ReadWrite(File file) throws IOException {
		// TODO Auto-generated method stub

		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			String line = null;
			while((line = br.readLine()) != null){
				System.out.println(line);
			}
		try(FileWriter fileWriter = new FileWriter(file)){
			String fileContent = "Insert Here";
			fileWriter.write(fileContent);
		}
		}
		
		
		
		
	}

}
