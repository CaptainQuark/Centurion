package playground;

import enumerations.HeroType;
import enumerations.MonsterType;
import factory.HeroFactory;
import factory.MonsterFactory;
import input.InputSimulator;
import manager.CombatState;
import manager.CombatStateManager;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class CombatStateManagerSample {

    public static void main(String...args){

        // '.start()' currently has no effect.

        CombatState combatManager = new CombatState()
                .setHero(HeroFactory.getInstance().produce(HeroType.DEBUG_HERO))
                .setMonster(MonsterFactory.getInstance().produce(MonsterType.DEBUG_MONSTER));

        InputSimulator input = new InputSimulator();

        // Set the initial state.
        CombatStateManager combatStateManager = new CombatStateManager(combatManager);

        // Tell the CombatState that someone (= StateManager) wants to observe him.
        combatStateManager.peek().setStateManagerObserver(combatStateManager);

        // Register CombatState as observer for inputs.
        input.addObserver(combatStateManager.peek());

        // Simulate some throws by the user.

        // No ability should start working.
        input.addThrow(300);
        System.out.println();

        System.out.println("Number of states: " + combatStateManager.getNumOfStates());
    }
}
