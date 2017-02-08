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
        demo2();
    }

    private static void demo2(){
        int i = 1000;
        int j = 753;
        double c = 1200;
        int d = 1000;
        System.out.println((i - j) * (c / 1000.0));
    }

    private static void demo(Enum e){
        if(MerchantFactory.getInstance().produce(HeroMerchant.class) == null){
            System.out.println("No merchant created.");
        }

        demo(HeroType.DEBUG_HERO);
    }
}
