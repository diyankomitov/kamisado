package com.team11.kamisado.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Diyan on 30/03/2017.
 */
public class Client {
    private static final String SERVER_IP = "52.214.254.147";
    private static final int SERVER_PORT = 5042;
    private static Socket socket;
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;
    
    public static void connectToServer() {
        try {
            System.out.println("Trying to connect");
            socket = new Socket(SERVER_IP, SERVER_PORT);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    
        System.out.println("Connection made");
    
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("OS made");
            boolean isHost = true;
            outputStream.writeBoolean(isHost);
            System.out.println("isHost was sent");
            outputStream.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("OS made");
            String name = "Diyan";
            boolean isSpeed = false;
            boolean isBlack = true;
            outputStream.writeObject(name);
            outputStream.writeBoolean(isSpeed);
            outputStream.writeBoolean(isBlack);
            System.out.println("Game options were sent");
            outputStream.close();
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

