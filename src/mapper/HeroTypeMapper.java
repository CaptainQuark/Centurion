package mapper;

import enumerations.HeroType;
import generator.HeroGenerator;
import helper.ODSFileHelper;
import helper.Preference;
import helper.StandardPathHelper;
import manager.CombatManager;
import model.Hero;

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
    public <E extends Enum> Hero map(E e) {
        File f = new File(StandardPathHelper.getInstance().getDataPath() + Preference.getInstance().HERO_DATA_FILE);
        ArrayList<HashMap<String, ArrayList<String>>> table = ODSFileHelper.readODSAtTab(f, 0);
        HeroGenerator g = new HeroGenerator(table);

        // Cast 'e' to HeroType to access HeroType's elements.
        HeroType types = (HeroType) e;

        switch (types) {

            case DEBUG_HERO:
                return (Hero) new Hero(g.getName(),
                        g.getFaction("NOT_IMPLEMENTED", "Primary"),
                        g.getFaction("NOT_IMPLEMENTED", "Secondary"),
                        g.getHitpoints(), 20, g.getEvasion(), g.getPurchaseCosts())
                        .addAbility((final CombatManager c) -> {
                            if (c.getLastNumberThrownByUser() == c.getHero().getBonusNumber()) {
                                System.out.println(c.getHero().getBonusNumber() + " - My (= " + c.getHero().getName() + ") bonus number has been thrown!");
                            } else {
                                System.out.println(c.getLastNumberThrownByUser() + "...that's not my bonus number...");
                            }
                        });

            default:
                System.out.println("default @ switch @ map(...) @ HeroTypeMapper called.");
                return null;
        }
    }
}
