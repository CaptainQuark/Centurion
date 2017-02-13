package playground;

import dao.DAO;
import dao.SerialDAO;
import enumerations.FileNames;
import enumerations.HeroType;
import factory.HeroFactory;
import manager.CreatureManager;
import model.Hero;

import java.util.ArrayList;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class CreatureManagerSample {

    private static DAO dao = new SerialDAO();

    public static void main(String... args) {

        //demoDAO();
        CreatureManager.getInstance(dao).getHeroes().forEach(System.out::println);
    }

    private static void demoDAO() {
        DAO dao = new SerialDAO();

        ArrayList<Hero> heroes = new ArrayList<>();
        heroes.add(HeroFactory.getInstance().produce(HeroType.DEBUG_HERO));

        dao.saveList(FileNames.USER_HEROES.name(), heroes);

        dao.getAllElements(FileNames.USER_HEROES.name()).forEach(System.out::println);
    }
}
