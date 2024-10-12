package com.example.android_esp32_led;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConnectButtonHandler {
    private final EditText editTextIp;
    private final EditText editTextPort;
    private final Button buttonConnect;
    private final TcpClient tcpClient;
    private final TextView textViewStatus;

    public ConnectButtonHandler(EditText editTextIp, EditText editTextPort, Button buttonConnect, TcpClient tcpClient, TextView textViewStatus) {
        this.editTextIp = editTextIp;
        this.editTextPort = editTextPort;
        this.buttonConnect = buttonConnect;
        this.tcpClient = tcpClient;
        this.textViewStatus = textViewStatus;
    }

    public void setConnectButtonListener() {
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = editTextIp.getText().toString();
                int port = Integer.parseInt(editTextPort.getText().toString());

                // Попробуем подключиться
                tcpClient.connect(ip, port);
            }
        });
    }
}
