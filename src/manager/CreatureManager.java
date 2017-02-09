package manager;

import dao.DAO;
import model.Hero;
import model.Monster;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Holds operations regarding the management
 * of creatures.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class CreatureManager {

    private static CreatureManager instance;
    private static DAO dao;
    private ArrayList<Hero> heroes;
    private ArrayList<Monster> monsters;

    private CreatureManager(DAO dao) {
        CreatureManager.dao = dao;
        heroes = retrieveAllHeros() != null ? retrieveAllHeros() : new ArrayList<>();
        monsters = retrieveAllMonsters() != null ? retrieveAllMonsters() : new ArrayList<>();
    }

    public static CreatureManager getInstance(DAO dao) {
        return instance == null ? instance = new CreatureManager(dao) : instance;
    }

    public boolean addHero(Hero h){
        return heroes.add(h) && dao.saveList(Hero.class, heroes);
    }

    public boolean addMonster(Monster m){
        return monsters.add(m) && dao.saveList(Monster.class, monsters);
    }

    public boolean removeHero(Hero h){
        return dao.removeElement(h) && heroes.remove(h);
    }

    public boolean removeMonster(Monster m){
        return dao.removeElement(m) && monsters.remove(m);
    }

    public ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    @Nullable
    private static ArrayList<Hero> retrieveAllHeros() {
        return dao.getAllElements(Hero.class) != null
                ? dao.getAllElements(Hero.class).stream().filter(c -> c instanceof Hero).map(c -> (Hero) c).collect(Collectors.toCollection(ArrayList::new))
                : null;
    }

    @Nullable
    private static ArrayList<Monster> retrieveAllMonsters() {
        return dao.getAllElements(Monster.class) != null
                ? dao.getAllElements(Monster.class).stream().filter(c -> c instanceof Monster).map(c -> (Monster) c).collect(Collectors.toCollection(ArrayList::new))
                : null;
    }
}
