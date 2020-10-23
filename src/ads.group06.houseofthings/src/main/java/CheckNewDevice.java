import Actuators.ActuatorsClass;
import Actuators.Lamp.LampBosch;

import Interface.ISensor;

import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Actuators.ActuatorsFactory;

public class CheckNewDevice {
    Integer numberOfModulesConnected;

    private final WatchService watcher;
    private final Path dir;
    private final String currentPath;
    public List<Object> actuatorList;
    public List<ISensor> ISensorList;
    DiscoveryModule discoveryModule;


    /**
     * Creates a WatchService and registers the given directory
     */
    CheckNewDevice() throws IOException {

        File directory = new File("./"); //  ./../../../Devices
        directory.getParent();directory.getParent();directory.getParent();
        String currentFolder= directory + "/Devices";
        dir = Path.of(currentFolder);
        currentPath=directory.getCanonicalPath() + "\\Devices";
       // System.out.println("currentFolder: " + );

        actuatorList=new ArrayList<>();
        discoveryModule=new DiscoveryModule();

        this.watcher = FileSystems.getDefault().newWatchService();
        dir.register(watcher, ENTRY_CREATE);
    }

    /**
     * Process all events for the key queued to the watcher.
     * @return
     */
    public void processEvents() throws IOException {
        // Waits for a new file. Just in run-time. Doens't check the ones already in the directory
        for (;;) {

            // wait for key to be signaled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                if (kind == OVERFLOW) {
                    continue;
                }

                //The filename is the context of the event.
                WatchEvent<Path> ev = (WatchEvent<Path>)event;
                Path filename = ev.context();

                try {
                    Path child = dir.resolve(filename);
                    //System.out.println(Files.probeContentType(child));
                    //if(!child.endsWith(".csv")){
                    if (!Files.probeContentType(child).equals("application/vnd.ms-excel")) {
                        System.err.format("New file '%s' is not a csv text file.%n", filename);
                        continue;
                    }
                } catch (IOException x) {
                    System.err.println(x);
                    continue;
                }

                //Is a CSV file. Need to instantiate class
                System.out.format("Instantiate Class from file %s%n", filename);


                //using threads
                synchronized(CheckNewDevice.class){
                    ExecutorService service = Executors.newFixedThreadPool(4);
                    service.submit(new Runnable() {
                        public void run() {
                            try {
                                //
                                discoveryModule.readCSV(filename);
                                //readCSV(filename);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                //discoveryModule.readCSV(filename);
            }}

            //Reset the key -- this step is critical if you want to receive
            //further watch events. If the key is no longer valid, the directory
            //is inaccessible so exit the loop.
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

    public void readCSV(Path filename) throws IOException {
        //ver os ficheiros
        // inicializar as classes adicionadas ao ficheiro
           /* Aquecedor aquecedor=new AquecerdorBosch();
            aquecedor.setTemperatura(20);*/
        // inicializar as classes adicionadas ao ficheiro
        //Aquecedor aquecedor=new AquecerdorBosch(temperatura);

        //Need to check and instantiate the files already created

        //System.out.println("****************ReadCSV******************");

        var filePath = currentPath + "\\" + filename;
        System.out.println(filePath);

        /*
        * Files.lines(Paths.get(filePath))
                //.skip(0) // ignore the first entry
                //.filter(line -> line.startsWith("India,Bihar"))
                .forEach(System.out::println);
        */
        BufferedReader br = new BufferedReader(new FileReader(String.valueOf(filePath)));
        String line;
        while ((line = br.readLine()) != null) {
            // use comma as separator
            String[] cols = line.split(",");
            //System.out.println(cols[0]);
            System.out.println(cols[1]);
            if(cols[0].equalsIgnoreCase("Actuators")){
                //System.out.println(cols[1]);
                    //creates a class
                    this.instantiateModuleActuators(cols);
            }
            else System.out.println("File has a wrong structure. Try \"Actuator/Sensor, Type, Brand\"");

        }

        // CSV Format:
        // Actuator/Sensor, Type, Brand, ...
    }
    public void instantiateModuleActuators(String[] cols){
        String classe=cols[1].concat(cols[2]);
        ActuatorsFactory actuatorsFactory=new ActuatorsFactory();
        ActuatorsClass newAct = actuatorsFactory.getActuator(classe);

        // Irá vir das regras
        if(newAct instanceof LampBosch){
            ((LampBosch) newAct).setIlumination(String.valueOf(cols[3]));
            // actuatorList.add(((LampBosch) newAct).getIlumination());
            //actuatorList.add((LampBosch) newAct);
        }

        actuatorList.add(newAct);

        /*System.out.println("classe: " + classe);
        System.out.println(newAct.toString());*/

        // FAZER um iterator da lista
        System.out.println("Number of Activators: " + getNumberOfActuators());

    }

    public Integer getNumberOfActuators(){
        return actuatorList.size();
    }
}
