package playground;

import dao.DAO;
import dao.SerialDAO;
import enumerations.HeroType;
import factory.HeroFactory;
import factory.MerchantFactory;
import model.Hero;
import model.HeroMerchant;

import java.util.ArrayList;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class GenericPlayground {

    public static void main(String...args){
        demoDAOByStringFilename();
        //demo2();
    }

    private static void demoDAOByStringFilename(){
        ArrayList<Hero> heroes = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            heroes.add(HeroFactory.getInstance().produce(HeroType.DEBUG_HERO));
        }

        DAO dao = new SerialDAO();

        dao.saveList("USER_HEROES", heroes);

        dao.getAllElements("USER_HEROES").forEach(System.out::print);
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
