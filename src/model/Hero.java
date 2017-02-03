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

    public Hero(String name, int hpTotal, int bonusNumber, int evasion, double purchaseCosts){
        super(name, hpTotal, bonusNumber, evasion);

        this.purchaseCosts = purchaseCosts;
    }
}
