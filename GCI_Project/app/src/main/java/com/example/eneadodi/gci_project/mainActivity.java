package com.example.eneadodi.gci_project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class mainActivity extends AppCompatActivity {
    DataBaseHelper mydb;

    private static final String TAG = "MainActivity"
    private ListView lv;
    private static String sender;
    private ArrayAdapter aa;
    private ArrayList<String> listData = new ArrayList<String>();
    private Client client;
    private String host;
    private int port;

    //Getters and setters

    public DataBaseHelper getMydb() {
        return mydb;
    }

    public void setMydb(DataBaseHelper mydb) {
        this.mydb = mydb;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    //END OF GETTERS AND SETTERS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setClient(new Client(host,port,sender));
        getClient().connect();
        getClient().setMydb(mydb);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (findViewById(R.id.allUsers));
        setHost(getIntent().getStringExtra("host"));
        setPort(getIntent().getIntExtra("port"));
        String reciever;
        sender = getIntent().getStringExtra("username");
        aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listData);
        lv.setAdapter(aa);
        lv.setLongClickable(true);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String senderToMessage = sender;
                String recieverOfMessage = parent.getItemAtPosition(position);
                Intent i = new Intent(this,sendMessage.class);
                i.putExtra("sender",senderToMessage);
                i.putExtra("Reciever", recieverOfMessage);
                startActivityForResult(i,1);
                return false;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i =  new Intent(this,ViewMessages.class);
                Cursor data1 = mydb.getItemMessage(sender);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data1.getBlob(mydb.getItemId(sender))));
                Cursor data2 = mydb.getItemMessage(parent.getItemAtPosition(position));
                ArrayList<String> listOfMessagesSender;
                ObjectInputStream ois2 = new ObjectInputStream(new ByteArrayInputStream(data2.getBlob(mydb.getItemId(parent.getItemAtPosition(position)))));
                @SuppressWarnings("unchecked")
                        ArrayList<String> listOfMessagesSender = (ArrayList<String>)ois.readObject();
                @SuppressWarnings("unchecked")
                        ArrayList<String> listOfMessagesReciever = (ArrayList<String>)ois2.readObject();
                ois.close();
                ois2.close();
                i.putStringArrayListExtra("senderMessages",listOfMessagesSender);
                i.putStringArrayListExtra("recieverMessages",listOfMessagesReciever);
                startActivity(i);
            }
        });
    }
    private void populateListView(){
        Log.d(TAG, "populating list view. plz work.");
        Cursor data= mydb.getData();
        ArrayList<String> listData2 = new ArrayList<String>();
        while(data.moveToNext()){

        }
        listData = listData2;
        aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listData);
        lv.setAdapter(aa);
    }


    public void onStop(){
        client.setMydb(mydb);
        client.sendDatabase();
    }

    public void onPause(){
        client.setMydb(mydb);
        client.sendDatabase();
    }
}

