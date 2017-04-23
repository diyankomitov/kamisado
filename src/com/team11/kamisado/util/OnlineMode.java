package com.team11.kamisado.util;

import com.team11.kamisado.controllers.GameController;
import com.team11.kamisado.models.Board;
import com.team11.kamisado.models.Player;
import com.team11.kamisado.network.Client;
import javafx.application.Platform;

public class OnlineMode implements Mode {
    private boolean first;
    
    public OnlineMode(boolean first) {
        this.first = first;
    }
    
    @Override
    public void move(GameController controller) {
        new Thread(() -> {
            System.out.print("thread started");
            Board board = controller.getBoard();
            Player player = board.getCurrentPlayer();
            
            if(!board.isFirstMove()) {
                if(first) {
                    System.out.println("sending again");
                    Client.sendCoordinates(board.getOldCoordinates());
                    first = false;
                }
                System.out.println("sending");
                System.out.println(board.getMoveCoordinates().getX() + " " + board.getMoveCoordinates().getY());
                Client.sendCoordinates(board.getMoveCoordinates());
            }
            else {
                Coordinates coordinates = new Coordinates(Client.getCoordinates());
                int x = coordinates.getX();
                int y = coordinates.getY();
                player.setCoordinates(x, y);
                System.out.println("from mode: " + x + " " + y);
                board.setCurrentSquare(x, y);
                board.setCurrentCoordinates();
                board.setValidCoordinates();
                board.setFirstMoveToFalse();
            }
            System.out.println("getting");
            Coordinates coordinates = new Coordinates(Client.getCoordinates());
            System.out.println("got coords");
            int x = coordinates.getX();
            int y = coordinates.getY();
            player.setCoordinates(x, y);
            System.out.println("from mode: " + x + " " + y);
            Platform.runLater(controller::onEnter);
            System.out.print("thread ended");
        }).start();
    }
}
