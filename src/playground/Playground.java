package playground;

import enumerations.HeroType;
import enumerations.MonsterType;
import factory.HeroFactory;
import factory.MonsterFactory;
import input.InputSimulator;
import manager.CombatManager;
import manager.CombatStateManager;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class Playground {

    public static void main(String...args){

        // '.start()' currently has no effect.

        CombatManager combatManager = new CombatManager()
                .setHero(HeroFactory.getInstance().produce(HeroType.DEBUG_HERO))
                .setMonster(MonsterFactory.getInstance().produce(MonsterType.DEBUG_MONSTER));

        InputSimulator input = new InputSimulator();

        // Set the initial state.
        CombatStateManager combatStateManager = new CombatStateManager(combatManager);

        // Tell the CombatManager that someone (= StateManager) wants to observe him.
        combatStateManager.peek().setStateManagerObserver(combatStateManager);

        // Register CombatManager as observer for inputs.
        input.addObserver(combatStateManager.peek());

        // Simulate some throws by the user.

        // No ability should should start working.
        input.addThrow(55);
        System.out.println();

        // The hero's ability should fire.
        input.addThrow(20);
        System.out.println();

        // The monster's ability should fire.
        input.addThrow(120);
        System.out.println();

        System.out.println("Number of states: " + combatStateManager.getNumOfStates());
    }
}
