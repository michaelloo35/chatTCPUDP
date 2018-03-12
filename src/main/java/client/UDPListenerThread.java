package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class UDPListenerThread implements Runnable {

    private final DatagramSocket socket;

    public UDPListenerThread(DatagramSocket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        byte[] receiveBuffer = new byte[16384];

        try {
            while (true) {
                Arrays.fill(receiveBuffer, (byte) 0);

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                System.out.println(new String(receivePacket.getData()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
