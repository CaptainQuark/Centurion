package mapper;

import enumerations.*;
import generator.MonsterGenerator;
import helper.ODSFileHelper;
import helper.Preference;
import helper.StandardPathHelper;
import manager.CombatManager;
import model.Monster;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
        File f = new File(StandardPathHelper.getInstance().getDataPath() + Preference.MONSTER_DATA_FILE);
        ArrayList<HashMap<String, ArrayList<String>>> table = ODSFileHelper.readODSAtTab(f, 0);
        MonsterGenerator g;

        // Cast 'e' to MonsterType to access its elements.
        MonsterType type = (MonsterType) e;


        switch (type) {
            case DEBUG_MONSTER:
                g = new MonsterGenerator(table, Biome.Forest, MonsterDifficulty.Easy);

                return (Monster) new Monster(g.getValAsString(MonsterValues.NAME),
                        g.getValAsString(MonsterValues.TYPE),
                        g.getValAsInt(MonsterValues.HP),
                        25,
                        g.getValAsInt(MonsterValues.EVASION),
                        Biome.Forest,
                        Checkout.DEFAULT, MonsterDifficulty.Medium,
                        g.getValAsDouble(MonsterValues.RESISTANCE),
                        g.getValAsDouble(MonsterValues.CRIT_MULITPLIER),
                        g.getValAsInt(MonsterValues.DMG_MIN),
                        g.getValAsInt(MonsterValues.DMG_MAX),
                        g.getValAsInt(MonsterValues.BLOCK),
                        g.getValAsInt(MonsterValues.CRIT_CHANCE),
                        g.getValAsInt(MonsterValues.BOUNTY),
                        g.getValAsInt(MonsterValues.ACCURACY))
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
