package client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {

    private final String SERVER_ADDRESS = "localhost";
    private final int SERVER_PORT = 12345;
    private final Scanner scanner;

    public Client() {
        scanner = new Scanner(System.in);
    }


    public void start() throws IOException {

        System.out.println("JAVA TCP CLIENT");

        try (Socket tcpSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             DatagramSocket datagramSocket = new DatagramSocket(tcpSocket.getLocalPort())) {

            // TCP in & out setup
            new Thread(new TCPListener(tcpSocket)).start();
            TCPWriter tcpWriter = new TCPWriter(tcpSocket);

            // UDP in & out setup
            new Thread(new UDPListener(datagramSocket)).start();
            UDPWriter udpWriter = new UDPWriter(datagramSocket);

            // MESSAGE LOOP
            while (true) {

                System.out.println("Choose message type:\n'T' - TCP\n'U' - UDP ASCII art");

                switch (scanner.nextLine()) {
                    case "T":
                        tcpWriter.sendMessageFromSTDIN();
                        break;

                    case "U":
                        udpWriter.sendRandomArt(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
                        break;

                    default:
                        System.out.println("Incorrect type");
                }
            }

        } catch (SocketException e) {
            System.out.println("Error, disconnected");
        }

    }


}
