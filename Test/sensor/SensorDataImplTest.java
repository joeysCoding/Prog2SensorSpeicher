package sensor;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SensorDataImplTest {
    @Test
    public void sensorDataToBytesAndBack() throws Exception {
        long time = System.currentTimeMillis();
        float data = 2.1f;
        String name = "honorable sensor";

        SensorData original = new SensorDataImpl(time, data, name);

        byte[] originalBytes = original.getBytes();

        SensorData fromBytes = new SensorDataImpl(originalBytes);

        long timeActual = fromBytes.getTime();
        float dataActual = fromBytes.getData();
        String nameActual = fromBytes.getName();

        Assert.assertEquals(time, timeActual);
        Assert.assertEquals(data, dataActual, 0.1);
        Assert.assertEquals(name, nameActual);
    }
}