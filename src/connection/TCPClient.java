package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient implements TCPPeer{
    String host;
    int port;
    Socket socket;

    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    @Override
    public void connect() throws IOException {
        try{
            socket = new Socket(host, port);
        } catch (IOException e){
            System.err.println(e.getMessage());
            throw new IOException("Couldn't create client socket.");
        }
    }

    @Override
    public void close() throws IOException{
        try{
            socket.close();
        } catch (IOException e){
            System.err.println(e.getMessage());
            throw new IOException("Couldn't close client socket");
        }
    }

    @Override
    public InputStream getInputStream() throws IllegalAccessException, IOException {
        if(socket == null || socket.isClosed())
            throw new IllegalAccessException("Client socket is null or closed. Call connect() before");
        try{
            return socket.getInputStream();
        } catch (IOException e){
            System.err.println(e.getMessage());
            throw new IOException("Couldn't get InputStraem from client socket!");
        }
    }

    @Override
    public OutputStream getOutputStream() throws IllegalAccessException, IOException {
        if(socket == null || socket.isClosed())
            throw new IllegalAccessException("Client socket is null or closed. Call connect() before");
        try{
            return socket.getOutputStream();
        } catch (IOException e){
            System.err.println(e.getMessage());
            throw new IOException("Couldn't get OutputStream from client socket!");
        }
    }
}
