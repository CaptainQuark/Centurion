package centurion.model.mapper;

import centurion.model.enumerations.AbilityType;
import centurion.model.enumerations.CombatStatus;
import centurion.model.model.Ability;

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

            case RESTORE_HP_IF_DAMAGE_WAS_TAKEN:
                return c -> {
                    if (c.getNumOfStates() > 1 && c.peek().getMonster().getHp() < c.look(c.getNumOfStates() - 1).getMonster().getHp()) {
                        c.peek().getMonster().setHp(c.peek().getMonster().getHpTotal());
                        System.out.println("Monster's health got restored.");
                    } else {
                        if (c.getNumOfStates() == 1 && c.peek().getMonster().getHp() < c.peek().getMonster().getHpTotal()) {
                            c.peek().getMonster().setHp(c.peek().getMonster().getHpTotal());
                            System.out.println("Monster's health got restored in first throw!.");
                        }
                    }
                };

            case HEAL_HERO_AT_BEGINNING_IF_HP_IS_UNDER_100:
                return c -> {
                    if (c.peek().getHero().getHp() < 100 && c.peek().getStatus().equals(CombatStatus.BEFORE_FIRST_ROUND_OF_ABILITIES))
                        c.peek().getHero().setHp(c.peek().getHero().getHpTotal());
                };

                default:
                    System.out.println("default @ switch @ map(...) @ AbilityMapper called.");
                    return null;
        }
    }
}
