package com.example.android_esp32_led;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpClient {
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private final TextView textViewStatus;
    private final Activity activity; // Ссылка на Activity

    public TcpClient(Activity activity, TextView textViewStatus) {
        this.activity = activity; // Инициализируем ссылку на Activity
        this.textViewStatus = textViewStatus; // Инициализируем TextView
    }

    public void connect(String ip, int port) {
        new Thread(() -> {
            try {
                socket = new Socket(ip, port);
                output = new PrintWriter(socket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.i("TCP", "Подключение установлено");

                // Получаем сообщения от ESP3232
                String message;
                while ((message = input.readLine()) != null) {
                    Log.i("TCP", "Сообщение от ESP32: " + message);
                    final String receivedMessage = message; // Переменная для обновления UI
                    activity.runOnUiThread(() -> textViewStatus.setText(receivedMessage)); // Обновление UI в главном потоке
                }

            } catch (IOException e) {
                Log.e("TCP", "Ошибка подключения: " + e.getMessage());
            }
        }).start();
    }

    public void sendMessage(String message) {
        if (output != null) {
            output.println(message);
            Log.i("TCP", "Отправлено сообщение: " + message);
        }
    }
}
