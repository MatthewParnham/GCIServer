package com.example.eneadodi.gci_project;

package com.example.eneadodi.gci_project;

import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class ClientWriter extends Thread {
    private ObjectOutputStream out;
    private Socket socket;
    private String clientName;
    private Client client;

    public ClientWriter(Socket clientSocket, String clientName, Client client) {
        this.socket = clientSocket;
        this.clientName = clientName;
        this.client = client;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            //out.writeObject(clientName

            out.writeObject(client.getMydb());
        } catch (IOException e) {
            System.out.println("Error outputing: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
