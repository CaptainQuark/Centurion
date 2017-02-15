package centurion.model.manager;

import centurion.model.dao.DAO;
import centurion.model.enumerations.FileNames;
import centurion.model.model.Hero;
import centurion.model.model.Monster;
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
        return heroes.add(h) && dao.saveList(FileNames.USER_HEROES.name(), heroes);
    }

    /*
     * TODO Not useful right now. Either change to retrieve object from Factory or delete at whole.
     */
    @Deprecated
    public boolean addMonster(Monster m){
        return monsters.add(m) && dao.saveList(FileNames.MONSTERS.name(), monsters);
    }

    // TODO Removing won't work due to new way of saving by file-name.
    @Deprecated
    public boolean removeHero(Hero h){
        return dao.removeElement(h) && heroes.remove(h);
    }

    // TODO Removing won't work due to new way of saving by file-name.
    @Deprecated
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
        return dao.<Hero>getAllElements(FileNames.USER_HEROES.name()) != null
                ? dao.<Hero>getAllElements(FileNames.USER_HEROES.name()).stream().filter(c -> c instanceof Hero).map(c -> c).collect(Collectors.toCollection(ArrayList::new))
                : null;
    }

    @Nullable
    private static ArrayList<Monster> retrieveAllMonsters() {
        return dao.<Monster>getAllElements(FileNames.MONSTERS.name()) != null
                ? dao.<Monster>getAllElements(FileNames.MONSTERS.name()).stream().filter(c -> c instanceof Monster).map(c -> c).collect(Collectors.toCollection(ArrayList::new))
                : null;
    }
}
