package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPListener implements Runnable {

    private final BufferedReader in;
    private final Socket socket;

    public TCPListener(Socket socket) throws IOException {
        this.socket = socket;
        this.in = setupIn(socket);
    }


    @Override
    public void run() {

        while (true) {

            try {
                String response = in.readLine();

                // in case error is detected then close socket and exit listening loop
                if (hasError(response)) break;

                System.out.println(response);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean hasError(String response) throws IOException {
        if (response == null) {
            socket.close();
            return true;
        }
        return false;
    }

    private BufferedReader setupIn(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

}
