package com.team11.kamisado.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        
        //        int numberOfGames = 0;
        //        Socket[][] socketArray = new Socket[1][2];
        
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            
            while(scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if(input.equals("exit")) {
                    System.out.println("Shutting down server");
                    System.exit(0);
                }
            }
        }).start();
        
        
        ArrayList<Socket> socketList = new ArrayList<>();
        
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(5042);
            System.out.println("Server started...");
        }
        catch(IOException e) {
            e.printStackTrace();
            return;
        }
        
        while(true) {
            try {
                Socket client = serverSocket.accept();
                socketList.add(client);
                System.out.println("New Client connected...");
                new ServerThread(client, socketList).start();
                System.out.println("Thread created...");
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}