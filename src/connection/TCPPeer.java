package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * use as communication endpoint for TCP server or client
 */
public interface TCPPeer {
    /**
     * use before first communication with peer
     * connects to peer (server or client/
     */
    void connect() throws IOException;

    /**
     * use after last communication with peer
     * closes connection to  with client/server
     */
    void close() throws IOException;

    /**
     * use this is to receive data from TCP Peer
     * @return InputStream of TCP peer
     * @throws IllegalAccessException
     * - connection to peer has to be established by calling connect() first
     * @throws IOException couldn't get InputStream from socket, even so connect() was called correctly
     */
    InputStream getInputStream() throws IllegalAccessException, IOException;

    /**
     * use this os to send data to TCP peer
     * @return OutputStream of TCP peer
     * @throws IllegalAccessException
     * - connection to peer has to be established by calling connect() first
     * @throws IOException couldn't get Outputstream for socket, even so connect() was called correctly
     */
    OutputStream getOutputStream() throws IllegalAccessException, IOException;
}
