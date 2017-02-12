package factory;

import dao.DAO;
import model.AbstractMerchant;
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

    public AbstractMerchant produce(DAO dao, Class<? extends AbstractMerchant> c) {


        if (c == HeroMerchant.class)
            return new HeroMerchant(dao, "Dummy", 3);


        throw new IllegalArgumentException("MerchantFactory : produce : No matching merchant-class provided.");
    }
}
