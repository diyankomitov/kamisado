package develop;

import java.util.*;

/**
 * Created by Diyan on 02/03/2017.
 */
public interface Observable {
    
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
    void notifyAllObservers();
    
}
