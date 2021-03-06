package centurion.model.mapper;

import centurion.model.enumerations.AbilityType;
import centurion.model.enumerations.Biome;
import centurion.model.enumerations.MonsterDifficulty;
import centurion.model.enumerations.MonsterType;
import centurion.model.factory.AbilityFactory;
import centurion.model.generator.MonsterGenerator;
import centurion.model.helper.ODSFileHelper;
import centurion.model.helper.Preference;
import centurion.model.helper.StandardPathHelper;
import centurion.model.model.Monster;

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
    public Monster map(Enum e) {
        File f = new File(StandardPathHelper.getInstance().getDataPath() + Preference.getInstance().MONSTER_DATA_FILE);
        ArrayList<HashMap<String, ArrayList<String>>> table = ODSFileHelper.readODSAtTab(f, 0);
        MonsterGenerator g;

        // Cast 'e' to MonsterType to access its elements.
        MonsterType type = (MonsterType) e;


        switch (type) {
            case DEBUG_MONSTER:
                g = new MonsterGenerator(table, Biome.Forest, MonsterDifficulty.Easy);

                return (Monster) g.spawn()
                        .addAbility(AbilityFactory.getInstance().produce(AbilityType.RESTORE_HP_IF_DAMAGE_WAS_TAKEN));

            default:
                System.out.println("default @ switch @ map(...) @ MonsterTypeMapper called.");
                return null;
        }
    }
}
