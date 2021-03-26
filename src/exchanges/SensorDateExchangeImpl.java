package exchanges;

import exceptions.NoSensorDataException;
import sensor.SensorData;
import sensor.SensorDataImpl;

import java.io.*;

public class SensorDateExchangeImpl extends ExchangeImpl<SensorData, SensorData> {
    public SensorDateExchangeImpl(int port) throws IOException {
        super(port);
    }

    public SensorDateExchangeImpl(String host, int port) throws IOException {
        super(host, port);
    }

    @Override
    public void send(SensorData data) throws IOException {
        try {
            OutputStream os = super.peer.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeLong(data.getTime());
            dos.writeFloat(data.getData());
            dos.writeUTF(data.getName());
            dos.flush();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IOException("Couldn't access peer output Stream!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Couldn't access peer output Stream!");
        }
    }

    @Override
    public SensorData receive() throws IOException, NoSensorDataException {
        try {
            InputStream is = super.peer.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            if (dis.available() < 1)
                throw new NoSensorDataException("Peer has not send new sensordata, use sensorDataAvailable() before this method");
            Long time = dis.readLong();
            float data = dis.readFloat();
            String name = dis.readUTF();

            SensorData sensor = new SensorDataImpl(time, data, name);
            return sensor;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IOException("Couldn't get peer Input Stream!");
        }
    }

    @Override
    public boolean sensorDataAvailable() throws IOException {
        try {
            InputStream is = super.peer.getInputStream();
            return is.available() > 0;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IOException("couldn't access peer input stream while checking whether more data is available!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("couldn't access peer input stream while checking whether more data is available!");
        }
    }
}
