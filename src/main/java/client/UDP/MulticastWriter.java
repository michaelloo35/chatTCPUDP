package client.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Random;

public class MulticastWriter {

    private final String IMG1 = "░░░░░░░█▐▓▓░████▄▄▄█▀▄▓▓▓▌█ very cool\n" +
            "░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█\n" +
            "░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█\n" +
            "░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌ such awsome\n" +
            "░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█\n" +
            "▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌\n" +
            "█▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█ much amazing\n" +
            "█▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█\n" +
            "▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌\n" +
            "▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌\n" +
            "█▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█ WoW\n";

    private final String IMG2 = "───▄▄▄\n" +
            "─▄▀░▄░▀▄\n" +
            "─█░█▄▀░█\n" +
            "─█░▀▄▄▀█▄█▄▀\n" +
            "▄▄█▄▄▄▄███▀\n";

    private final String IMG3 = "░░█▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\n" +
            "██▀▀▀██▀▀▀▀▀▀██▀▀▀██\n" +
            "█▒▒▒▒▒█▒▀▀▀▀▒█▒▒▒▒▒█\n" +
            "█▒▒▒▒▒█▒████▒█▒▒▒▒▒█\n" +
            "██▄▄▄██▄▄▄▄▄▄██▄▄▄██\n";

    private final MulticastSocket socket;


    public MulticastWriter(MulticastSocket socket) {
        this.socket = socket;
    }

    public void sendRandomArt(InetAddress multicastGroup) throws IOException {

        String[] arts = {IMG1, IMG2, IMG3};

        byte[] sendBuffer = arts[new Random().nextInt(3)].getBytes();

        socket.leaveGroup(multicastGroup);
        socket.send(
                new DatagramPacket(sendBuffer, sendBuffer.length, multicastGroup, socket.getLocalPort()));
        socket.joinGroup(multicastGroup);
    }
}
