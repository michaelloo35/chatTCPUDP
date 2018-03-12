package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class UDPWriter {
    private final DatagramSocket socket;

    private final String IMG1 = "▓▓▓▓\n" +
            "▒▒▒▓▓\n" +
            "▒▒▒▒▒▓\n" +
            "▒▒▒▒▒▒▓\n" +
            "▒▒▒▒▒▒▓\n" +
            "▒▒▒▒▒▒▒▓\n" +
            "▒▒▒▒▒▒▒▓▓▓\n" +
            "▒▓▓▓▓▓▓░░░▓\n" +
            "▒▓░░░░▓░░░░▓\n" +
            "▓░░░░░░▓░▓░▓\n" +
            "▓░░░░░░▓░░░▓\n" +
            "▓░░▓░░░▓▓▓▓\n" +
            "▒▓░░░░▓▒▒▒▒▓\n" +
            "▒▒▓▓▓▓▒▒▒▒▒▓\n" +
            "▒▒▒▒▒▒▒▒▓▓▓▓\n" +
            "▒▒▒▒▒▓▓▓▒▒▒▒▓\n" +
            "▒▒▒▒▓▒▒▒▒▒▒▒▒▓\n" +
            "▒▒▒▓▒▒▒▒▒▒▒▒▒▓\n" +
            "▒▒▓▒▒▒▒▒▒▒▒▒▒▒▓\n" +
            "▒▓▒▓▒▒▒▒▒▒▒▒▒▓\n" +
            "▒▓▒▓▓▓▓▓▓▓▓▓▓\n" +
            "▒▓▒▒▒▒▒▒▒▓\n" +
            "▒▒▓▒▒▒▒▒▓\n";

    private final String IMG2 = "░░░░░░░▄▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▄░░░░░░\n" +
            "░░░░░░█░░▄▀▀▀▀▀▀▀▀▀▀▀▀▀▄░░█░░░░░\n" +
            "░░░░░░█░█░▀░░░░░▀░░▀░░░░█░█░░░░░\n" +
            "░░░░░░█░█░░░░░░░░▄▀▀▄░▀░█░█▄▀▀▄░\n" +
            "█▀▀█▄░█░█░░▀░░░░░█░░░▀▄▄█▄▀░░░█░\n" +
            "▀▄▄░▀██░█▄░▀░░░▄▄▀░░░░░░░░░░░░▀▄\n" +
            "░░▀█▄▄█░█░░░░▄░░█░░░▄█░░░▄░▄█░░█\n" +
            "░░░░░▀█░▀▄▀░░░░░█░██░▄░░▄░░▄░███\n" +
            "░░░░░▄█▄░░▀▀▀▀▀▀▀▀▄░░▀▀▀▀▀▀▀░▄▀░\n" +
            "░░░░█░░▄█▀█▀▀█▀▀▀▀▀▀█▀▀█▀█▀▀█░░░\n" +
            "░░░░▀▀▀▀░░▀▀▀░░░░░░░░▀▀▀░░▀▀░░░░\n";

    private final String IMG3 = "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n" +
            "█░░░░░░░░▀█▄▀▄▀██████░▀█▄▀▄▀██████\n" +
            "░░░░ ░░░░░░░▀█▄█▄███▀░░░ ▀█▄█▄███\n";

    public UDPWriter(DatagramSocket socket) {
        this.socket = socket;
    }

    public void sendRandomArt(InetAddress address, int port) throws IOException {

        String[] arts = {IMG1, IMG2, IMG3};

        byte[] sendBuffer = arts[new Random().nextInt(3)].getBytes();
        socket.send(
                new DatagramPacket(sendBuffer, sendBuffer.length, address, port));

    }
}
