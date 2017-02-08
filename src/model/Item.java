package model;

import java.util.ArrayList;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class Item {

    private final String name;
    private final ArrayList<Ability> abilities;

    public Item(String name, ArrayList<Ability> abilities){
        this.name = name;
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }
}
