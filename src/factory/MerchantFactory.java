package factory;

import model.AbstractMerchant;
import model.Hero;
import model.HeroMerchant;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class MerchantFactory {

    private static MerchantFactory instance;

    private MerchantFactory(){}

    public static MerchantFactory getInstance(){
        return instance == null ? instance = new MerchantFactory() : instance;
    }

    public AbstractMerchant produce(Class<? extends AbstractMerchant> c){


        if (c == HeroMerchant.class)
            return new HeroMerchant(Hero.class, "Dummy", 3);


        throw new IllegalArgumentException("MerchantFactory : produce : No matching merchant-class provided.");
    }
}
