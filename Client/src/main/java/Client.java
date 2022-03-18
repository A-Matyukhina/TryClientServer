import com.github.javafaker.Faker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String args[]) throws Exception {
        // Определяем номер порта, на котором нас ожидает сервер для ответа
        int portNumber = 1777;
        // Подготавливаем данные для запроса - просто строка
        List<String> arrStr = new ArrayList<>();
        Faker faker = new Faker();
        int count = 0;
        for (int i = 0; i <= 30; i++) {
            arrStr.add(faker.name().lastName());
        }
        String streamArr = arrStr.toString();

        // Пишем, что стартовали клиент
        System.out.println("Client is started");

        // Открыть сокет (Socket) для обращения к локальному компьютеру
        // Сервер мы будем запускать на этом же компьютере
        // Это специальный класс для сетевого взаимодействия c клиентской стороны
        Socket socket = new Socket("127.0.0.1", portNumber);

        // Создать поток для чтения символов из сокета
        // Для этого надо открыть поток сокета - socket.getInputStream()
        // Потом преобразовать его в поток символов - new InputStreamReader
        // И уже потом сделать его читателем строк - BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Создать поток для записи символов в сокет
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);


        // Отправляем тестовую строку в сокет
        pw.println(streamArr);

        // Входим в цикл чтения, что нам ответил сервер
        while ((streamArr = br.readLine()) != null) {
            // Если пришел ответ “bye”, то заканчиваем цикл
            if (arrStr.equals("bye")) {
                break;
            }
            // Печатаем ответ от сервера на консоль для проверки
            System.out.println(streamArr);
            // Посылаем ему "bye" для окончания "разговора"
            pw.println("bye");
        }

        br.close();
        pw.close();
        socket.close();
    }
}