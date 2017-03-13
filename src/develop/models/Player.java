package develop.models;

/**
 * Created by Diyan on 11/03/2017.
 */
public class Player {
    private String name;
    private String playerColor;
    
    public Player(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPlayerColor() {
        return playerColor;
    }
    
    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }
}