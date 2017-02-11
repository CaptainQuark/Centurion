package model;

import dao.DAO;
import enumerations.FileNames;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class HeroMerchant extends AbstractMerchant<Hero> {

    public HeroMerchant(DAO dao, String name, int maxElements) {
        super(dao, name, maxElements);
    }

    @Override
    protected String getFileName() {
        return FileNames.MERCHANT_HEROES.name();
    }
}
