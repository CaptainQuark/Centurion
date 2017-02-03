package manager;

import model.Hero;
import model.Monster;
import java.util.Observable;
import java.util.Observer;

/**
 * Handles combat-scenario of app.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public final class CombatManager extends Observable implements Observer {

    // TODO Delete after initial successful debugging.
    public Integer lastNumberThrownByUser = -1;
    private Hero hero;
    private Monster monster;

    /**
     * Single used instance of CombatManager. There will only
     *  be one combat at the time.
     */
    private static CombatManager instance;

    // TODO Consider changing to take arguments (hero, monster).
    private CombatManager(){}

    /**
     * Really necessary? CombatManager already listens to user input by itself.
     */
    public void start(){
        // Add requiered logic.
    }

    /**
     * Really useful?
     *
     * <a href="http://stackoverflow.com/questions/5342357/ever-need-to-destroy-a-singleton-instance"></a>
     */
    public static void terminate(){
        instance = null;
    }

    // Observer-method. Listens to input from user.
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Integer){
            System.out.println("The user has hit a number, Combat has been notified.");
            this.lastNumberThrownByUser = (Integer) arg;
        }

        tellObservers();
    }

    /**
     * Helper to fire Observer-related update-methods.
     */
    private void tellObservers(){
        setChanged();
        notifyObservers(this);
    }

    public static void deleteAllObservers(){
        instance.deleteObservers();
    }

    public static void deleteSpecificObserver(Observer o){
        instance.deleteObserver(o);
    }

    /*
     * Getters and setters.
     */

    public CombatManager setHero(Hero h){
        instance.addObserver(hero = h);
        return instance;
    }

    public CombatManager setMonster(Monster m){
        instance.addObserver(monster = m);
        return instance;
    }

    public Hero getHero(){
        return this.hero;
    }

    public Monster getMonster(){
        return monster;
    }

    /**
     * <tt>CombatManger</tt>'s interface to get its instance.
     *
     * @return  Instance of <tt>CombatManger</tt>.
     */
    public static CombatManager getInstance(){
        return instance == null ? instance = new CombatManager() : instance;
    }
}
