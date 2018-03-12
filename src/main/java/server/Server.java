package server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {


    private static final int PORT_NUMBER = 12345;
    private static final int MAX_CLIENTS = 0;
    private static final String CLIENTS_LIMIT_ERROR_MSG = "clients limit exceeded";

    private final List<ClientService> clients;
    private int clientCounter = 0;

    public Server() {
        clients = new ArrayList<>();
    }


    public void start() {

        System.out.println("JAVA TCP SERVER");

        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
             DatagramSocket datagramSocket = new DatagramSocket(PORT_NUMBER)) {

            while (true) {

                ClientService cs = new ClientService(serverSocket.accept(), clients, clientCounter);


                if (clientCounter < MAX_CLIENTS) {

                    clientCounter++;
                    new Thread(cs).start();

                    clients.add(cs);
                    System.out.println("client: " + clientCounter + " connected");

                } else {
                    System.out.println(CLIENTS_LIMIT_ERROR_MSG);
                    cs.closeSocket();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
