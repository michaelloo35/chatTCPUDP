package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class ClientService implements Runnable {

    private final Socket socket;
    private final List<ClientService> clients;
    private final PrintWriter out;
    private final BufferedReader in;
    private final int id;

    public ClientService(Socket socket, List<ClientService> clients, int id) throws IOException {
        this.socket = socket;
        this.clients = clients;
        this.id = id;

        // in & out streams
        this.out = setupOut();
        this.in = setupIn();

    }

    @Override
    public void run() {
        try {

            while (true) {

                String msg = in.readLine();

                // break connection if error is detected
                if (msg == null)
                    break;

                msg = "client" + id + " says " + msg;

                broadcastMessage(msg);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void broadcastMessage(String message) {

        synchronized (clients) {
            for (ClientService c : clients) {

                // don't broadcast to yourself
                if (c.id != this.id)
                    c.getOut().println(message);
            }
        }
    }

    public void closeSocket() throws IOException {
        this.socket.close();
    }

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public int getPort() {
        return socket.getPort();
    }

    public InetAddress getAddress() {
        return socket.getInetAddress();
    }

    private BufferedReader setupIn() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private PrintWriter setupOut() throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }


}
