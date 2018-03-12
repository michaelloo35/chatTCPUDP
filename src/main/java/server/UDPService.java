package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

public class UDPService implements Runnable {

    private final List<ClientService> clients;
    private final DatagramSocket socket;

    public UDPService(DatagramSocket socket, List<ClientService> clients) {
        this.socket = socket;
        this.clients = clients;
    }


    @Override
    public void run() {
        byte[] receiveBuffer = new byte[16384];

        try {
            while (true) {
                Arrays.fill(receiveBuffer, (byte) 0);

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                String msg = new String(receivePacket.getData());
                broadcastMessage(msg, receivePacket.getAddress(), receivePacket.getPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastMessage(String msg, InetAddress senderAddress, int senderPort) throws IOException {
        byte[] sendBuffer = msg.getBytes();

        for (ClientService cs : clients) {
            if (cs.getPort() != senderPort && cs.getAddress() != senderAddress) {

                socket.send(
                        new DatagramPacket(sendBuffer, sendBuffer.length, cs.getAddress(), cs.getPort()));

            }
        }
    }
}
