import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ThreadServer {
    public static void main(String args[]) {
        // Определяем номер порта, который будет "слушать" сервер
        int port = 1777;

        try {
            // Открыть серверный сокет (ServerSocket)
            ServerSocket servSocket = new ServerSocket(port);
            servSocket.setSoTimeout(10_000);

            while (true) {
                System.out.println("Waiting for a connection on " + port);

                // Получив соединение начинаем работать с сокетом
                Socket fromClientSocket = servSocket.accept();

                // Стартуем новый поток для обработки запроса клиента
                new SocketThread(fromClientSocket).start();
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Время истекло.");
        }
        catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
