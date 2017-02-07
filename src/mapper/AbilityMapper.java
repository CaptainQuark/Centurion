package mapper;

import enumerations.AbilityType;
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

                default:
                    System.out.println("default @ switch @ map(...) @ AbilityMapper called.");
                    return null;
        }
    }
}
