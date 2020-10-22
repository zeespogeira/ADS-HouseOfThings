import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HouseOfThingsApplication {


	//private static String currentPath;

	public static void main(String[] args) throws IOException {

		/*ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				try {
					new CheckNewDevice().processEvents();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
*/
		//DiscoveryModule discoveryModule=new DiscoveryModule();
		//discoveryModule.loadFiles();
		new CheckNewDevice().processEvents();

		//System.out.println("Hello");
	}

}
