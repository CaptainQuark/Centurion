package manager;

import java.util.Observable;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class CombatStateManager extends StateManager<CombatManager> {

    public CombatStateManager(CombatManager t) {
        super(t);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CombatManager.LastCreatureProcessed) {
            //System.out.println("Every creature has been processed, autoPush() going to be called.");
            this.autoPush();
        }
    }
}
