package manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public abstract class StateManager<T extends Cloneable> implements Observer {

    private final ArrayList<T> states;

    StateManager(T t) {
        this.states = new ArrayList<>();
        this.states.add(t);
    }

    public final boolean push(T t) {
        return states.add(t);
    }

    public final T peek() {
        return states.get(states.size() - 1);
    }

    public final boolean pop() {
        return states.remove(states.size() - 1) != null;
    }

    boolean autoPush(){
        try {
            Method method = Object.class.getDeclaredMethod("clone");
            method.setAccessible(true);
            return states.add((T) method.invoke(peek()));
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
            Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
            return false;
        }
    }

    public final int getNumOfStates() {
        return states.size();
    }
}
