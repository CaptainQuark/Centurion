package mapper;

import enumerations.AbilityType;
import manager.CombatManager;
import model.Ability;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class AbilityMapper extends AbstractMapper<Ability> {

    @Override
    public <E extends Enum> Ability map(E e) {
        AbilityType a = (AbilityType) e;

        switch (a){

            case DEBUG:
                return c -> System.out.println("DEBUG Ability working.");

                default:
                    System.out.println("default @ switch @ map(...) @ AbilityMapper called.");
                    return null;
        }
    }
}
