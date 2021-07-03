import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private final static String CLOSE_STRING = "quit";

    public static void main(final String [] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(12345));

            while (true) {
                Socket socket = serverSocket.accept();

                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println("received : " + line);
                        if (CLOSE_STRING.equals(line)) {
                            break;
                        }

                        bufferedWriter.write(line + '\n');
                        bufferedWriter.flush();
                    }
                    bufferedWriter.write("bye\n");
                    bufferedWriter.flush();
                }
            }
        }
    }
}
