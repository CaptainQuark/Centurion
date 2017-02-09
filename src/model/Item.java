package model;

import java.util.ArrayList;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class Item {

    private final String name;
    private final ArrayList<Ability> abilities;

    /**
     * Maximum amount of throws the item can last. Use '-1'
     *  to represent persistent use throughout a combat.
     */
    private final int throwsToLastMax;

    /**
     * Current count of how many throws the item will last.
     */
    private int throwsUsed;

    public Item(String name, ArrayList<Ability> abilities, int throwsToLastMax){
        this.name = name;
        this.abilities = abilities;
        this.throwsToLastMax = throwsToLastMax;
        throwsUsed = throwsToLastMax;
    }

    public int decrementThrowsCount(){
        return --throwsUsed;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public int getThrowsToLastMax() {
        return throwsToLastMax;
    }

    public int getThrowsUsed() {
        return throwsUsed;
    }
}
