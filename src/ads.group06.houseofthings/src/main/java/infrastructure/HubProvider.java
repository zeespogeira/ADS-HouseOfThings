package infrastructure;

public class HubProvider {
    private static SensorReadingsHub sensorHub;
    //SensorReadingsHub sensorHub;

    //private static HubProvider instance;

    public static SensorReadingsHub getReadingHub(){
        if(sensorHub==null){
            sensorHub=new SensorReadingsHub();
        }
        return sensorHub;
    }
}
