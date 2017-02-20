package centurion.model.model;

import centurion.model.dao.DAO;
import centurion.model.enumerations.CombatStatus;
import centurion.model.generator.DamageGenerator;
import centurion.model.manager.CombatStateManager;
import centurion.model.manager.StateManager;

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

    private Throw lastNumberThrownByUser;
    private int lastDamageCreatedByMonster;
    private Hero hero;
    private Monster monster;
    private static AllCreaturesProcessedTeller finishedTeller;
    private CombatStatus status;
    private ArrayList<Item> itemsInStock;
    private ArrayList<Item> itemsUsedInThisState;
    private static DAO dao;
    private static StateManager stateManager;
    private static int throwCountCurrently;
    private int throwCount;

    /**
     * Constructor to be called when first <code>CombatState</code> gets initialized.
     *  Makes sure that <code>throwCountCurrently</code> get correctly initialized.
     *
     * @param dao                       DAO for file access.
     * @param throwCountStartingPoint   Global count for number of user's throws registered.
     */
    public CombatState(DAO dao, int throwCountStartingPoint){
        throwCountCurrently = throwCountStartingPoint;
        initialize(dao);
    }

    /**
     * Constructor used when duplicating the state.
     *
     * @param dao                       DAO for file access.
     */
    public CombatState(DAO dao) {
        initialize(dao);
        throwCount = ++throwCountCurrently % 3;
    }

    /**
     * Initializes some of the state's variables correctly.
     */
    private void initialize(DAO dao){
        CombatState.dao = dao;
        finishedTeller = new AllCreaturesProcessedTeller();
        status = CombatStatus.BEFORE_FIRST_ROUND_OF_ABILITIES;
        itemsInStock = dao.getAllElements(Item.class) != null ? dao.getAllElements(Item.class) : new ArrayList<>();
        itemsUsedInThisState = new ArrayList<>();
    }

    /**
     * Makes an user's <code>Item</code> available for use in the combat. Depending on the
     *  <code>Item</code> it will last for only the single state or more.
     *
     * @param item  The <code>Item</code> to use.
     * @return      Result if activation of item was successful.
     */
    @Deprecated
    public boolean useItem(Item item) {
        if (!itemsInStock.contains(item))
            throw new IllegalArgumentException("CombatState : activateItem() : Item to be activated isn't in user's stock.");

        // TODO Will most likely fail, add SequentialIDGenerator to Item and override its .equals()-method.
        return itemsUsedInThisState.add(item) && itemsInStock.remove(item);
    }

    /**
     * Retrieve all <code>Item</code>'s the use owns.
     *
     * @return The user's <code>Item</code>s.
     */
    public ArrayList<Item> getItemsInStock() {
        return itemsInStock;
    }

    /**
     * Retrieve all <code>Item</code>'s the use currently uses.
     *
     * @return The user's <code>Item</code>s currently in use.
     */
    public ArrayList<Item> getItemsUsedInThisState(){
        return itemsUsedInThisState;
    }

    /**
     * Process the used <code>Item</code>'s <code>Ability</code>s to make
     *  them effectively operational.
     */
    private void processUsedItemsAbilities(){
        itemsUsedInThisState.forEach(item -> item.getAbilities()
                .forEach(ability -> ability
                        .ability(CombatStateManager.getInstance(this, dao))));
    }

    /**
     * Remove every item currently in use which lifetime has ended.
     */
    private void removeDeadItems(){
        itemsUsedInThisState.removeIf(item -> item.decrementThrowsCount() == 0);
    }

    /**
     * Observer-method. Listens to centurion.model.input from user.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Integer) {
            System.out.println("The user has hit the field " + arg + ", Combat has been notified.");

            // Update 'lastNumberThrownByUser'.
            this.setLastNumberThrownByUser((Throw) arg);

            // Decrease the monster's hp.
            if(lastNumberThrownByUser instanceof ValidThrow){
                ValidThrow t = (ValidThrow) lastNumberThrownByUser;
                this.monster.setHp(this.monster.getHp() - t.getScore());
            }
        }

        // Process hero's and monster's abilities + items.
        tellObservers();

        if(throwCountCurrently == 0 && throwCount != 0){

            // TODO Replace '0' used for bonus evasion with non-dummy.
            hero.setHp(hero.getHpTotal() - DamageGenerator.calculateMonsterDamage(monster,hero, 0));
        }

        /*
         * Tell every observer who wants to know when the combat
         *  has finished.
         */
        finishedTeller.tell();
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
        notifyObservers(stateManager);
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
        notifyObservers(stateManager);
        processUsedItemsAbilities();
    }

    @Deprecated
    public void deleteAllObservers() {
        this.deleteObservers();
    }

    @Deprecated
    public void deleteSpecificObserver(Observer o) {
        this.deleteObserver(o);
    }

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
        stateManager = s;
        finishedTeller.addObserver(s);
        return this;
    }

    public CombatStatus getStatus() {
        return status;
    }

    public Throw getLastNumberThrownByUser() {
        return lastNumberThrownByUser;
    }

    public void setLastNumberThrownByUser(Throw lastNumberThrownByUser) {
        this.lastNumberThrownByUser = lastNumberThrownByUser;
    }

    public Hero getHero() {
        return hero;
    }

    public Monster getMonster() {
        return monster;
    }

    public int getLastDamageCreatedByMonster() {
        return lastDamageCreatedByMonster;
    }

    public void setLastDamageCreatedByMonster(int i) {
        lastDamageCreatedByMonster = i;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Helper to tell related observers that this combat state has reached its end,
     *  meaning that every operation has been performed.
     */
    public class AllCreaturesProcessedTeller extends Observable {

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
