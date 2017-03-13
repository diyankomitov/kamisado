package develop.util;

/**
 * Created by Diyan on 02/03/2017.
 */
public interface Observable {
    
    void subscribe(develop.util.Observer observer);
    void unsubscribe(develop.util.Observer observer);
    void notifyAllObservers();
    
    
}
