package mapper;

import enumerations.AbilityType;
import enumerations.CombatStatus;
import model.Ability;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class AbilityMapper extends AbstractMapper<Ability> {

    @Override
    public Ability map(Enum e) {
        AbilityType a = (AbilityType) e;

        switch (a){

            case DEBUG:
                return c -> System.out.println("DEBUG Ability working.");

            case HEAL_HERO_AT_BEGINNING_IF_HP_IS_UNDER_100:
                return c -> {
                    if(c.getHero().getHp() < 100 && c.getStatus().equals(CombatStatus.BEFORE_FIRST_ROUND_OF_ABILITIES))
                        c.getHero().setHp(c.getHero().getHpTotal());
                };

                default:
                    System.out.println("default @ switch @ map(...) @ AbilityMapper called.");
                    return null;
        }
    }
}
