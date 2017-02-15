package centurion.model.manager;

import centurion.model.dao.DAO;
import centurion.model.enumerations.CombatStatus;
import centurion.model.model.CombatState;
import centurion.model.model.Item;

import java.util.Observable;

/**
 * Class to manage stack of <code>CombatState</code>s.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class CombatStateManager extends StateManager<CombatState> {

    private static DAO dao;
    private static CombatStateManager instance;

    public static CombatStateManager getInstance(CombatState t, DAO dao) {
        return instance == null ? instance = new CombatStateManager(t, dao) : instance;
    }

    private CombatStateManager(CombatState t, DAO dao) {
        super(t);
        CombatStateManager.dao = dao;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CombatState.AllCreaturesProcessedTeller) {
            CombatState.AllCreaturesProcessedTeller c = (CombatState.AllCreaturesProcessedTeller) o;

            if(c.getStatus().equals(CombatStatus.FINISHED)){
                dao.saveList(Item.class, c.getItemsInStock());
                System.out.println("Combat has ended.");
                tellObserversThatCombatHasFinished();
            }
            else{
                // The process-cycle of a CombatState has finished, so put
                //  the current state back and start working on a new one.
                this.autoPush();
            }
        }
    }

    private void tellObserversThatCombatHasFinished(){
        setChanged();
        notifyObservers(CombatStatus.FINISHED);
    }
}
