package server;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import echoServer.EchoServer;

public class WebServer {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(100);
    private static final AtomicBoolean isTerminate = new AtomicBoolean(false);

    public static void main(final String [] args) throws IOException {
        System.out.println("hi!");
        Runtime.getRuntime().addShutdownHook(new Thread(
                WebServer::shutdown
        ));

        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress("0.0.0.0", 12345));

            while (!isTerminate.get()) {
                Socket socket = serverSocket.accept();
                try {
                    executorService.submit(new EchoServer(socket));
                } catch (RejectedExecutionException e) {
                    System.out.println("thread not available!!");
                    socket.close();
                }
            }
        }
    }

    private static void shutdown() {
        System.out.println("start shutdown");
        isTerminate.set(true);
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("thread time out");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            e.printStackTrace();
        }finally {
            System.out.println("bye bye!");
        }
    }
}
