package manager;

import dao.DAO;
import enumerations.CombatStatus;
import model.Hero;
import model.Item;
import model.Monster;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Handles combat-scenario of app.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public final class CombatState extends Observable implements Observer, Cloneable, Serializable {

    private int lastNumberThrownByUser;
    private int lastNumberThrownByMonster;
    private Hero hero;
    private Monster monster;
    private AllCreaturesProcessedTeller lastCreatureProcessed;
    private CombatStatus status;
    private ArrayList<Item> itemsInStock;
    private ArrayList<Item> itemsUsedInThisState;

    // TODO Consider changing to take arguments (hero, monster).
    public CombatState(DAO dao) {
        lastCreatureProcessed = new AllCreaturesProcessedTeller();
        status = CombatStatus.BEFORE_FIRST_ROUND_OF_ABILITIES;
        itemsInStock = dao.getAllElements(Item.class) != null ? dao.getAllElements(Item.class) : new ArrayList<>();
        itemsUsedInThisState = new ArrayList<>();
    }

    public boolean useItem(Item item) {
        if (!itemsInStock.contains(item))
            throw new IllegalArgumentException("CombatState : activateItem() : Item to be activated isn't in user's stock.");

        return itemsUsedInThisState.add(item) && itemsInStock.remove(item);
    }

    public ArrayList<Item> getItemsInStock() {
        return itemsInStock;
    }

    public ArrayList<Item> getItemsUsedInThisState(){
        return itemsUsedInThisState;
    }

    private void processUsedItemsAbilities(){
        itemsUsedInThisState.forEach(item -> item.getAbilities().forEach(ability -> ability.ability(this)));
    }

    /**
     * Remove every item currently in use which lifetime has ended.
     */
    private void removeDeadItems(){
        itemsUsedInThisState.removeIf(item -> item.decrementThrowsCount() == 0);
    }

    // Observer-method. Listens to input from user.
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Integer) {
            System.out.println("The user has hit the field " + arg + ", Combat has been notified.");
            this.setLastNumberThrownByUser((Integer) arg);
        }

        tellObservers();
    }

    /**
     * Helper to fire Observer-related update-methods.
     */
    private void tellObservers() {
        status = CombatStatus.BEFORE_FIRST_ROUND_OF_ABILITIES;

        /*
         * Tell every observer that a change in state has happened,
         *  implicitly that a throw by the user has been registered.
         */
        setChanged();
        notifyObservers(this);
        processUsedItemsAbilities();

        /*
         * Change te status of this combat's state to indicate that
         *  abilities may have been applied by the creatures.
         */
        status = CombatStatus.AFTER_FIRST_ROUND_OF_ABILITIES;

        /*
         * Active the observers' update method once again.
         */
        setChanged();
        notifyObservers(this);
        processUsedItemsAbilities();

        /*
         * Tell every observer who wants to know when the combat
         *  has finished.
         */
        this.lastCreatureProcessed.tell();
    }

    public void deleteAllObservers() {
        this.deleteObservers();
    }

    public void deleteSpecificObserver(Observer o) {
        this.deleteObserver(o);
    }

    /*
     * Getters and setters.
     */

    public CombatState setHero(Hero h) {
        this.hero = h;
        //instance.addObserver(h);
        //return instance;
        this.addObserver(h);
        return this;
    }

    public CombatState setMonster(Monster m) {
        this.monster = m;
        //instance.addObserver(m);
        //return instance;
        this.addObserver(m);
        return this;
    }

    public CombatState setStateManagerObserver(StateManager s) {
        lastCreatureProcessed.addObserver(s);
        return this;
    }

    public CombatStatus getStatus() {
        return status;
    }

    public int getLastNumberThrownByUser() {
        return lastNumberThrownByUser;
    }

    public void setLastNumberThrownByUser(int lastNumberThrownByUser) {
        this.lastNumberThrownByUser = lastNumberThrownByUser;
    }

    public Hero getHero() {
        return hero;
    }

    public Monster getMonster() {
        return monster;
    }

    public int getLastNumberThrownByMonster() {
        return lastNumberThrownByMonster;
    }

    public void setLastNumberThrownByMonster(int i) {
        lastNumberThrownByMonster = i;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    class AllCreaturesProcessedTeller extends Observable {

        private void tell() {

            removeDeadItems();

            // If a creature is down, set the status accordingly.
            if (monster.getHp() <= 0 || hero.getHp() <= 100)
                status = CombatStatus.FINISHED;

            setChanged();
            notifyObservers(this);
        }

        public ArrayList<Item> getItemsInStock(){
            return itemsUsedInThisState;
        }

        public CombatStatus getStatus(){
            return status;
        }
    }
}
