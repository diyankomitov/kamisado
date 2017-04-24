package com.team11.kamisado.util;

import com.team11.kamisado.controllers.GameController;
import com.team11.kamisado.models.Player;

/**
 * Created by Diyan on 22/04/2017.
 */
public class GameMode {
    private Mode mode;
    private Player player;
    
    public GameMode(Mode mode) {
        this.mode = mode;
    }
    
    public void executeMove(GameController controller) {
        if(player == controller.getBoard().getCurrentPlayer()) {
            mode.move(controller);
        }
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public String getMode() {
        return mode.getName();
    }
}
