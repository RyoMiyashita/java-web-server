import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

    public static void main(final String [] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(12345));

            ExecutorService executorService = Executors.newFixedThreadPool(100);

            //noinspection InfiniteLoopStatement
            while (true) {
                executorService.submit(new EchoServer(serverSocket.accept()));
            }
        }
    }
}
