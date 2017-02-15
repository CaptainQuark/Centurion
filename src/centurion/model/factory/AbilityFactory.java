package centurion.model.factory;

import centurion.model.enumerations.AbilityType;
import centurion.model.mapper.AbilityMapper;
import centurion.model.model.Ability;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class AbilityFactory {

    private static AbilityFactory instance;

    private AbilityFactory() {
    }

    public static AbilityFactory getInstance() {
        return instance == null ? instance = new AbilityFactory() : instance;
    }

    /**
     * Get an ability matching to the provided <tt>Enum</tt>-val.
     *
     * @param type  Type of <tt>Ability</tt> to look for.
     * @return      The requested <tt>Ability</tt>.
     */
    public Ability produce(AbilityType type) {
        return new AbilityMapper().map(type);
    }
}
