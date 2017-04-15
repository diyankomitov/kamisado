package com.team11.kamisado.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    
    private Socket client;
    private ArrayList<Socket> socketList;
    private boolean running;
    
    public ServerThread(Socket client, ArrayList<Socket> socketList) {
        
        this.client = client;
        this.socketList = socketList;
    }
    
    @Override
    public void run() {
        running = true;
        System.out.println("Running");
        boolean isHost = false;
        
        try {
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            isHost = inputStream.readBoolean();
            System.out.println("this client is host: " + isHost);
            inputStream.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        if(isHost) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
                String name = (String) inputStream.readObject();
                boolean isSpeed = inputStream.readBoolean();
                boolean isBlack = inputStream.readBoolean();
                System.out.println("The hosts name is: " + name + " this is speed: " + isSpeed + " the host is black: " + isBlack);
            }
            catch(IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
