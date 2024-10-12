package com.example.android_esp32_led;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConnectionSettings {

    private EditText editTextIp;
    private EditText editTextPort;
    private Button buttonConnect;
    private SharedPreferences sharedPreferences;

    public ConnectionSettings(EditText editTextIp, EditText editTextPort, Button buttonConnect, Context context) {
        this.editTextIp = editTextIp;
        this.editTextPort = editTextPort;
        this.buttonConnect = buttonConnect;

        // Инициализация SharedPreferences
        this.sharedPreferences = context.getSharedPreferences("TCP_Settings", Context.MODE_PRIVATE);

        // Загрузка сохранённых данных
        loadSettings();

        // Обработка нажатия на кнопку подключения
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = editTextIp.getText().toString();
                int port = Integer.parseInt(editTextPort.getText().toString());

                // Сохранение IP и порта
                saveSettings(ip, port);
            }
        });
    }

    // Сохранение IP и порта в SharedPreferences
    private void saveSettings(String ip, int port) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("IP_ADDRESS", ip);
        editor.putInt("PORT", port);
        editor.apply();
    }

    // Загрузка сохранённых настроек
    private void loadSettings() {
        String savedIp = sharedPreferences.getString("IP_ADDRESS", "");
        int savedPort = sharedPreferences.getInt("PORT", 8080);

        editTextIp.setText(savedIp);
        editTextPort.setText(String.valueOf(savedPort));
    }
}
