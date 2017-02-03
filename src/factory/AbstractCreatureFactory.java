package factory;

import model.AbstractCreature;

/**
 * Factory for publishing <tt>AbstractCreature</tt>s.
 *  Uses Singleton-pattern.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
abstract class AbstractCreatureFactory {

    /**
     * Guarantees that subclass produces objects.
     *
     * @param e     The type of  <tt>AbstractCreature</tt> requested.
     * @return      <tt>AbstractCreature</tt> object.
     */
    protected abstract <E extends Enum> AbstractCreature produce(E e);
}
