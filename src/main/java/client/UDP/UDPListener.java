package client.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class UDPListener implements Runnable {

    private final DatagramSocket socket;

    public UDPListener(DatagramSocket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        byte[] receiveBuffer = new byte[16384];

        try {
            while (true) {

                // flush buffer
                Arrays.fill(receiveBuffer, (byte) 0);

                // wait for message
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                // print message
                System.out.println(new String(receivePacket.getData()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
