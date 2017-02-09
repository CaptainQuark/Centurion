package playground;

import dao.SerialDAO;
import enumerations.CombatStatus;
import enumerations.HeroType;
import enumerations.MonsterType;
import factory.HeroFactory;
import factory.MonsterFactory;
import input.InputSimulator;
import manager.CombatState;
import manager.CombatStateManager;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class CombatStateManagerSample implements Observer {

    public static void main(String...args){

        CombatState combatManager = new CombatState(new SerialDAO())
                .setHero(HeroFactory.getInstance().produce(HeroType.DEBUG_HERO))
                .setMonster(MonsterFactory.getInstance().produce(MonsterType.DEBUG_MONSTER));

        InputSimulator input = new InputSimulator();

        // Set the initial state.
        CombatStateManager combatStateManager = new CombatStateManager(combatManager, new SerialDAO());

        // Tell the CombatState that someone (= StateManager) wants to observe him.
        combatStateManager.peek().setStateManagerObserver(combatStateManager);

        // Register CombatState as observer for inputs.
        input.addObserver(combatStateManager.peek());

        // Simulate some throws by the user.

        // Monster's ability should fire twice.
        input.addThrow(160);
        System.out.println();

        System.out.println("Number of states: " + combatStateManager.getNumOfStates());
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof CombatStatus){
            CombatStatus s = (CombatStatus) arg;

            System.out.println("Combat has now status : " + s.name());
        }
    }
}
