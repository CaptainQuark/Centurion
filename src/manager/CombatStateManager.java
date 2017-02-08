package manager;

import java.util.Observable;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class CombatStateManager extends StateManager<CombatState> {

    public CombatStateManager(CombatState t) {
        super(t);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CombatState.AllCreaturesProcessedTeller) {
            //System.out.println("Every creature has been processed, autoPush() going to be called.");
            this.autoPush();
        }
    }
}
