import Models.AbstractActuator;
import Models.AbstractSensor;
import infrastructure.SensorReadingsHub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class DiscoveryModule extends Thread{

    private final String currentPath;
    private final WatchService watcher;
    private final Path dir;

    private SensorReadingsHub sensorReadingsHub;

    //ArrayList<AbstractActuator> actuatorList;
    List<AbstractActuator> actuatorList = Collections.synchronizedList(new ArrayList<AbstractActuator>());
    List<AbstractSensor> sensorList= Collections.synchronizedList(new ArrayList<AbstractSensor>());;

    /**
     * Creates a WatchService and registers the given directory
     */
    DiscoveryModule(List<AbstractActuator> actuatorList, List<AbstractSensor> sensorList, SensorReadingsHub sensorReadingsHub) throws IOException {

        this.actuatorList = actuatorList;
        this.sensorList=sensorList;

        this.sensorReadingsHub=sensorReadingsHub;

        File directory = new File("./");
        currentPath=directory.getCanonicalPath() + "\\src\\ads.group06.houseofthings\\src\\main\\java\\Devices";
        String currentFolder= directory + "/src/ads.group06.houseofthings/src/main/java/Devices";

        dir = Path.of(currentFolder);
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
                    //if(!child.endsWith(".csv")){
                    if (!Files.probeContentType(child).equals("application/vnd.ms-excel")) {
                        System.err.format("New file '%s' is not a csv text file.%n", filename);
                        continue;
                    }
                } catch (IOException x) {
                    System.err.println(x);
                    continue;
                }

                //using threads
                synchronized(DiscoveryModule.class){
                    ExecutorService service = Executors.newFixedThreadPool(4);
                    service.submit(new Runnable() {
                        public void run() {
                            try {
                                //discoveryModule.readCSV(filename);
                                //var filePath = currentPath + "\\" + filename;
                                readCSV(Paths.get(currentPath + "\\" + filename));
                                //getActuatorsList();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    //printActuatorsList();
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
            synchronized(DiscoveryModule.class){
                try {
                    readCSV((Path) temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void readCSV(Path filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(String.valueOf(filename)));
        String line;
        // Maybe just go to the first line. Don't check the other lines
        while ((line = br.readLine()) != null) {
            // use comma as separator
            String[] cols = line.split(",");
            //System.out.println(cols[0] + " " + cols[1]);
            if(cols[0].equalsIgnoreCase("Actuator")){
                //System.out.println("********* Actuators *********");
                this.instantiateModuleActuators(cols);
            }
            else if(cols[0].equalsIgnoreCase("Sensor")){
                this.instantiateModuleSensor(cols);
            }
            else System.out.println("File has a wrong structure. Try \"Actuator/Sensor, Type, Brand\"");
        }
        br.close();
        // CSV Format:
        // Actuator/Sensor, Brand
    }

    public synchronized void instantiateModuleActuators(String[] cols){
        for(int i=0; i<cols.length; i++){
            cols[i]=cols[i].replaceAll("\\s+","");
        }
        String classe=(cols[1].substring(0, 1).toUpperCase() + cols[1].substring(1))
                .concat(cols[2].substring(0, 1).toUpperCase() + cols[2].substring(1));
        classe=classe.replaceAll("\\s+","");

        String name=(cols[1].substring(0, 1).toUpperCase() + cols[1].substring(1).toLowerCase())
                .concat(" ");
        name=name.concat(cols[3].substring(0, 1).toUpperCase() + cols[3].substring(1).toLowerCase());
        //name=name.replaceAll("\\s+","");

        Class<?> factoryClsImpl = null;
        AbstractActuator obj = null;
        try {
            factoryClsImpl = Class.forName("Actuators." + cols[1].replaceAll("\\s+","") + "." +  classe);
            obj = (AbstractActuator) factoryClsImpl.newInstance();
            obj.setName(name);
            actuatorList.add(obj);
            //System.out.println(obj);
        } catch (ClassNotFoundException e) {
            System.err.format("This Actuator brand doesn't have a plugin\n");
            return;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public synchronized void instantiateModuleSensor(String[] cols){
        for(int i=0; i<cols.length; i++){
            cols[i]=cols[i].replaceAll("\\s+","");
        }
        String classe=(cols[1].substring(0, 1).toUpperCase() + cols[1].substring(1))
                .concat(cols[2].substring(0, 1).toUpperCase() + cols[2].substring(1));
        classe=classe.replaceAll("\\s+","");


        String name=(cols[3].substring(0, 1).toUpperCase() + cols[3].substring(1).toLowerCase()).concat(" ");
        name=name.concat(cols[4].substring(0, 1).toUpperCase() + cols[4].substring(1).toLowerCase());

        Class<?> factoryImpl = null;
        AbstractSensor obj = null;
        try {
            //Tambem teria de por todos os sensors no mesmo package
            factoryImpl = Class.forName("Sensors." + cols[1].replaceAll("\\s+","") + "." + classe);
            obj = (AbstractSensor) factoryImpl.newInstance();
            obj.setName(name);
            obj.setWhatIsMeasuring(cols[3]);
            sensorReadingsHub.addSensor(obj);
            sensorList.add(obj);
        } catch (ClassNotFoundException e) {
            System.err.format("This Sensor brand doesn't have a plugin\n");
            return;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

    }

    public Integer getNumberOfActuators(){
        return actuatorList.size();
    }

    public Integer getNumberOfSensors(){
        return sensorList.size();
    }

    public List<AbstractActuator> getActuatorsList(){
        synchronized (actuatorList) {
            return actuatorList;
        }
    }

    public void printActuatorsList(){
        synchronized (actuatorList){
            System.out.println("******* Actuators List *******");
            Iterator it =actuatorList.iterator();
            while (it.hasNext()){
                System.out.println(it.next());
            }
        }
    }

    public List<AbstractSensor> getSensorList(){
        synchronized (sensorList) {
            return sensorList;
        }
    }

    public void printSensorList(){
        synchronized (sensorList){
            System.out.println("******* Sensor List *******");
            Iterator it =sensorList.iterator();
            while (it.hasNext()){
                System.out.println(it.next());
            }
        }
    }
}
