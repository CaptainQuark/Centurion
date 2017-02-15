package centurion.model.mapper;

import centurion.model.enumerations.MerchantType;
import centurion.model.model.AbstractMerchant;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class MerchantMapper extends AbstractMapper<AbstractMerchant> {

    @Override
    public AbstractMerchant map(Enum e) {

        switch ((MerchantType) e) {
            case DESSERT_WALKER:
                return null;
            default:
                return null;
        }
    }
}
