package sensor;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SensorDataImpl implements SensorData {
    long time;          // time the sensor data was measured in milliseconds
    float data;         // measurement read from sensor at time
    String name;        // name of the sensor

    public SensorDataImpl(long time, float data, String name) {
        this.time = time;
        this.data = data;
        this.name = name;
    }

    /**
     * use to initialize new Sensor Data instance with bytes representing sensor data time, data, name
     * @param sensorData the byte representation of a sensor data
     * @throws Exception Can't read time, data or name from sensor data bytes, check, if protocol
     */
    public SensorDataImpl(byte[] sensorData) throws Exception {
        setData(sensorData);
    }

    @Override
    public byte[] getBytes() throws Exception {
        return convertToBytes(time, data, name);
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public float getData() {
        return data;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(time);
        sb.append(" ");
        sb.append(data);
        sb.append(" ");
        sb.append(name);
        return sb.toString();
    }

    private byte[] convertToBytes(long time, float data, String name) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeLong(time);
            dos.writeFloat(data);
            // todo: check if write UTF is better, but I remember a problem
            // dos.write(name.getBytes(StandardCharsets.UTF_8));
            dos.writeUTF(name);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("couldn't convert time, data, name to byte array!");
        }

        return baos.toByteArray();
    }

    private void setData(byte[] dataBytes) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(dataBytes);
        DataInputStream dis = new DataInputStream(bais);

        try {
            time = dis.readLong();
            data = dis.readFloat();
            name = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error while converting sensor byte array to time, data, name!");
        }
    }
}
