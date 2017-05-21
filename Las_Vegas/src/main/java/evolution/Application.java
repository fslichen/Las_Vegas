package evolution;

public class Application {
	public static void main(String[] args) throws Exception {
		LasVegas lasVegas = new LasVegas();
		String str = lasVegas.encrypt("/Users/chenli/Desktop/SwaggerFactory.java",
				"/Users/chenli/Desktop/Encrypted_SwaggerFactory.java");
		System.out.println(str);
		lasVegas.decrypt("/Users/chenli/Desktop/Encrypted_SwaggerFactory.java", 
				"/Users/chenli/Desktop/Decrypted_SwaggerFactory.java");
	}
}
