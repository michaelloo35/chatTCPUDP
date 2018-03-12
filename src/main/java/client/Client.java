package client;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Client {

    private final String host = "localhost";
    private final int PORT_NUMBER = 12345;

    public Client() {
    }


    public void start() throws IOException {

        System.out.println("JAVA TCP CLIENT");

        try (Socket socket = new Socket(host, PORT_NUMBER)) {

            // in & out
            new Thread(new TCPListenerThread(socket)).start();
            TCPWriter writer = new TCPWriter(socket);

            // send msg, read response
            while (true) {
                // exit loop if error occured and socket is closed
                writer.writeFromSTDIN();
            }
        }
        catch(SocketException e){
            System.out.println("Error, disconnected");
        }

    }


}
