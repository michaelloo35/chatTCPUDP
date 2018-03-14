package client.TCP;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPWriter {
    private final PrintWriter out;
    private final Scanner scanner;

    public TCPWriter(Socket socket) throws IOException {
        out = setupOut(socket);
        scanner = new Scanner(System.in);
    }


    private PrintWriter setupOut(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Captures message from standard input and transfers through TCP connection
     */
    public void sendMessageFromSTDIN() {
        String msg = scanner.nextLine();
        out.println(msg);
    }

}
