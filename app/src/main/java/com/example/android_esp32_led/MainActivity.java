package com.example.android_esp32_led;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextIp;
    private EditText editTextPort;
    private Button buttonConnect;
    private TextView textViewStatus;

    private SharedPreferences sharedPreferences;
    private TcpClient tcpClient;
    private ConnectButtonHandler connectButtonHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextIp = findViewById(R.id.editTextIp);
        editTextPort = findViewById(R.id.editTextPort);
        buttonConnect = findViewById(R.id.buttonConnect);
        textViewStatus = findViewById(R.id.textViewStatus);

        // Инициализация SharedPreferences
        sharedPreferences = getSharedPreferences("TCP_Settings", Context.MODE_PRIVATE);

        // Создание экземпляра TcpClient, передавая текущую Activity и TextView
        tcpClient = new TcpClient(this, textViewStatus); // Передаем 'this' как Activity

        // Создание обработчика кнопки подключения
        connectButtonHandler = new ConnectButtonHandler(editTextIp, editTextPort, buttonConnect, tcpClient, textViewStatus);
        connectButtonHandler.setConnectButtonListener();
    }

    // Дополнительные методы для сохранения и загрузки настроек (не показаны в этом фрагменте)
}
