import java.io.*;
import java.nio.file.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class DiscoveryModule {
    Integer numberOfModulesConnected;

    /*private final WatchService watcher;
    private final Path dir;*/
    private final String currentPath;

    /*
     * Creates a WatchService and registers the given directory
     */
    DiscoveryModule() throws IOException {

        File directory = new File("./"); //  ./../../../Devices
        directory.getParent();directory.getParent();directory.getParent();
        currentPath=directory.getCanonicalPath() + "\\Devices";
        //currentPath="\\Devices";

    }

    public void loadFiles() throws IOException {
        List files;
        files = Files.walk(Paths.get(currentPath))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
/*
        Iterator it =files.iterator();
        while (it.hasNext()){
            readCSV(it.next());
            it.next();
        }
*/
        files.forEach((temp) -> {
            System.out.println(temp);
            //File file=File.

            try {
                readCSV((File) temp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void readCSV(File next) throws IOException {
        //ver os ficheiros
        // inicializar as classes adicionadas ao ficheiro
           /* Aquecedor aquecedor=new AquecerdorBosch();
            aquecedor.setTemperatura(20);*/
        // inicializar as classes adicionadas ao ficheiro
            //Aquecedor aquecedor=new AquecerdorBosch(temperatura);

        //Need to check and instantiate the file already created


        BufferedReader br = new BufferedReader(new FileReader(next));
        String line;
        while ((line = br.readLine()) != null) {
            // use comma as separator
            String[] cols = line.split(",");
            System.out.println("Coulmn 4= " + cols[0] + " , Column 5=" + cols[1]);
        }

        // CSV Format:
            // Actuator/Sensor, Brand
    }
    public void instantiateModule(){}

}
