package factory;

import model.Hero;
import model.HeroMerchant;
import model.Merchant;

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

    public Merchant produce(Class<? extends Merchant> c){


        if (c == HeroMerchant.class)
            return new HeroMerchant(Hero.class, "Dummy", 3);


        throw new IllegalArgumentException("MerchantFactory : produce : No matching merchant-class provided.");
    }
}
