package factory;

import model.AbstractCreature;

/**
 * Factory for publishing <tt>AbstractCreature</tt>s.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public interface CreatureFactory<T extends AbstractCreature> {

    /**
     * Guarantees that subclass produces objects.
     *
     * @param e     The type of  <tt>AbstractCreature</tt> requested.
     * @return      <tt>AbstractCreature</tt> object.
     */
    public abstract <E extends Enum> T produce(E e);
}
