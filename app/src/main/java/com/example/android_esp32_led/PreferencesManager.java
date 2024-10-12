package com.example.android_esp32_led;


import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private static final String PREF_NAME = "TCP_Settings";
    private static final String KEY_IP_ADDRESS = "IP_ADDRESS";
    private static final String KEY_PORT = "PORT";

    private SharedPreferences sharedPreferences;

    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Метод для сохранения IP и порта
    public void saveSettings(String ip, int port) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IP_ADDRESS, ip);
        editor.putInt(KEY_PORT, port);
        editor.apply();
    }

    // Метод для загрузки IP и порта
    public String getIpAddress() {
        return sharedPreferences.getString(KEY_IP_ADDRESS, "");
    }

    public int getPort() {
        return sharedPreferences.getInt(KEY_PORT, 8080);
    }
}
