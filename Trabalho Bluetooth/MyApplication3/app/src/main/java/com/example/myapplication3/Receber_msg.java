package com.example.myapplication3;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static java.lang.System.in;

public class Receber_msg extends MainActivity {
    private  static final UUID uuid= UUID.fromString("77b0c6de-38a4-4352-a377-faaae440179f");
    private  boolean running;
    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_receber_msg);
    }

    @Override
    protected  void onResume(){
        super.onResume();
        //se o bluetooth está ligado
        if(meuAdapter!=null && meuAdapter.isEnabled()){
            new ThreadServidor().start();
            running=true;
        }
    }
    //Thread para controlar a conexao e não travar
    class ThreadServidor extends Thread{
        @Override
        public void run(){

            try{
                //Abre o socket servidor e quem for conectar precisa ter o mesmo uuid
                BluetoothServerSocket serverSocket=meuAdapter.listenUsingInsecureRfcommWithServiceRecord("myapplication3",uuid);
                //fica aguardando alguem se conectar
                    try {
                    //aguardando conexões
                    socket=serverSocket.accept();
                    }catch (Exception e){
                    //se deu erro, vai encerrar aqui
                    return;
                    }
                if (socket!=null){
                    //Alguem conectou
                    serverSocket.close();
                    InputStream in=socket.getInputStream();
                    //Recuperar dispositivos
                    BluetoothDevice device= socket.getRemoteDevice();
                    updateViewConectou(device);
                    byte[] bytes= new byte[1024];
                    int length;
                    //ficar em loop para receber as mensagens
                    while (running){
                        //ler as mensagens
                        length=in.read(bytes);
                        String mensagemRecebida= new String(bytes,0,length);
                        TextView tMsg=(TextView)findViewById(R.id.tMsg);
                        final  String s= tMsg.getText().toString()+mensagemRecebida+"\n";
                        updateViewMensagem(s);
                    }
                }
            }catch (IOException e){
               // Log.e("Erro no servidor:" + e.getMessage(),e);
                running=false;
            }
        }
    }
    //Exibir mensagem na tela
    public void updateViewConectou(final BluetoothDevice device){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tNomeDevice= (TextView)findViewById(R.id.tNomeDevice);
                tNomeDevice.setText(device.getName());
            }
        });
    }
    //exibir a mensagem na tela
    public void  updateViewMensagem(final  String s){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tMsg=(TextView)findViewById(R.id.tMsg);
                tMsg.setText(s);
            }
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        running=false;
        try {
            if (socket!=null){
                socket.close();
            }
            if (serverSocket!=null){
                serverSocket.close();
            }
        }catch (IOException e){

        }
    }
}
