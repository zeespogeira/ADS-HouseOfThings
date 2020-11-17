import Models.AbstractActuator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HouseOfThingsApplication {

	public static void main(String[] args) throws IOException {
		//List<AbstractActuator> actuatorList;
		//actuatorList=new ArrayList<>();

		//List<AbstractActuator> actuatorList;
		//actuatorList=new ArrayList<>();

		// Run this 3 lines
		/*DiscoveryModule discoveryModule=new DiscoveryModule();
		discoveryModule.loadFiles();
		discoveryModule.processEvents();*/


		//CODIGO PARA CORRER
		/*DiscoveryModuleManualReflection discoveryModule=new DiscoveryModuleManualReflection();
		discoveryModule.loadFiles();
		discoveryModule.processEvents();*/


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

		//System.out.println("MAIN");

	}

}
