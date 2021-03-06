package centurion.model.model;

import centurion.model.dao.DAO;
import centurion.model.dao.SerialDAO;
import centurion.model.enumerations.FileNames;

import java.util.ArrayList;
import java.util.List;

/**
 * Every creature the user can control.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class Hero extends AbstractCreature {

    /**
     * Amount of money required to buy
     * an instance of <tt>Hero</tt>.
     * May change late in the game.
     */
    private double purchaseCosts;
    private String primaryFaction;
    private String secondaryFaction;
    private boolean inMedication = false;
    private String activeFaction;

    public Hero(String name, String primaryFaction, String secondaryFaction, int hpTotal, int bonusNumber, int evasion, double purchaseCosts) {
        super(name, hpTotal, bonusNumber, evasion);

        this.purchaseCosts = purchaseCosts;
        this.primaryFaction = primaryFaction;
        this.secondaryFaction = secondaryFaction;

        // Default the active faction to the hero's primary.
        this.activeFaction = primaryFaction;
    }

    public String getActiveFaction(){
        return activeFaction;
    }

    public void setActiveFaction(String faction){
        if(!faction.equals(primaryFaction) || !faction.equals(secondaryFaction))
            throw new IllegalArgumentException("AbstractCreature : setActiveFaction() : Given faction isn't part of the creature.");

        activeFaction = faction;
    }

    public double getPurchaseCosts() {
        return this.purchaseCosts;
    }

    public String getPrimaryFaction() {
        return this.primaryFaction;
    }

    public String getSecondaryFaction() {
        return this.secondaryFaction;
    }

    public boolean getInMedication() {
        return inMedication;
    }

    public void setInMedication(boolean b) {
        inMedication = b;
    }

    @Override
    public String toString() {
        String n = System.getProperty("line.separator");
        return super.toString()
                + "Purchase costs: " + purchaseCosts + n
                + "1. Faction: " + primaryFaction + n
                + "2. Faction: " + secondaryFaction + n
                + "In medication? : " + inMedication + n;
    }

    @Override
    protected List<AbstractCreature> provideAllElements() {
        ArrayList<AbstractCreature> everyHero = new ArrayList<>();
        DAO dao = new SerialDAO();

        // Make sure to use every hero in the game.
        if (dao.getAllElements(FileNames.USER_HEROES.name()) != null)
            everyHero.addAll(dao.getAllElements(FileNames.USER_HEROES.name()));

        if (dao.getAllElements(FileNames.MERCHANT_HEROES.name()) != null)
            everyHero.addAll(dao.getAllElements(FileNames.MERCHANT_HEROES.name()));

        return everyHero;
    }
}
