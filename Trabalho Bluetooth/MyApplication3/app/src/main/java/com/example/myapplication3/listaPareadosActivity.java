package com.example.myapplication3;

import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnContextClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class listaPareadosActivity extends MainActivity implements OnItemClickListener{
    protected List <BluetoothDevice> lista;
    private ListView listView;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity__listview);
        listView=(ListView) findViewById(R.id.listView);
        Toast.makeText(this,"Lista",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected  void onResume(){
     super.onResume();
     //Listar aparelhos pareados
     if(meuAdapter!=null){
        lista= new ArrayList<BluetoothDevice>(meuAdapter.getBondedDevices());
        updateLista();
     }
    }

    protected void updateLista(){
        List<String> nomes= new ArrayList<String>();

        //Criar lista de pareados
        for (BluetoothDevice device:lista) {
            // A variavel boolean sera true, pois a lista é de parados
            boolean pareado= device.getBondState()==BluetoothDevice.BOND_BONDED;
            nomes.add(device.getName()+"-"+ device.getAddress()+(pareado ?"*pareado":" "));
        }

        //Criando um adapter para parear a lista
        int layout= android.R.layout.simple_list_item_1;
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,layout,nomes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int idx, long id){
        //recuperar o device selecionado
        BluetoothDevice device=lista.get(idx);
        String msg= device.getName()+"-"+ device.getAddress();
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}
