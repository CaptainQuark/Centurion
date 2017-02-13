package playground;

import dao.DAO;
import dao.SerialDAO;
import enumerations.FileNames;
import enumerations.HeroType;
import factory.HeroFactory;
import model.Hero;
import model.HeroMerchant;
import model.HospitalMerchant;
import model.ItemMerchant;

import java.util.ArrayList;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class GenericPlayground {

    public static void main(String...args){
        //demoID();
        //printHeroes();
        //demoHospitalMerchant();
        //demoDAOByStringFilename();
        //demo2();
        //demoHeroEqual();
        //demoHeroMerchant();
        //demoItemMerchant();
    }

    private static void demoItemMerchant() {
        DAO dao = new SerialDAO();
        ItemMerchant itemMerchant = new ItemMerchant(dao, "Dummy item merchant", 20);

        System.out.println();
        itemMerchant.getMerchantElements().forEach(System.out::println);
    }

    private static void demoHeroMerchant() {
        DAO dao = new SerialDAO();
        HeroMerchant m = new HeroMerchant(dao, "Dummy merchant", 3);

        m.sellElement(m.getMerchantElements().indexOf(m.getMerchantElements().get(0)));

        System.out.println();
        m.getMerchantElements().forEach(System.out::println);
    }

    private static void demoID() {
        ArrayList<Hero> heroes = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            heroes.add(HeroFactory.getInstance().produce(HeroType.DEBUG_HERO));
            new SerialDAO().saveList(FileNames.USER_HEROES.name(), heroes);
        }

        System.out.println();
        heroes.forEach(System.out::println);
    }

    private static void demoHeroEqual() {
        DAO dao = new SerialDAO();
        Hero h = dao.<Hero>getAllElements(FileNames.USER_HEROES.name()).get(0);

        if (dao.<Hero>getAllElements(FileNames.USER_HEROES.name()).contains(h))
            System.out.println("Hero is in list.");
        else System.out.println("No hero in list detected.");
    }

    private static void demoHospitalMerchant() {
        DAO dao = new SerialDAO();
        HospitalMerchant hospitalMerchant = new HospitalMerchant(new SerialDAO());

        for (int i = 0; i < 2; i++) {
            hospitalMerchant.setHeroInNextFreeSlot(dao.<Hero>getAllElements(FileNames.USER_HEROES.name()).get(i));
        }

        System.out.println();
        printHeroes();


        //dao.<Hero>getAllElements(FileNames.USER_HEROES.name()).forEach(System.out::println);
    }

    private static void printHeroes() {
        DAO dao = new SerialDAO();
        System.out.println();
        dao.<Hero>getAllElements(FileNames.USER_HEROES.name()).forEach(System.out::println);
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
}
