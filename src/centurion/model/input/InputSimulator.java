package centurion.model.input;

import java.util.Observable;

/**
 * Used to simulate user centurion.model.input.
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
