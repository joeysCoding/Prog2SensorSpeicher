package sensor;

public interface SensorData {
    /**
     * Get the bytes representing this sensor data
     * Use to send to peer
     * @return byte array representing a sensor date
     * @throws Exception error while converting sensor time, data, name to byte array
     */
    byte[] getBytes() throws Exception;

    /**
     * Get the sensor time
     * @return time the sensor data was taken
     */
    long getTime();

    /**
     * Get the senor data
     * @return data of sensor
     */
    float getData();

    /**
     * Get the sensor name
     * @return name of sensor
     */
    String getName();
}
