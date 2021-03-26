package db;

import exceptions.IllegalOperationException;
import exceptions.NoDBAccessException;
import sensor.SensorData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SensorDBImpl implements SensorDB {
    private Map<String, List<SensorData>> db;

    public SensorDBImpl(){
        db = new HashMap<>();
    }

    @Override
    public void save(SensorData sd) throws NoDBAccessException {
        List<SensorData> sdList = db.containsKey(sd.getName())? db.get(sd.getName()): new LinkedList<>();
        sdList.add(sd);
        db.put(sd.getName(), sdList);
    }

    @Override
    public int count(String name) throws NoDBAccessException {
        return db.containsKey(name)? db.get(name).size(): 0;
    }

    @Override
    public float getTotal() throws NoDBAccessException {
        int total = 0;
        for (String name: db.keySet()){
            total += getMeasurementSum(name);
        }
        return total;
    }

    @Override
    public int countAll() throws NoDBAccessException{
        int count = 0;
        for (String curName: db.keySet()){
            count += count(curName);
        }
        return count;
    }

    @Override
    public float getAverageAll() throws NoDBAccessException, IllegalOperationException {
        if (db.isEmpty())
            throw new IllegalOperationException("There are no elements in the sensor db. + " +
                    " Average of zero elements not defined: O/0");
        return getTotal() / countAll();
    }

    @Override
    public float getAverage(String name) throws NoDBAccessException, IllegalOperationException {
        if (db.isEmpty() || !db.containsKey(name))
            throw new IllegalOperationException("There are no elements in the sensor db. + " +
                    " Average of zero elements not defined: O/0");
        float sum = getMeasurementSum(name);
        int numMeasurements = count(name);
        return sum / numMeasurements;
    }

    private float getMeasurementSum(String name){
        if (!db.containsKey(name)) return 0;

        List <SensorData> sensors = db.get(name);
        float sum = 0;
        for (SensorData curSD: sensors){
            sum += curSD.getData();
        }
        return sum;
    }
}
