package model;

import model.AbstractCreature;

/**
 * Every creature the user can control.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class Hero extends AbstractCreature {

    /**
     * Amount of money required to buy
     *  an instance of <tt>Hero</tt>.
     *  May change late in the game.
     */
    private double purchaseCosts;
    private String primaryFaction;
    private String secondaryFaction;

    public Hero(String name, String primaryFaction, String secondaryFaction, int hpTotal, int bonusNumber, int evasion, double purchaseCosts){
        super(name, hpTotal, bonusNumber, evasion);

        this.purchaseCosts = purchaseCosts;
        this.primaryFaction = primaryFaction;
        this.secondaryFaction = secondaryFaction;
    }

    public double getPurchaseCosts(){
        return this.purchaseCosts;
    }

    public String getPrimaryFaction(){
        return this.primaryFaction;
    }

    public String getSecondaryFaction(){
        return this.secondaryFaction;
    }
}
