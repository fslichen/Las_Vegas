package evolution;

public class Application {
	public static void main(String[] args) throws Exception {
		LasVegas lasVegas = new LasVegas();
		lasVegas.encrypt("/Users/chenli/Desktop/Buffer/file.zip", 3,
				"/Users/chenli/Desktop/Buffer/file");
		lasVegas.decrypt("/Users/chenli/Desktop/Buffer/file");
	}
}
