package centurion.model.generator;

import java.util.List;

/**
 * Class to provide a unified way of extending a class with an ID.
 *  <tt>SequentialIDGenerator</tt> is able to automatically determine
 *  the next possible highest ID available and assign it to its local variable.
 *
 * @author Thomas Schoenmann, a1208739
 *
 * @param <T> Necessary to enable the correct implementation, only
 *  classes extending <tt>SequentialIDGenerator</tt> are allowed
 *  to guarantee that an ID to compare with is provided.
 */
public abstract class SequentialIDGenerator<T extends SequentialIDGenerator<?>> implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    private final int elementID;

    /**
     * Force every subclass to call 'setElementID' by calling
     *  <tt>SequentialIDGenerator</tt>'s constructor.
     *
     *  Automatically set the next highest ID (in the provided context).
     *
     * @param b	Single purpose is to force the subclass of calling the constructor.
     */
    public SequentialIDGenerator(boolean b){
        elementID = (this.provideAllElements() != null) ? generateHighestId(this.provideAllElements()) : 0;
    }

    /**
     * Simple method to retrieve the ID.
     *
     * @return ID of the object.
     */
    public int getElementID(){
        return this.elementID;
    }

    /**
     * Place where the magic happens and the highest ID gets determined.
     *
     * @param t	List of every currently available element.
     * @return	The new highest ID.
     */
    private int generateHighestId(List<T> t){
        int highest = 0;

        for(T temp : t){
            if(temp.getElementID() > highest)
                highest = temp.getElementID() + 1;
            else if(temp.getElementID() == highest)
                highest++;
        }

        return highest;
    }

    /**
     * Method which has to be implemented by the subclass. This way,
     *  the ElementID class stays flexible and doesn't have to care
     *  where its source comes from. That's the subclass' job!
     *
     * @return A list of every element currently available.
     */
    protected abstract List<T> provideAllElements();
}
