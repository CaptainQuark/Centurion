package mapper;

import enumerations.MerchantType;
import model.Merchant;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class MerchantMapper extends AbstractMapper<Merchant> {

    @Override
    public Merchant map(Enum e) {

        switch ((MerchantType) e) {
            case DESSERT_WALKER:
                return null;
            default:
                return null;
        }
    }
}
