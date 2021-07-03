import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public static void main(final String [] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(12345));

            while (true) {
                Socket socket = serverSocket.accept();

                try (InputStream in = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {
                    int readByte = in.read();
                    out.write(readByte);

                    System.out.println("received: " + String.valueOf(readByte));
                }
            }
        }
    }
}
