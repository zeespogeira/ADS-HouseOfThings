import Models.AbstractActuator;
import Models.AbstractSensor;

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
    Integer numberOfModulesConnected;

    private final String currentPath;
    private final WatchService watcher;
    private final Path dir;

    //ArrayList<AbstractActuator> actuatorList;
    List<AbstractActuator> actuatorList = Collections.synchronizedList(new ArrayList<AbstractActuator>());
    List<AbstractSensor> sensorList= Collections.synchronizedList(new ArrayList<AbstractSensor>());;

    /**
     * Creates a WatchService and registers the given directory
     */
    DiscoveryModule(List<AbstractActuator> actuatorList, List<AbstractSensor> sensorList) throws IOException {

        this.actuatorList = actuatorList;
        this.sensorList=sensorList;

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

                //Is a CSV file. Need to instantiate class
                //System.out.format("Instantiate Class from file %s%n", filename);

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
        String classe=cols[1].concat(cols[2]);
        classe=classe.replaceAll("\\s+","");

        Class<?> factoryClsImpl = null;
        AbstractActuator obj = null;
        try {
            factoryClsImpl = Class.forName("Actuators." + cols[1].replaceAll("\\s+","") + "." +  classe);
            obj = (AbstractActuator) factoryClsImpl.newInstance();
            obj.setName(classe);
            actuatorList.add(obj);
            //System.out.println(obj);
        } catch (ClassNotFoundException e) {
            System.err.format("This Actuator brand doesn't have a plugin\n");
            return;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }


        /*
        // Desta forma tenho de por todos os sub-metodos especificos de uma classe aqui -> NAO E PERMANENTE
        Class par=java.lang.String.class;
        Method methodToInsertValue = null;
        String[] specificMethods={"setIlumination"};

        try {
            for (String method2:specificMethods
            ) {
                methodToInsertValue = factoryClsImpl.getMethod(method2,par);
                if(methodToInsertValue!=null){
                    break;
                }
            }
        } catch (NoSuchMethodException  e) { //To search in the superClass
            try {
                for (String methodS:specificMethods
                ) {
                    String variableValue=methodS.replaceAll("set","").toLowerCase();
                    //Class pal= factoryClsImpl.getSuperclass().getField(variableValue).getType();
                    //method2 = factoryImpl.getSuperclass().getMethod(methodS, par);
                    methodToInsertValue = factoryClsImpl.getSuperclass().getMethod(methodS, par);
                    if(methodToInsertValue!=null){
                        break;
                    }
                }
            } catch (NoSuchMethodException ex) {
                //Poderia passar isto a frente (Just information to test)
                //System.err.format("This Actuator doesn't have this option\n");
            }
            //System.err.format("This Sensor doesn't have this option\n");
        }
       // System.out.println(method2);

        synchronized (actuatorList){
            try {
                //Vai ser aleatorio
                String input2=cols[3]; //if doesn't have the value, it's caught in the exception "ArrayIndexOutOfBoundsException"

                // If it's different there is a actuator instantiated
                if (obj != null) {
                    AbstractActuator es = (AbstractActuator)methodToInsertValue.invoke(obj, input2);
                    //System.out.println(obj.toString());
                    actuatorList.add(obj);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            catch (ArrayIndexOutOfBoundsException | NullPointerException ex){
                // If catches this error (there isn't any more arguments in cols) there isn't a field to instantiate
                actuatorList.add(obj);
                //System.out.println(obj.toString());
            }
        }*/
    }

    public synchronized void instantiateModuleSensor(String[] cols){
        String classe=cols[1].concat(cols[2]);
        classe=classe.replaceAll("\\s+","");

        Class<?> factoryImpl = null;
        AbstractSensor obj = null;
        try {
            //Tambem teria de por todos os sensors no mesmo package
            factoryImpl = Class.forName("Sensors." + cols[1].replaceAll("\\s+","") + "." + classe);
            obj = (AbstractSensor) factoryImpl.newInstance();
            obj.setName(classe);
            obj.setWhatIsMeasuring(cols[3]);
            sensorList.add(obj);
            //System.out.println(factoryImpl);
        } catch (ClassNotFoundException e) {
            System.err.format("This Sensor brand doesn't have a plugin\n");
            return;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }


        /*
        // Desta forma tenho de por todos os sub-metodos especificos de uma classe aqui -> NAO E PERMANENTE
        // if there is arguments in file
        synchronized (sensorList) {
            if(cols.length>3) {
            Method methodToInsertValue = null;
            String[] specificMethods = {"setHumidity", "setTemperature", "setRealFeel"};
            //System.out.println("specificMethods: "+ specificMethods.length);

            try {
                for (String method2 : specificMethods
                ) {
                    methodToInsertValue = factoryImpl.getMethod(method2, java.lang.String.class);
                    //System.out.println("method2"+ method2);System.out.println("method: "+ method + "\n");
                    if (methodToInsertValue != null) {
                        break;
                    }
                }
            } catch (NoSuchMethodException e) {

                try {

                    for (String methodS : specificMethods
                    ) {
                        try {
                            String variableValue = methodS.replaceAll("set", "").toLowerCase();
                            //System.out.println("**********test: "+variableValue);
                            //System.out.println("field: "+ factoryImpl.getSuperclass().getField(variableValue));

                            Class pal = factoryImpl.getSuperclass().getField(variableValue).getType();
                            //method2 = factoryImpl.getSuperclass().getMethod(methodS, par);
                            methodToInsertValue = factoryImpl.getSuperclass().getMethod(methodS, java.lang.String.class);
                            if (methodToInsertValue != null) {
                                break;
                            }
                        } catch (NoSuchFieldException ex) {
                        }
                    }
                } catch (NoSuchMethodException ex) {
                    //Poderia passar isto a frente (Just information to test)
                    //System.err.format("This Sensor doesn't have this option\n");
                }
                //System.err.format("This Sensor doesn't have this option\n");
            }
                try {
                    //Pode vir das regras
                    String input2 = cols[3]; //if doesn't have the value, it's caught in the exception "ArrayIndexOutOfBoundsException"

                    // If it's different there is an empty actuator instantiated
                    if (obj != null) {
                        AbstractSensor es = (AbstractSensor) methodToInsertValue.invoke(obj, input2);
                        //System.out.println(obj.toString());
                        //obj.setName(classe);
                        sensorList.add(obj);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException ex) {
                    // If catches this error (there isn't any more arguments in cols) there isn't a field to instantiate
                    //System.out.println(obj.toString());
                    sensorList.add(obj);

                }

            }else //same as catch ArrayIndexOutOfBoundsException
            {
                sensorList.add(obj);
                System.out.println(obj.toString());
            }
        }*/
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
