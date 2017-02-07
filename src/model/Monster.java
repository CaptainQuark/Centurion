package model;

import enumerations.Biome;
import enumerations.Checkout;
import enumerations.MonsterDifficulty;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class Monster extends AbstractCreature {

    private final Biome biome;
    private final Checkout checkout;
    private final MonsterDifficulty difficulty;
    private final String type;
    private final double resistance;
    private final double critMultiplier;
    private final int minDmg;
    private final int maxDmg;
    private final int block;
    private final int critChance;
    private final int bounty;
    private final int accuracy;

    public Monster(String name, String type, int hpTotal, int bonusNumber, int evasion, Biome biome, Checkout checkout, MonsterDifficulty difficulty,
                   double resistance, double critMultiplier, int minDmg, int maxDmg, int block, int critChance,
                   int bounty, int accuracy) {
        super(name, hpTotal, bonusNumber, evasion);

        this.type = type;
        this.biome = biome;
        this.checkout = checkout;
        this.difficulty = difficulty;
        this.resistance = resistance;
        this.critMultiplier = critMultiplier;
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
        this.block = block;
        this.critChance = critChance;
        this.bounty = bounty;
        this.accuracy = accuracy;
    }

    public Biome getBiome(){
        return biome;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public MonsterDifficulty getDifficulty() {
        return difficulty;
    }

    public double getResistance() {
        return resistance;
    }

    public double getCritMultiplier() {
        return critMultiplier;
    }

    public int getMinDmg() {
        return minDmg;
    }

    public int getMaxDmg() {
        return maxDmg;
    }

    public int getBlock() {
        return block;
    }

    public int getCritChance() {
        return critChance;
    }

    public int getBounty() {
        return bounty;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public String getType(){ return type; }

    @Override
    public String toString(){
        String n = System.getProperty("line.separator");
        return super.toString()
                + "Type: " + type + n
                + "Biome: " + biome.name() + n
                + "Checkout: " + checkout.name() + n
                + "Difficulty: " + difficulty.name() + n
                + "Resistance: " + resistance + n
                + "Crit multiplier: " + critMultiplier + n
                + "Crit chance: " + critChance + n
                + "Damage min: " + minDmg + n
                + "Damage max: " + maxDmg + n
                + "Block: " + block + n
                + "Bounty: " + bounty + n
                + "Accuracy: " + accuracy + n;
    }
}
