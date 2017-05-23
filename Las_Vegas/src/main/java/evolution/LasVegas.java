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
import java.util.TreeMap;

public class LasVegas {
	public static final Map<Byte, String> encodingMap = new LinkedHashMap<>();
	public static final Map<String, Byte> decodingMap = new LinkedHashMap<>();
	
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
	
	public void encrypt(String originalFilePath, int segmentCount, String encryptedFolderPath) throws Exception {
		Path path = Paths.get(originalFilePath);
		byte[] fileInByte =  Files.readAllBytes(path);
		StringBuilder fileString = new StringBuilder();
		for (int i = 0; i < fileInByte.length; i++) {
			fileString.append(encodingMap.get(fileInByte[i]));
		}
		int length = fileString.length();
		int segmentSize = length / segmentCount;
		for (int i = 0; i < segmentCount; i++) {
			String fileStringSegment = fileString.substring(i * segmentSize, Math.min((i + 1) * segmentSize, length));
			File file = new File(encryptedFolderPath + "/" + i);
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			bufferedWriter.write(fileStringSegment);
			bufferedWriter.close();
		}
	}
	
	public void decrypt(String encryptedFolderPath) throws Exception {
		File[] files = new File(encryptedFolderPath).listFiles();
		Map<Integer, File> fileMap = new TreeMap<>();
		StringBuilder fileString = new StringBuilder();
		for (File file : files) {
			try {
				fileMap.put(new Integer(file.getName()), file);
			} catch (Exception e) {}// File name is not an integer.
		}
		for (Entry<Integer, File> entry : fileMap.entrySet()) {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(entry.getValue()));
			fileString.append(bufferedReader.readLine());
			bufferedReader.close();
		}
		File decryptedFile = new File(encryptedFolderPath + "/file.zip");
		FileOutputStream out = new FileOutputStream(decryptedFile);
		byte[] bytes = new byte[fileString.length() / 2];
		for (int i = 0; i < fileString.length(); i += 2) {
			byte b = decodingMap.get(fileString.substring(i, i + 2));
			bytes[i / 2] = b;
		}
		out.write(bytes);
		out.close();
	}
}
