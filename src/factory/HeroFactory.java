package factory;

import mapper.HeroTypeMapper;
import model.Hero;

/**
 * Factory for producing <tt>Hero</tt>-objects.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class HeroFactory implements CreatureFactory<Hero> {

    private static HeroFactory instance;

    private HeroFactory(){}

    public static HeroFactory getInstance(){
        return instance == null ? instance = new HeroFactory() : instance;
    }

    /**
     * Encapsulated method to map a request <tt>Hero</tt>
     * to a produced object.
     *
     * @param e The type of  <tt>AbstractCreature</tt> requested.
     * @return The produced <tt>Hero</tt>.
     */
    @Override
    public <E extends Enum> Hero produce(E e) {
        return new HeroTypeMapper().map(e);
    }
}
