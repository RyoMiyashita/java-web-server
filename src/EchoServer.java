import java.io.*;
import java.net.Socket;

public class EchoServer implements Runnable{
    private final static String CLOSE_STRING = "quit";

    private final Socket socket;

    public EchoServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
