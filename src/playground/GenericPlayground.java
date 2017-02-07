package playground;

import enumerations.HeroType;
import factory.MerchantFactory;
import model.HeroMerchant;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class GenericPlayground {

    public static void main(String...args){

        if(MerchantFactory.getInstance().produce(HeroMerchant.class) == null){
            System.out.println("No merchant created.");
        }

        demo(HeroType.DEBUG_HERO);
    }

    private static void demo(Enum e){
        System.out.println(e.name());
    }
}
