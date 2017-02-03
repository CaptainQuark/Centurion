package mapper;

import enumerations.HeroType;
import manager.CombatManager;
import model.Hero;

/**
 * The place where every kind of <tt>Hero</tt> is defined.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class HeroTypeMapper extends AbstractMapper<Hero> {

    @Override
    public  <E extends Enum> Hero map(E e) {

        // Cast 'e' to HeroType to access HeroType's elements.
        HeroType types = (HeroType) e;

        switch (types) {

            case DEBUG_HERO:
                return (Hero) new Hero("Dummy Hero", 100, 20, 20, 100.0)
                        .addAbility((CombatManager c) -> {
                            if (c.lastNumberThrownByUser == c.getHero().getBonusNumber()) {
                                System.out.println(c.getHero().getBonusNumber() + " - My (= " + c.getHero().getName() + ") bonus number has been thrown!");
                            }
                            else{
                                System.out.println(c.lastNumberThrownByUser + "...that's not my bonus number...");
                            }
                        });

            default:
                System.out.println("default @ switch @ map(...) @ HeroTypeMapper called.");
                return null;
        }
    }
}
