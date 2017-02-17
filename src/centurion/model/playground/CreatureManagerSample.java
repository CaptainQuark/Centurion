package centurion.model.playground;

import centurion.model.dao.DAO;
import centurion.model.dao.SerialDAO;
import centurion.model.enumerations.FileNames;
import centurion.model.enumerations.HeroType;
import centurion.model.factory.HeroFactory;
import centurion.model.manager.CreatureManager;
import centurion.model.model.Hero;

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

    private static void createHeroes(){
        ArrayList<Hero> heroes = new ArrayList<>();
        for(int i = 0; i < 10; i++){

        }
    }

    private static void demoDAO() {
        DAO dao = new SerialDAO();

        ArrayList<Hero> heroes = new ArrayList<>();
        heroes.add(HeroFactory.getInstance().produce(HeroType.DEBUG_HERO));

        dao.saveList(FileNames.USER_HEROES.name(), heroes);

        dao.getAllElements(FileNames.USER_HEROES.name()).forEach(System.out::println);
    }
}
