package exchanges;

import connection.TCPClient;
import connection.TCPPeer;
import connection.TCPServer;
import exceptions.NoSensorDataException;

import java.io.IOException;

// only generic classes can implement generic interfaces
// https://javaconceptoftheday.com/generic-interfaces-java/
public abstract class ExchangeImpl<T, V> implements Exchange<T, V> {
    TCPPeer peer;

    public ExchangeImpl(int port) throws IOException {
        try {
            peer = new TCPServer(port);
        } catch (IOException e){
            System.err.println(e.getMessage());
            throw new IOException("Can't create new Server Peer for exchange");
        }
        peer.connect();
    }

    public ExchangeImpl(String host, int port) throws IOException{
        peer = new TCPClient(host, port);
        try {
            peer.connect();
        } catch (IOException e){
            System.err.println(e.getMessage());
            throw new IOException("Couldn't connect to client peer for exchange!");
        }
    }

    public abstract void send(T data) throws IOException;

    public abstract V receive() throws IOException, NoSensorDataException;
}
