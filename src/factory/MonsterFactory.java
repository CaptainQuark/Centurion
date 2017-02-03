package factory;

import mapper.MonsterTypeMapper;
import model.AbstractCreature;
import model.Monster;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class MonsterFactory implements CreatureFactory {

    private static MonsterFactory instance;

    private MonsterFactory() {}

    public static MonsterFactory getInstance() {
        return instance == null ? instance = new MonsterFactory() : instance;
    }

    @Override
    public Monster produce(Enum e) {
        return new MonsterTypeMapper().map(e);
    }
}
