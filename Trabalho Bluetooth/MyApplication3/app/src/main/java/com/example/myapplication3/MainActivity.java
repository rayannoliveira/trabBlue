package com.example.myapplication3;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected BluetoothAdapter meuAdapter;
    Button btnBuscarListaPareados;
    Button btnDeMensagensRecebidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meuAdapter= BluetoothAdapter.getDefaultAdapter();
        //If para saber se o aparelho possuie o bluetooth no aparelho
        if(meuAdapter!=null){
            Toast.makeText(this,"O aparelho possui bluetooth", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Dispositivo sem bluetooth",Toast.LENGTH_SHORT).show();
        }
        //mostrar os buttons para o java
        //Pareados
        btnBuscarListaPareados=findViewById(R.id.btnListarPareados);
        btnBuscarListaPareados.setOnClickListener(this);
        //Servidor de mensagem
        btnDeMensagensRecebidas=findViewById(R.id.btnDeMensagensRecebidas);
        btnDeMensagensRecebidas.setOnClickListener(this);
    }
    public void onReume(){
        super.onResume();
        if(meuAdapter.isEnabled()){
            //If para a função para saber se o bluetooth esta ligado
            Toast.makeText(this,"Blutooth está ligado",Toast.LENGTH_SHORT).show();
        }else{
            Intent enableIntent= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent,0);
        }
    }
    public  void onActivityResult(int requestCode,int resultCode,Intent data){
        // Funcção para conferir se o bluetooth esta conectado
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode!= Activity.RESULT_OK){
            Toast.makeText(this,"O bluetooth não foi ativado",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
      if (v.getId()==R.id.btnListarPareados){
          Intent it = new Intent(this,listaPareadosActivity.class);
          startActivity(it);

      }
      if (v.getId()==R.id.btnDeMensagensRecebidas){
          Intent it= new Intent(this, Receber_msg.class);
          startActivity(it);
      }
    }
}
