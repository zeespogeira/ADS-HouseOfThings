import Actuators.DynamicReflectionActuatorFactory;
import Actuators.Lamp.LampBosch;
import Models.AbstractActuator;
import Models.AbstractSensor;
import Sensors.DynamicReflectionSensorFactory;
import Sensors.Humidity.HumidityBosch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class DiscoveryModuleTestWithReflection {
    /*static {
        try { //ads.group06.houseofthings.
            Class.forName("LampPhilips");
            Class.forName("LampBosch");
            Class.forName("ThermometerBosch");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    Integer numberOfModulesConnected;

    private final String currentPath;


    private final WatchService watcher;
    private final Path dir;

    List<AbstractActuator> actuatorList;
    List<AbstractSensor> sensorList;


    /**
     * Creates a WatchService and registers the given directory
     */
    DiscoveryModuleTestWithReflection() throws IOException {

        //this.actuatorList = actuatorList;
        File directory = new File("./"); //  ./../../../Devices
        //directory.getParent();directory.getParent();directory.getParent();
        //currentPath=directory.getCanonicalPath() + "\\Devices";
        currentPath=directory.getCanonicalPath() + "\\src\\ads.group06.houseofthings\\src\\main\\java\\Devices";

        actuatorList=new ArrayList<>();
        sensorList=new ArrayList<>();

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
                synchronized(DiscoveryModuleTestWithReflection.class){
                    ExecutorService service = Executors.newFixedThreadPool(4);
                    service.submit(new Runnable() {
                        public void run() {
                            try {
                                //
                                //discoveryModule.readCSV(filename);
                                //var filePath = currentPath + "\\" + filename;
                                readCSV(Paths.get(currentPath + "\\" + filename));

                            } catch (IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
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

            synchronized(DiscoveryModuleTestWithReflection.class){
                try {
                    readCSV((Path) temp);
                } catch (IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
                    e.printStackTrace();
                }             }
        });

    }

    public void readCSV(Path filename) throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        //ver os ficheiros
        // inicializar as classes adicionadas ao ficheiro
           /* Aquecedor aquecedor=new AquecerdorBosch();
            aquecedor.setTemperatura(20);*/
        // inicializar as classes adicionadas ao ficheiro
        //Aquecedor aquecedor=new AquecerdorBosch(temperatura);

        BufferedReader br = new BufferedReader(new FileReader(String.valueOf(filename)));
        String line;
        // Maybe just go to the first line. Don't check the other lines
        while ((line = br.readLine()) != null) {
            // use comma as separator
            String[] cols = line.split(",");
            //System.out.println(cols[0] + " " + cols[1]);
            if(cols[0].equalsIgnoreCase("Actuator")){
                //System.out.println("Entrou");
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

    public void instantiateModuleActuators(String[] cols) throws NoSuchMethodException, ClassNotFoundException {
        String classe=cols[1].concat(cols[2]);
        classe=classe.replaceAll("\\s+","");

        AbstractActuator newAct = null;
        try {
            newAct = DynamicReflectionActuatorFactory.getActuator(classe);
            // Talvez ter 1 campo em que diga o tipo em AbstractActuator
            // OU
            // Talvez


            //Get All public fields
            /*Field[] publicFields = Class.forName("Actuators.Lamp.LampBosch").getFields();
            //prints public fields of ConcreteClass, it's superclass and super interfaces
            System.out.println(Arrays.toString(publicFields));*/

            //Still misses the arguments part in constructor

            actuatorList.add(newAct);

            System.out.format("Actuator %s was instantiate%n", classe);
            System.out.println("Number of Actuators: " + getNumberOfActuators() + "\n");

            //getActuatorsList();

        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NullPointerException e) {
            System.err.format("This Actuator brand doesn't have a plugin\n");
            // Are we suppose to instantiate a base class (like lamp)???
            //Actuators.DynamicReflectionActuatorFactory.registerType(classe, className.class());
        }

        // Irá vir das regras
        if(newAct instanceof LampBosch){
            ((LampBosch) newAct).setIlumination(String.valueOf(cols[3]));
        }



    }

    public void instantiateModuleSensor(String[] cols){
        String classe=cols[1].concat(cols[2]);
        classe=classe.replaceAll("\\s+","");

        try {
            AbstractSensor newSensor= DynamicReflectionSensorFactory.getSensor(classe);

            sensorList.add(newSensor);

            System.out.format("Sensor %s was instantiate%n", classe);
            System.out.println("Number of Sensors: " + getNumberOfSensors() + "\n");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            System.err.format("This Sensor brand doesn't have a plugin\n");
            // Are we suppose to instantiate a base class (like lamp)???
        }


        // Irá vir das regras. For now is the only option
        /*if(cols[3]!=null){
            newSensor = new HumidityBosch();
        }
        else{
            newSensor = new HumidityBosch(cols[3]);
        }*/

    }

    public Integer getNumberOfActuators(){
        return actuatorList.size();
    }

    public Integer getNumberOfSensors(){
        return sensorList.size();
    }

    public void getActuatorsList(){
        Iterator it =actuatorList.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }
}
