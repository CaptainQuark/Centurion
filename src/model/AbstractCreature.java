package model;

import generator.SequentialIDGenerator;
import manager.CombatStateManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Representation of every being used
 *  in the application.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public abstract class AbstractCreature extends SequentialIDGenerator<AbstractCreature> implements Observer, Serializable {

    private final String name;
    private final int hpTotal;
    private final int bonusNumber;
    private final int evasion;
    private int hp;
    private ArrayList<Ability> abilities;

    AbstractCreature(String name, int hpTotal, int bonusNumber, int evasion){
        super(true);
        this.name = name;
        this.hpTotal = hpTotal;
        this.bonusNumber = bonusNumber;
        this.hp = hpTotal;
        this.evasion = evasion;
        abilities = new ArrayList<>();
    }

    /**
     * As many abilities as wanted can be added.
     *  Uses Builder-pattern.
     *
     * @param a The <tt>Ability</tt> to add.
     * @return  The instance of the object.
     */
    public AbstractCreature addAbility(final Ability a) {
        abilities.add(a);
        return this;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(arg instanceof CombatStateManager))
            throw new IllegalArgumentException("No CombatStateManager provided for creature.");

        //System.out.println(name + "'s update with CombatState as arg called.");

        // Delegate the CombatManger-object to every own action
        //  so they can operate with it.
        abilities.forEach(a -> a.ability((CombatStateManager) arg));
    }

    /*
     * Getters and setters.
     */

    public String getName() {
        return name;
    }

    public int getHpTotal() {
        return hpTotal;
    }

    public int getBonusNumber() {
        return bonusNumber;
    }

    public int getHp() {
        return hp;
    }

    public ArrayList<Ability> getAbilities(){
        return abilities;
    }

    public AbstractCreature setHp(int hp){
        if(hp <= hpTotal && hp > 0){
            this.hp = hp;
            return this;
        }

        throw new IllegalArgumentException("HP value out of range.");
    }

    public int getEvasion() {
        return evasion;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof AbstractCreature))
            return false;

        if (other == this)
            return true;

        AbstractCreature a = (AbstractCreature) other;

        return a.getElementID() == this.getElementID();
    }

    @Override
    public String toString(){
        String n = System.getProperty("line.separator");
        return "Name: " + name + n
                + "ID: " + getElementID() + n
                + "HP total: " + hpTotal + n
                + "HP currently: " + hp + n
                + "Evasion: " + evasion + n
                + "Bonus number: " + bonusNumber + n;
    }
}
