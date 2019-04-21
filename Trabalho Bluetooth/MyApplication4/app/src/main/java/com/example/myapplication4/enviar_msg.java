package com.example.myapplication4;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class enviar_msg extends MainActivity implements View.OnClickListener {
    private static final String TAG="Myaction";
    //O mesmo uuid do servidor
    private  static  final UUID uuid= UUID.fromString("77b0c6de-38a4-4352-a377-faaae440179f");
    private BluetoothDevice device;
    private TextView tmMsg;
    private OutputStream out;
    private BluetoothSocket socket;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_msg);
        tmMsg=(TextView)findViewById(R.id.tMsg);
        //device selecionado na lista
        Intent minhaintent = getIntent();
        device = minhaintent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        TextView tNome=(TextView)findViewById(R.id.tNomeDevice);
        tNome.setText(device.getName()+"-"+device.getAddress());

        findViewById(R.id.btConecta).setOnClickListener(onClickConectar());
        findViewById(R.id.btEnviar).setOnClickListener(onClickEnviar());
    }

    private View.OnClickListener onClickConectar(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              try {
                  //faz a conexao utilozando uuid
                  socket=device.createInsecureRfcommSocketToServiceRecord(uuid);
                  socket.connect();
                  out=socket.getOutputStream();
                  //se chegou aqui é porque conectou
                  if (out!=null){
                      //Habilitar o botao para enviar a mensagem
                      findViewById(R.id.btConecta).setEnabled(false);
                      findViewById(R.id.btEnviar).setEnabled(true);
                  }

              }catch (IOException e){
              }
            }
        };
    }
    private View.OnClickListener onClickEnviar(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String msg= tmMsg.getText().toString();
             try{
                 if (out!=null){
                     System.out.println("passou");
                     out.write(msg.getBytes());
                 }
             }catch (IOException e){

             }
            }
        };
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        try{
            if (out!=null){
                out.close();
            }
        }catch (IOException e){

        }
        try {
            if (socket!=null){
                socket.close();
            }
        }catch (IOException e){
        }
    }

}
