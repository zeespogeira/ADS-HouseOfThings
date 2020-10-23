import Actuators.ActuatorsClass;
import Actuators.ActuatorsFactory;
import Actuators.Lamp.LampBosch;
import Models.AbstractActuator;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class DiscoveryModule {
    Integer numberOfModulesConnected;

    /*private final WatchService watcher;
    private final Path dir;*/
    private final String currentPath;

    //private List<? extends GenericLamp> lampList;
    //private List<? extends Thermometer> thermometerList;
    private List<Object> actuatorList;

    private final WatchService watcher;
    private final Path dir;
    //DiscoveryModule discoveryModule;


    /**
     * Creates a WatchService and registers the given directory
     */
    DiscoveryModule() throws IOException {

        File directory = new File("./"); //  ./../../../Devices
        directory.getParent();directory.getParent();directory.getParent();
        currentPath=directory.getCanonicalPath() + "\\Devices";
        //currentPath="\\Devices";

        actuatorList=new ArrayList<>();

        String currentFolder= directory + "/Devices";
        dir = Path.of(currentFolder);
        //currentPath=directory.getCanonicalPath() + "\\Devices";
        // System.out.println("currentFolder: " + );

        actuatorList=new ArrayList<>();
        //discoveryModule=new DiscoveryModule();

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
                //System.out.format("Instantiate Class from file %s%n", filename);

                //using threads
                synchronized(DiscoveryModule.class){
                    ExecutorService service = Executors.newFixedThreadPool(4);
                    service.submit(new Runnable() {
                        public void run() {
                            try {
                                //
                                //discoveryModule.readCSV(filename);
                                //var filePath = currentPath + "\\" + filename;
                                readCSV(Paths.get(currentPath + "\\" + filename));
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


    /**
     * Load files already in the directory
     * */
    public void loadFiles() throws IOException {
        List files;
        files = Files.walk(Paths.get(currentPath))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());


        files.forEach((temp) -> {
            //System.out.println(temp);
            //File file=File.

            try {
                readCSV((Path) temp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void readCSV(Path filename) throws IOException {
        //ver os ficheiros
        // inicializar as classes adicionadas ao ficheiro
           /* Aquecedor aquecedor=new AquecerdorBosch();
            aquecedor.setTemperatura(20);*/
        // inicializar as classes adicionadas ao ficheiro
            //Aquecedor aquecedor=new AquecerdorBosch(temperatura);

        BufferedReader br = new BufferedReader(new FileReader(String.valueOf(filename)));
        String line;
        while ((line = br.readLine()) != null) {
            // use comma as separator
            String[] cols = line.split(",");
            if(cols[0].equalsIgnoreCase("Actuators")){
                this.instantiateModuleActuators(cols);
            }
            else System.out.println("File has a wrong structure. Try \"Actuator/Sensor, Type, Brand\"");

        }

        // CSV Format:
            // Actuator/Sensor, Brand
    }
    public void instantiateModuleActuators(String[] cols){
        String classe=cols[1].concat(cols[2]);
        ActuatorsFactory actuatorsFactory=new ActuatorsFactory();
        AbstractActuator newAct = actuatorsFactory.getActuator(classe);

        // Irá vir das regras
        if(newAct instanceof LampBosch){
            ((LampBosch) newAct).setIlumination(String.valueOf(cols[3]));
            // actuatorList.add(((LampBosch) newAct).getIlumination());
            //actuatorList.add((LampBosch) newAct);
        }

        actuatorList.add(newAct);

        /*System.out.println("classe: " + classe);
        System.out.println(newAct.toString());*/

        System.out.format("Device %s already instantiate%n", classe);
        System.out.println("Number of Actuators: " + getNumberOfActuators());
    }

    public Integer getNumberOfActuators(){
        return actuatorList.size();
    }

   /* public void getActuatorsList(){
        //List <ActuatorsClass> list=new ArrayList<>();
        Iterator it =actuatorList.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }*/
}
