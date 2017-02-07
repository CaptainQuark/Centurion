package manager;

import model.Hero;
import model.Monster;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * Handles combat-scenario of app.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public final class CombatManager extends Observable implements Observer, Cloneable, Serializable {

    private int lastNumberThrownByUser;
    private int lastNumberThrownByMonster;
    private Hero hero;
    private Monster monster;
    private AllCreaturesProcessedTeller lastCreatureProcessed;

    // TODO Consider changing to take arguments (hero, monster).
    public CombatManager(){
        lastCreatureProcessed = new AllCreaturesProcessedTeller();
    }

    /**
     * Really necessary? CombatManager already listens to user input by itself.
     */
    public void start(){
        // Add requiered logic.
    }

    // Observer-method. Listens to input from user.
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Integer){
            System.out.println("The user has hit the field " + arg +", Combat has been notified.");
            this.setLastNumberThrownByUser((Integer) arg);
        }

        tellObservers();
    }

    /**
     * Helper to fire Observer-related update-methods.
     */
    private void tellObservers(){
        setChanged();
        notifyObservers(this);

        this.lastCreatureProcessed.tell();
    }

    public void deleteAllObservers(){
        this.deleteObservers();
    }

    public void deleteSpecificObserver(Observer o){
        this.deleteObserver(o);
    }

    /*
     * Getters and setters.
     */

    public CombatManager setHero(Hero h){
        this.hero = h;
        //instance.addObserver(h);
        //return instance;
        this.addObserver(h);
        return this;
    }

    public CombatManager setMonster(Monster m){
        this.monster = m;
        //instance.addObserver(m);
        //return instance;
        this.addObserver(m);
        return this;
    }

    public CombatManager setStateManagerObserver(StateManager s){
        lastCreatureProcessed.addObserver(s);
        return this;
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

    public int getLastNumberThrownByMonster(){
        return lastNumberThrownByMonster;
    }

    public void setLastNumberThrownByMonster(int i){
        lastNumberThrownByMonster = i;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    class AllCreaturesProcessedTeller extends Observable {

        AllCreaturesProcessedTeller(){}

        private void tell(){
            setChanged();
            notifyObservers(this);
        }
    }
}
