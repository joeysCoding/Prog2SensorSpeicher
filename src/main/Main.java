package main;

import exceptions.NoSensorDataException;
import exchanges.Exchange;
import exchanges.SensorDateExchangeImpl;
import exchanges.FileExchangeImpl;
import sensor.SensorData;

import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception{
        
    }

    private static void receiveSendMultipleSensorData() throws IOException, InterruptedException, NoSensorDataException {
        //        testExchangeFile();
        // be Server; receive Sensor Data print it and return it to client
        System.out.println("Receiving sensor");
        Exchange<SensorData, SensorData> exchange = new SensorDateExchangeImpl(3333);
        while(!exchange.sensorDataAvailable()){
            Thread.sleep(50);
        }
        SensorData sensorReceived1 = exchange.receive();

        System.out.println(sensorReceived1.toString());
        System.out.println("Reading second data ");
        while(!exchange.sensorDataAvailable()){
            Thread.sleep(50);
        }
        SensorData sensorReceived2 = exchange.receive();

        System.out.println(sensorReceived2.toString());

        System.out.println();
        System.out.println("Sending first sensor back to client...");
        exchange.send(sensorReceived1);

        System.out.println("Sending second sensor back to client...");
        exchange.send(sensorReceived2);
    }

    private static void testExchangeFile() throws IOException, NoSensorDataException {
        Exchange<File, File> exchange = new FileExchangeImpl(3333);
        File file = exchange.receive();

        System.out.println("File received! Content:");

        InputStream is = new FileInputStream(file);

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line = br.readLine();
        StringBuilder sb = new StringBuilder();

        while(line != null){
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }

        System.out.println(sb.toString());

        exchange.send(file);
        System.out.println("File send back");
    }
}
