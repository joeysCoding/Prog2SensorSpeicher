package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements TCPPeer{
    ServerSocket serverSocket;
    Socket socket;

    /**
     * Host TCP Server at port ...
     * @param port
     */
    public TCPServer(int port) throws IOException {
        try{
            serverSocket = new ServerSocket(port);
        } catch (IOException e){
            String errMsg = "Couldn't open ServerSocket at port + " + port + " Message: " + e.getMessage();
            System.err.println(errMsg);
            throw new IOException(errMsg);
        }
    }

    @Override
    public void connect() throws IOException{
        if (serverSocket == null)
            throw new IOException("Serversocket not set; is null!!!");
        socket = serverSocket.accept();
    }

    @Override
    public void close() throws IOException{
        try{
            socket.close();
            serverSocket.close();
        } catch (IOException e){
            System.err.println(e.getMessage());
            throw new IOException("Couldn't close socket.");
        }
    }

    @Override
    public InputStream getInputStream() throws IllegalAccessException, IOException {
        if (socket == null || socket.isClosed())
            throw new IllegalAccessException("Server is not connected to client; socket is null. " +
                    "use connect() before getting the inputStream");
        try{
            return socket.getInputStream();
        } catch (IOException e){
            System.err.println(e.getMessage());
            throw new IOException("Couldn't get InputStream from socket");
        }
    }

    @Override
    public OutputStream getOutputStream() throws IllegalAccessException, IOException {
        if (socket == null || socket.isClosed())
            throw new IllegalAccessException("Server is not connected to client; socket is null. " +
                    "use connect() before getting the OutputStream");
        try{
            return socket.getOutputStream();
        } catch (IOException e){
            System.err.println(e.getMessage());
            throw new IOException("Couldn't get InputStream from socket");
        }
    }
}
