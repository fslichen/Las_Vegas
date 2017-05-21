package evolution;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class LasVegas {
	public static final Map<Byte, String> encodingMap = new LinkedHashMap();
	public static final Map<String, Byte> decodingMap = new LinkedHashMap();
	
	static {
		byte k = -128;
		for (short i = 97; i < 123; i++) {
			char character = (char) i;
			for (short j = 48; j < 58; j++) {
				char number = (char) j;
				encodingMap.put(k, character + "" + number);
				k++;
				if (k == 127) {
					encodingMap.put(k, "z5");
					break;
				}
			}
		}
		for (Entry<Byte, String> entry : encodingMap.entrySet()) {
			decodingMap.put(entry.getValue(), entry.getKey());
		}
	}
	
	public String encrypt(String originalFilePath, String encryptedFilePath) throws Exception {
		Path path = Paths.get(originalFilePath);
		byte[] fileInByte =  Files.readAllBytes(path);
		StringBuilder fileStringBuilder = new StringBuilder();
		for (int i = 0; i < fileInByte.length; i++) {
			fileStringBuilder.append(encodingMap.get(fileInByte[i]));
		}
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(encryptedFilePath)));
		String fileString = fileStringBuilder.toString();
		bufferedWriter.write(fileString);
		bufferedWriter.close();
		return fileString;
	}
	
	public void decrypt(String encryptedFilePath, String decryptedFilePath) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(encryptedFilePath)));
		String fileString = bufferedReader.readLine();
		bufferedReader.close();
		File decryptedFile = new File(decryptedFilePath);
		FileOutputStream out = new FileOutputStream(decryptedFile);
		for (int i = 0; i < fileString.length(); i += 2) {
			byte b = decodingMap.get(fileString.substring(i, i + 2));
			out.write(b);
		}
		out.close();
	}
}
