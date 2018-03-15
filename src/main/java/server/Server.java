package server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {


    private static final int PORT_NUMBER = 12345;
    private static final int MAX_CLIENTS = 2;
    private static final String CLIENTS_LIMIT_ERROR_MSG = "clients limit exceeded";
    public static final int THREAD_COUNT = 5;

    private final List<ClientService> clients;
    private int clientCounter = 0;

    public Server() {
        clients = new ArrayList<>();
    }


    public void start() {

        System.out.println("JAVA SERVER");

        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
             DatagramSocket datagramSocket = new DatagramSocket(PORT_NUMBER)) {

            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

            startUDPService(datagramSocket);

            while (true) {

                ClientService cs = new ClientService(serverSocket.accept(), clients, clientCounter + 1);

                if (clientCounter < MAX_CLIENTS) {

                    executorService.submit(cs);

                    clientCounter++;
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

    private void startUDPService(DatagramSocket datagramSocket) {
        UDPService udpService = new UDPService(datagramSocket, clients);
        new Thread(udpService).start();
    }

}
