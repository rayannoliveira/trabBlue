package com.example.myapplication3;


import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;
import android.widget.Toast;

public class BuscarDevicesActivity extends listaPareadosActivity {
    private ProgressDialog dialog;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceStace) {
        super.onCreate(savedInstanceStace);
        //Registrar o reciver para receber a mensagem
       this.registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        //Como o broadcast tiver terminado
      this.registerReceiver(mReceiver,new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
      buscar();

    }
    private void buscar(){
        //Outra busca não poderá ser usada
       if (meuAdapter.isDiscovering()){
            meuAdapter.cancelDiscovery();
        }
        //Disparar a busca
        meuAdapter.startDiscovery();
        dialog= ProgressDialog.show(this,"Exemplo","Buscando Bluetooth",false,true);

    }
    //Receiver para receber broadcast
    private final BroadcastReceiver mReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action= intent.getAction();
            //se um dispositivo foi encontrado
            if (BluetoothDevice.ACTION_FOUND.equals(action)){
                //Recupera o device da intent
                Parcelable[] listaPar= intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_DEVICE);
                BluetoothDevice device= (BluetoothDevice) listaPar[0];
                //Apenas inserir a lista de advices
                if (device.getBondState()!=BluetoothDevice.BOND_BONDED){
                    lista.add(device);
                    //Toast.makeText(this,"Encontrou"+ device.getName()+":"+ device.getAddress(),Toast.LENGTH_SHORT).show();
                    count++;
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                //iniciar a busca
                count=0;
                //Toast.makeText(this,"Busca iniciada",Toast.LENGTH_SHORT).show();
            } else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                //Terminou a busca
                //Toast.makeText(this,"Busca finalizada"+count +"devices encontrados",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                //Atualizar a lista
                //updateLista();
            }
        }
    };
    @Override
    protected void onDestroy(){
        super.onDestroy();
        //busca cancelada ao sair
        if (meuAdapter!=null){
            meuAdapter.cancelDiscovery();
        }
        //cancela o registro
        this.unregisterReceiver(mReceiver);
    }
}
