import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HouseOfThingsApplication {

	public static void main(String[] args) throws IOException {
		//List<Object> actuatorList;
		//actuatorList=new ArrayList<>();

		// Run this 3 lines
		/*DiscoveryModule discoveryModule=new DiscoveryModule();
		discoveryModule.loadFiles();
		discoveryModule.processEvents();*/

		//DiscoveryModuleTestWithSupplier discoveryModule=new DiscoveryModuleTestWithSupplier();
		DiscoveryModuleTestWithReflection discoveryModule=new DiscoveryModuleTestWithReflection();
		discoveryModule.loadFiles();
		discoveryModule.processEvents();


		/*ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				try {
					discoveryModule.processEvents();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});*/


		discoveryModule.getActuatorsList();
		//System.out.println("Hello");
	}

}
