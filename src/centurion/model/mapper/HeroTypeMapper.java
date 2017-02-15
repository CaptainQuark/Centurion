package centurion.model.mapper;

import centurion.model.enumerations.AbilityType;
import centurion.model.enumerations.Biome;
import centurion.model.enumerations.HeroType;
import centurion.model.factory.AbilityFactory;
import centurion.model.generator.HeroGenerator;
import centurion.model.helper.ODSFileHelper;
import centurion.model.helper.Preference;
import centurion.model.helper.StandardPathHelper;
import centurion.model.model.Hero;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The place where every kind of <tt>Hero</tt> is defined.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class HeroTypeMapper extends AbstractMapper<Hero> {

    @Override
    public Hero map(Enum e) {
        File f = new File(StandardPathHelper.getInstance().getDataPath() + Preference.getInstance().HERO_DATA_FILE);
        ArrayList<HashMap<String, ArrayList<String>>> table = ODSFileHelper.readODSAtTab(f, 0);
        HeroGenerator g = new HeroGenerator(table);

        // Cast 'e' to HeroType to access HeroType's elements.
        HeroType types = (HeroType) e;

        switch (types) {

            case DEBUG_HERO:
                return (Hero) g.spawn(Biome.Coast)
                        .addAbility(AbilityFactory.getInstance().produce(AbilityType.HEAL_HERO_AT_BEGINNING_IF_HP_IS_UNDER_100));

            default:
                System.out.println("default @ switch @ map(...) @ HeroTypeMapper called.");
                return null;
        }
    }
}
