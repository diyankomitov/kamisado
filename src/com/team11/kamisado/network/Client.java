package com.team11.kamisado.network;

import com.team11.kamisado.util.Coordinates;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "52.214.254.147";
    private static final int SERVER_PORT = 5042;
    
    private static Socket socket;
    private static ObjectInputStream inputStream;
    private static ObjectOutputStream outputStream;
    private static boolean black;
    private static String otherPlayerName;
    
    public static void connectToServer(String name, boolean isHost, boolean isBlack, boolean isSpeed) {
        try {
            System.out.println("Trying to connect");
            socket = new Socket(SERVER_IP, SERVER_PORT);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection made");
        
        black = isBlack;
        
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("OS made");
            outputStream.writeBoolean(isHost);
            outputStream.flush();
            System.out.println("isHost was sent");
            outputStream.writeObject(name);
            outputStream.flush();
            System.out.println("name sent");
            outputStream.writeBoolean(isSpeed);
            outputStream.flush();
            System.out.println("speed sent");
            if(isHost) {
                outputStream.writeBoolean(black);
                outputStream.flush();
                System.out.println("black sent");
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("waiting for opponent...");
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            otherPlayerName = (String) inputStream.readObject();
            System.out.println("Opponent is: " + otherPlayerName);
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    
        if(!isHost) {
            try {
                black = inputStream.readBoolean();
                System.out.println("black received");
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String getOtherPlayerName() {
        return otherPlayerName;
    }
    
    public static boolean getBlack() {
        return black;
    }
    
    public static void close() {
        try {
            outputStream.close();
            inputStream.close();
            socket.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Coordinates getCoordinates() {
        try {
            return (Coordinates) inputStream.readObject();
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void sendCoordinates(Coordinates outputCoords) {
        try {
            outputStream.writeObject(new Coordinates(outputCoords));
            outputStream.flush();
            System.out.println("Coordinates were sent: " + outputCoords.getX() + " " + outputCoords.getY());
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}














//
//
//        new Thread(() -> {
//            while(true) {
//                Scanner scanner = new Scanner(System.in);
//                System.out.println("Say something");
//                String input = scanner.nextLine();
//                System.out.println("You wrote something");
//
//                if(input.equals("exit")) {
//                    System.out.println("Goodbye");
//                    System.exit(0);
//                }
//
//                try {
//                    outputStream = new ObjectOutputStream(socket.getOutputStream());
//                    System.out.println("OS made");
//                    outputStream.writeObject(input);
//                    System.out.println("Something was sent");
//                }
//                catch(IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//
//        new Thread(() -> {
//            while(true) {
//                try {
//                    inputStream = new ObjectInputStream(socket.getInputStream());
//                    System.out.println("IS made");
//                    String output = (String) inputStream.readObject();
//                    System.out.println("'" + output + "'");
//                }
//                catch(IOException | ClassNotFoundException exception) {
//                    exception.printStackTrace();
//                }
//            }
//        }).start();
//    }

