package model;

import dao.DAO;
import enumerations.FileNames;
import enumerations.HeroType;
import factory.HeroFactory;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class HeroMerchant extends AbstractMerchant<Hero> {

    public HeroMerchant(DAO dao, String name, int maxElements) {
        super(dao, name, maxElements);

        System.out.println("HeroMerchant constructor called.");

    }

    @Override
    protected String getMerchantElementsFileName() {
        return FileNames.MERCHANT_HEROES.name();
    }

    @Override
    protected String getUserElementsFileName() {
        return FileNames.USER_HEROES.name();
    }

    @Override
    protected Hero createElement() {

        // TODO Make accessible at runtime to enable correct hero creation.
        return HeroFactory.getInstance().produce(HeroType.DEBUG_HERO);
    }
}
