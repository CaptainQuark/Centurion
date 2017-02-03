package input;

import java.util.Observable;

/**
 * Used to simulate user input.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class InputSimulator extends Observable {

    public void addThrow(Integer i){
        setChanged();
        notifyObservers(i);
    }
}
