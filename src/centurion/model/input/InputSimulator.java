package centurion.model.input;

import centurion.model.model.Throw;

import java.util.Observable;

/**
 * Used to simulate user centurion.model.input.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class InputSimulator extends Observable {

    public void addThrow(Throw i){
        setChanged();
        notifyObservers(i);
    }
}
