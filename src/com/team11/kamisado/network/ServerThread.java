package com.team11.kamisado.network;

import com.team11.kamisado.util.Coordinates;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    
    private Socket client;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean onTurn;
    private String name;
    private boolean isSpeed;
    private boolean isBlack;
    private ServerThread opponent;
    private boolean isHost;
    private boolean first = false;
    
    public ServerThread(Socket client) {
        this.client = client;
    }
    
    public void setUp() {
        try {
            inputStream = new ObjectInputStream(client.getInputStream());
            isHost = inputStream.readBoolean();
            System.out.println("this client is host: " + isHost);
            name = (String) inputStream.readObject();
            System.out.println("read name");
            isSpeed = inputStream.readBoolean();
            System.out.println("read speed");
            if(isHost) {
                isBlack = inputStream.readBoolean();
                System.out.println("read black");
            }
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        System.out.println("Running");
    
        System.out.println("opponent found");
        if(isHost) {
            opponent.setOpponent(this);
            System.out.println("opponent set");
        }
    
        try {
            outputStream = new ObjectOutputStream(client.getOutputStream());
            outputStream.writeObject(opponent.getPlayerName());
            outputStream.flush();
            System.out.println("sent otherplayer name");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        if(!isHost) {
            isBlack = !opponent.getBlack();
            try {
                outputStream.writeBoolean(isBlack);
                outputStream.flush();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        if(isBlack) {
            onTurn = true;
            first = true;
        }
        
        while(true) {
            if(onTurn) {
                System.out.println("trying to get from client: " + getPlayerName());
                Coordinates coords = getFromClient();
                System.out.println(coords.getX() + " " + coords.getY());
                opponent.sendToClient(coords);
                System.out.println("continue1");
                if(first) {
                    coords = getFromClient();
                    System.out.println(coords.getX() + " " + coords.getY());
                    opponent.sendToClient(coords);
                    first = false;
                }
                
                System.out.println("continue2");
                onTurn = false;
                opponent.onTurn = true;
                
                System.out.println("continue3");
            }
            else {
                try {
                    sleep(1);
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    public String getPlayerName() {
        return name;
    }
    
    public void setOnTurn(boolean turn) {
        onTurn = turn;
        System.out.println("turns switched: " + onTurn + " for: " + getPlayerName());
    }
    
    public Coordinates getFromClient() {
        Coordinates coords = null;
        try {
            coords = new Coordinates((Coordinates) inputStream.readObject());
            System.out.println("Coordinates are: " + coords.getX() + ", " + coords.getY());
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return coords;
    }
    
    public void sendToClient(Coordinates coordinates) {
        try {
            outputStream.writeObject(coordinates);
            outputStream.flush();
            System.out.println("Coordinates sent: " + coordinates.getX() + " " + coordinates.getY());
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean getSpeed() {
        return isSpeed;
    }
    
    public boolean getBlack() {
        return isBlack;
    }
    
    public boolean getHost() {
        return isHost;
    }
    
    public void setOpponent(ServerThread opponent) {
        this.opponent = opponent;
        System.out.println("setting opponent");
        this.start();
    }
    public boolean isOpponent() {
        return opponent != null;
    }
}
