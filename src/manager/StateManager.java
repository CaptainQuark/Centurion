package manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Organizes a history of states of a given type in a stack-style.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public abstract class StateManager<T extends Cloneable> implements Observer {

    private final ArrayList<T> states;

    StateManager(T t) {
        this.states = new ArrayList<>();
        this.states.add(t);
    }

    /**
     * Add a new state as the latest element.
     *
     * @param t     The state to save.
     * @return      Result if addition was successful.
     */
    public final boolean push(T t) {
        return states.add(t);
    }

    /**
     * Retrieve the latest state.
     *
     * @return  The latest state currently saved.
     */
    public final T peek() {
        return states.get(states.size() - 1);
    }

    /**
     * Remove the latest state from the stack.
     *
     * @return  Result if removal was successful.
     */
    public final boolean pop() {
        return states.remove(states.size() - 1) != null;
    }

    /**
     * Clones the latest element and puts in on top of the stack,
     *  resulting in two equal states on top of the stack. The
     *  now latest state is used for operation, making the 2nd
     *  state effectively a un-operational until it gets used
     *  again, which is onyl possible if every state after gets
     *  removed.
     *
     * @return  Result if cloning was successful.
     */
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

    /**
     * Retrieve the number of states currently saved.
     *
     * @return Number of states.
     */
    public final int getNumOfStates() {
        return states.size();
    }
}
