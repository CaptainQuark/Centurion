package factory;

import enumerations.HeroTypes;
import mapper.HeroTypeMapper;
import model.AbstractCreature;
import model.Hero;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class HeroFactory extends AbstractCreatureFactory {

    /**
     * Encapsulated method to map a request <tt>Hero</tt>
     *  to a produced object.
     *
     * @param e   The type of  <tt>AbstractCreature</tt> requested.
     * @return      The produced <tt>Hero</tt>.
     */
    @Override
    protected <E extends Enum> AbstractCreature produce(E e) {
        return new HeroTypeMapper().map(e);
    }

    /**
     * API to create a <tt>Hero</tt>.
     *
     * @param type  The type of Hero requested.
     * @return      <tt>Hero</tt> object.
     */
    public static Hero get(HeroTypes type){
        return (Hero) new HeroFactory().produce(type);
    }
}
