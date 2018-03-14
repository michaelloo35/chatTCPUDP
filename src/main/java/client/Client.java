package client;

import client.TCP.TCPListener;
import client.TCP.TCPWriter;
import client.UDP.MulticastWriter;
import client.UDP.UDPListener;
import client.UDP.UDPWriter;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {

    private final String SERVER_ADDRESS = "localhost";
    private final int SERVER_PORT = 12345;
    private final int MULTICAST_PORT = 12346;
    private final String MULTICAST_ADDRESS = "239.0.0.21";
    private final Scanner scanner;

    public Client() {
        scanner = new Scanner(System.in);
    }


    public void start() throws IOException {

        System.out.println("JAVA TCP CLIENT");

        try (Socket tcpSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             DatagramSocket datagramSocket = new DatagramSocket(tcpSocket.getLocalPort());
             MulticastSocket multicastSocket = new MulticastSocket(MULTICAST_PORT)) {

            TCPWriter tcpWriter = setupTCP(tcpSocket);

            // UDP in & out setup
            UDPWriter udpWriter = setupUDP(datagramSocket);

            // Multicast setup
            multicastSocket.joinGroup(InetAddress.getByName(MULTICAST_ADDRESS));
            new Thread(new UDPListener(multicastSocket)).start();
            MulticastWriter multicastWriter = new MulticastWriter(multicastSocket);


            // MESSAGE LOOP
            while (true) {

                System.out.println("Choose message type:\n'T' - TCP\n'U' - UDP ASCII art\n'M' - Multicast ASCII art");

                switch (scanner.nextLine()) {
                    case "T":
                        tcpWriter.sendMessageFromSTDIN();
                        break;

                    case "U":
                        udpWriter.sendRandomArt(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
                        break;

                    case "M":
                        multicastWriter.sendRandomArt(InetAddress.getByName(MULTICAST_ADDRESS));
                        break;

                    default:
                        System.out.println("Incorrect type");
                }
            }

        } catch (SocketException e) {
            System.out.println("Error, disconnected");
        }

    }

    private UDPWriter setupUDP(DatagramSocket datagramSocket) {
        new Thread(new UDPListener(datagramSocket)).start();
        return new UDPWriter(datagramSocket);
    }

    private TCPWriter setupTCP(Socket tcpSocket) throws IOException {
        new Thread(new TCPListener(tcpSocket)).start();
        return new TCPWriter(tcpSocket);
    }


}
