package playground;

import enumerations.HeroType;
import enumerations.MonsterType;
import factory.HeroFactory;
import factory.MonsterFactory;
import input.InputSimulator;
import manager.CombatManager;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class Playground {

    public static void main(String...args){

        // '.start()' currently has no effect.

        CombatManager.getInstance()
                .setHero(HeroFactory.getInstance().produce(HeroType.DEBUG_HERO))
                .setMonster(MonsterFactory.getInstance().produce(MonsterType.DEBUG_MONSTER))
                .start();

        InputSimulator input = new InputSimulator();

        input.addObserver(CombatManager.getInstance());

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

        CombatManager.terminate();
    }
}
