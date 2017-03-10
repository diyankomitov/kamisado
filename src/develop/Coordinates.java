package develop;

import java.util.Objects;

/**
 * Created by Diyan on 09/03/2017.
 */
public class Coordinates{
    private int x;
    private int y;
    
    Coordinates() {
        
    }
    
    Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object o) {
        return o instanceof Coordinates &&
                this.getX() == ((Coordinates) o).getX() &&
                this.getY() == ((Coordinates) o).getY();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }
}