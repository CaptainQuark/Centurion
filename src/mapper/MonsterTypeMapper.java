package mapper;

import enumerations.Biome;
import enumerations.MonsterType;
import manager.CombatManager;
import model.Monster;

/**
 * Outsources the mapping process to make classes
 * where logic is implemented more readable.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class MonsterTypeMapper extends AbstractMapper<Monster> {

    // TODO Still strange behavior that 'new Monster(...)' has to be cast to Monster. Improvements needed.

    @Override
    public <E extends Enum> Monster map(E e) {

        // Cast 'e' to MonsterType to access its elements.
        MonsterType type = (MonsterType) e;

        switch (type) {
            case DEBUG_MONSTER:
                return (Monster) new Monster("Dummy Monster", 200, 25, 10, Biome.DEBUG_BIOME)
                        .addAbility((CombatManager c) -> {
                            if (c.getLastNumberThrownByUser() > 100)
                                System.out.println("More than a 100 has been thrown - what a mighty shot!");
                        });

            default:
                System.out.println("default @ switch @ map(...) @ MonsterTypeMapper called.");
                return null;
        }
    }
}
