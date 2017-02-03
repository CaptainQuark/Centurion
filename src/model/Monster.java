package model;

import enumerations.Biome;

import java.util.Observable;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class Monster extends AbstractCreature {

    private final Biome biome;

    public Monster(String name, int hpTotal, int bonusNumber, int evasion, Biome biome) {
        super(name, hpTotal, bonusNumber, evasion);

        this.biome = biome;
    }

    public Biome getBiome(){
        return biome;
    }
}
