package model;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class HeroMerchant extends Merchant<Hero> {

    public HeroMerchant(Class<Hero> c, String name, int maxElements) {
        super(c, name, maxElements);
    }
}
