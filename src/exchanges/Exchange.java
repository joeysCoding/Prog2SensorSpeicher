package exchanges;

import exceptions.NoSensorDataException;

import java.io.IOException;

/**
 * use to exchange data with a peer
 */
public interface Exchange<T, V> {
    /**
     * send data to peer
     * @throws IOException if data couldn't be send
     */
    void send(T data) throws IOException;

    /**
     * receive data from peer
     * @throws IOException if data couldn't be received
     * @throws NoSensorDataException there is no data in the stream,
     * check with sensorDataAvailable() [has to be true] before receiving
     */
    V receive() throws IOException, NoSensorDataException;

    /**
     * check whether peer has send more sensor data
     * @return
     * - true: there is more sensor data from peer
     * - false: peer has not send new sensor data
     */
    boolean sensorDataAvailable() throws IOException;
}
