package mapper;

import model.AbstractCreature;

/**
 * Outsources the mapping process to make classes
 * where logic is implemented more readable.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public abstract class AbstractMapper<T> {

    /**
     * Maps a given <tt>Enum</tt>-element to an instance
     *  of type T.
     *
     * @param e     <tt>Enum</tt>element to use as key for mapping.
     * @param <E>   Class of <tt>Enum</tt> provided.
     * @return      Instance of type T.
     */
    public abstract <E extends Enum> T map(E e);
}
