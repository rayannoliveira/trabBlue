package com.example.trabalhoblue;


import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import  java.io.IOException;
import  java.io.OutputStream;
import java.util.UUID;
import android.bluetooth.BluetoothAdapter;
import  android.bluetooth.BluetoothServerSocket;
import  android.bluetooth.BluetoothSocket;



public class ServidorBTActivity extends Activity {
private TextView lbEntrada;
private TextView lbCliente;
private BluetoothAdapter adapter;

private static final String NAME="EccoServerBT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servidor_bt);
    }
}
