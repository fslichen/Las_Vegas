package evolution;

public class Application {
	public static void main(String[] args) throws Exception {
		LasVegas lasVegas = new LasVegas();
		String str = lasVegas.encrypt("D:/Buffer/Ext_Framework.zip",
				"D:/Buffer/Ext_Framework_Encoded.txt");
		System.out.println(str);
		lasVegas.decrypt("D:/Buffer/Ext_Framework_Encoded.txt", 
				"D:/Buffer/Ext_Framework_Decoded.zip");
	}
}
