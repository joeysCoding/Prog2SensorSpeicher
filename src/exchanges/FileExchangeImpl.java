package exchanges;

import java.io.*;


public class FileExchangeImpl extends ExchangeImpl<File, File>{
    File dirTmpFiles = new File("./tmpFiles");
    File tmpFile = File.createTempFile("exchanges", ".tmp", dirTmpFiles);

    public FileExchangeImpl(int port) throws IOException {
        super(port);
    }

    public FileExchangeImpl(String host, int port) throws IOException {
        super(host, port);
    }

    @Override
    public void send(File data) throws IOException{
        try {
            OutputStream os = super.peer.getOutputStream();
            os.write(getBytes(data));
            os.flush();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IOException("File couldn't be send!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("File couldn't be send!");
        }
    }


    @Override
    public File receive() throws IOException{
        try {
            InputStream is = super.peer.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while(is.available() == 0){
                Thread.sleep(50);
            }
            // is.readAllBytes() doesn't work here because it would stop the thread,
            // waiting for more bytes, that will never get send, tcp is does not send eof
            int remainingBytes;
            while((remainingBytes = is.available()) > 0){
                baos.write(is.readNBytes(remainingBytes));
            }
            OutputStream os = new FileOutputStream(tmpFile);
            os.write(baos.toByteArray());
            os.flush();
            return tmpFile;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IOException("couldn't receive file");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("couldn't receive file");
        } catch (Exception e){
            e.printStackTrace();
            throw new IOException("couldn't receive file");
        }
    }

    @Override
    public boolean sensorDataAvailable() throws IOException {
        return false;
    }

    private byte[] getBytes(File file) throws IOException {
        try {
            InputStream is = new FileInputStream(file);
            // works here because FileInputStream is finite, has eof
            return is.readAllBytes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IOException("Couldn't read file!");
        }

    }
}
