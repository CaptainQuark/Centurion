package centurion.model.playground;

import centurion.model.dao.SerialDAO;
import centurion.model.enumerations.CombatStatus;
import centurion.model.enumerations.MonsterType;
import centurion.model.factory.MonsterFactory;
import centurion.model.input.InputSimulator;
import centurion.model.manager.CombatStateManager;
import centurion.model.manager.CreatureManager;
import centurion.model.model.CombatState;
import centurion.model.model.ValidThrow;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class CombatStateManagerSample implements Observer {

    public static void main(String...args){

        CombatState combatManager = new CombatState(new SerialDAO())
                .setHero(CreatureManager.getInstance(new SerialDAO()).getHeroes().get(0))
                .setMonster(MonsterFactory.getInstance().produce(MonsterType.DEBUG_MONSTER));

        InputSimulator input = new InputSimulator();

        // Set the initial state.
        //CombatStateManager combatStateManager = new CombatStateManager(combatManager, new SerialDAO());

        // Tell the latest (= current and only) CombatState that someone (= StateManager) wants to observe him.
        CombatStateManager.getInstance(combatManager, new SerialDAO()).peek()
                .setStateManagerObserver(CombatStateManager.getInstance(combatManager, new SerialDAO()));

        // Register CombatState as observer for inputs.
        input.addObserver(CombatStateManager.getInstance(combatManager, new SerialDAO()).peek());

        // Simulate some throws by the user.

        // Monster's ability should fire twice.
        input.addThrow(new ValidThrow(20, ValidThrow.Multiplier.THREE));
        System.out.println();

        System.out.println("Number of states: " + CombatStateManager.getInstance(combatManager, new SerialDAO()).getNumOfStates());
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof CombatStatus){
            CombatStatus s = (CombatStatus) arg;

            System.out.println("Combat has now status : " + s.name());
        }
    }
}
