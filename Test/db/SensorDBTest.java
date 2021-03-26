package db;

import exceptions.IllegalOperationException;
import exceptions.NoDBAccessException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sensor.SensorData;
import sensor.SensorDataImpl;

public class SensorDBTest {
    SensorData sdFloyd1;
    SensorData sdFloyd2;
    SensorData sdFloyd3;
    SensorData sdHugo1;
    SensorData sdHugo2;
    SensorData sdHugo3;

    @Before
    public void setFloydAndHugo(){
        sdFloyd1 = new SensorDataImpl(System.currentTimeMillis(), 20,"Floyd");
        sdFloyd2 = new SensorDataImpl(System.currentTimeMillis(), 10,"Floyd");
        sdFloyd3 = new SensorDataImpl(System.currentTimeMillis(), 30,"Floyd");
        sdHugo1 = new SensorDataImpl(System.currentTimeMillis(), 0,"Hugo");
        sdHugo2 = new SensorDataImpl(System.currentTimeMillis(), 40,"Hugo");
        sdHugo3 = new SensorDataImpl(System.currentTimeMillis(), -10,"Hugo");
    }

    private static SensorDB getSensorDB(){
        return new SensorDBImpl();
    }


    @Test
    public void saveCountSeveralSensorGood() throws Exception{
        SensorDB sensorDB = getSensorDB();

        sensorDB.save(sdFloyd1);
        sensorDB.save(sdFloyd2);
        sensorDB.save(sdHugo1);
        sensorDB.save(sdHugo2);
        sensorDB.save(sdHugo3);
        int floydCount = sensorDB.count("Floyd");
        int hugoCount = sensorDB.count("Hugo");
        Assert.assertEquals(2, floydCount);
        Assert.assertEquals(3, hugoCount);
    }

    @Test
    public void countNoSensorEdge() throws Exception{
        SensorDB sensorDB = getSensorDB();
        int floydCount = sensorDB.count("Floyd");

        Assert.assertEquals(0, floydCount);
    }

    @Test
    public void countAllGood() throws Exception{
        SensorDB sensorDB = getSensorDB();

        sensorDB.save(sdFloyd1);
        sensorDB.save(sdFloyd2);
        sensorDB.save(sdHugo1);
        sensorDB.save(sdHugo2);
        sensorDB.save(sdHugo3);

        int expected = 5;
        int actual = sensorDB.countAll();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void countAllEdge() throws Exception{
        SensorDB sensorDB = getSensorDB();

        int actual = sensorDB.countAll();

        Assert.assertEquals(0, actual);
    }

    @Test
    public void totalDataGood() throws NoDBAccessException{
        SensorDB sensorDB = getSensorDB();

        sensorDB.save(sdFloyd1);
        sensorDB.save(sdFloyd2);
        sensorDB.save(sdFloyd3);
        sensorDB.save(sdHugo1);
        sensorDB.save(sdHugo2);
        sensorDB.save(sdHugo3);

        float expectedTotal = sdFloyd1.getData() + sdFloyd2.getData() + sdFloyd3.getData()
                + sdHugo1.getData() + sdHugo2.getData() + sdHugo3.getData();

        float actualTotal = sensorDB.getTotal();

        Assert.assertEquals(expectedTotal, actualTotal, 0.1);
    }

    @Test
    public void totalDataEdge() throws NoDBAccessException {
        SensorDB sensorDB = getSensorDB();

        float actualTotal = sensorDB.getTotal();
        Assert.assertEquals(0, actualTotal, 0.1);
    }

    @Test
    public void averageAndCountAllDataGood() throws NoDBAccessException, IllegalOperationException {
        SensorDB sensorDB = getSensorDB();

        sensorDB.save(sdFloyd1);
        sensorDB.save(sdFloyd2);
        sensorDB.save(sdFloyd3);
        sensorDB.save(sdHugo1);
        sensorDB.save(sdHugo2);
        sensorDB.save(sdHugo3);

        float expectedTotal = sdFloyd1.getData() + sdFloyd2.getData() + sdFloyd3.getData()
                + sdHugo1.getData() + sdHugo2.getData() + sdHugo3.getData();

        float expectedAverage = expectedTotal / sensorDB.countAll();
        float actualAverage = sensorDB.getAverageAll();

        Assert.assertEquals(expectedAverage, actualAverage, 0.1);
    }

    @Test (expected = IllegalOperationException.class)
    public void getAverageAllDataBad() throws Exception{
        SensorDB sensorDB = getSensorDB();

        sensorDB.getAverageAll();
    }

    @Test
    public void getAverageSensorGood() throws Exception{
        SensorDB sensorDB = getSensorDB();

        sensorDB.save(sdFloyd1);
        sensorDB.save(sdFloyd2);
        sensorDB.save(sdFloyd3);
        sensorDB.save(sdHugo1);
        sensorDB.save(sdHugo2);
        sensorDB.save(sdHugo3);

        float expectedTotalFloyd = sdFloyd1.getData() + sdFloyd2.getData() + sdFloyd3.getData();
        int expectedCountFloyd = 3;
        float expectedAverageFloyd = expectedTotalFloyd / expectedCountFloyd;

        float actualAverageFloyd = sensorDB.getAverage("Floyd");

        Assert.assertEquals(expectedAverageFloyd, actualAverageFloyd, 0.1);

        float expectedTotalHugo =  sdHugo1.getData() + sdHugo2.getData() + sdHugo3.getData();
        int expectedCountHugo = 3;
        float expectedAverageHugo = expectedTotalHugo / expectedCountFloyd;

        float actualAverageHugo = sensorDB.getAverage("Hugo");

        Assert.assertEquals(expectedAverageHugo, actualAverageHugo, 0.1);
    }

    @Test (expected = IllegalOperationException.class)
    public void getAverageBad() throws Exception{
        SensorDB sensorDB = getSensorDB();

        sensorDB.getAverage("unknown");
    }

}