package com.team11.kamisado.network;

import com.team11.kamisado.util.Coordinates;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    
    private static ArrayList<ServerThread> availablePlayers = new ArrayList<>();
    
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
        
        int maxGames = 3;
        int numPlayers = 2;
        
        ServerThread[][] clientList = new ServerThread[maxGames][numPlayers];
        int i = 0;
        int j = 0;
        
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
                System.out.println("New Client connected...");
                ServerThread serverThread = new ServerThread(client);
                serverThread.setUp();
    
                if(serverThread.getHost()) {
                    clientList[i][0] = serverThread;
                    for(ServerThread availableGame : availablePlayers) {
                        if(serverThread.getSpeed() == availableGame.getSpeed()) {
                            clientList[i][2] = availableGame;
                            serverThread.setOpponent(availableGame);
                            System.out.println(serverThread.isOpponent());
                            break;
                        }
                    }
                    i = i == maxGames ? i : i+1;
                }
                else {
                    boolean matched = false;
                    for(int x = 0; x < i; x++) {
                        System.out.println("x = " + x);
                        ServerThread availableGame = clientList[x][0];
                        if(serverThread.getSpeed() == availableGame.getSpeed()) {
                            clientList[x][1] = serverThread;
                            matched = true;
                            System.out.println("matched");
                            availableGame.setOpponent(serverThread);
                            System.out.println(availableGame.isOpponent());
                            break;
                        }
                    }
                    if(!matched) {
                        availablePlayers.add(serverThread);
                    }
                }
                System.out.println("Thread created...");
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}