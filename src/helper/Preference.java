package helper;

import model.Hero;
import model.Monster;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Holds various preferences.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public final class Preference implements Serializable {

    private static Preference instance;
    public final String HERO_DATA_FILE = "hero_resource_table.ods";
    public final String MONSTER_DATA_FILE = "monster_resource_table.ods";
    public ArrayList<Monster> monsters;
    public ArrayList<Hero> heros;

    private Preference(){
        monsters = new ArrayList<>();
        heros = new ArrayList<>();
    }

    public static Preference getInstance(){
        return instance == null ? instance = new Preference() : instance;
    }

    // TODO Check if useful and/or correct.
    public static void setInstance(Preference p){
        instance = p;
    }
}
