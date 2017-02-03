package factory;

import enumerations.MonsterType;
import mapper.MonsterTypeMapper;
import model.AbstractCreature;
import model.Monster;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class MonsterFactory extends AbstractCreatureFactory {

    @Override
    protected <E extends Enum> AbstractCreature produce(E e) {
        return new MonsterTypeMapper().map(e);
    }

    /**
     * API to create a <tt>Hero</tt>.
     *
     * @param type The type of Hero requested.
     * @return <tt>Hero</tt> object.
     */
    public static Monster get(MonsterType type) {
        return (Monster) new MonsterFactory().produce(type);
    }
}
