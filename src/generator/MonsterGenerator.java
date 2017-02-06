package generator;

import enumerations.Biome;
import enumerations.MonsterDifficulty;
import helper.ODSFileHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class MonsterGenerator {

    private int index;
    private final ArrayList<HashMap<String, ArrayList<String>>> table;

    public MonsterGenerator(ArrayList<HashMap<String, ArrayList<String>>> table, Biome biome, MonsterDifficulty difficulty) {
        this.table = table;

        ArrayList<String> restrictedBiomes = ODSFileHelper.extractColumn(table, "RESTRICTED_BIOME");
        ArrayList<Integer> rarities = ODSFileHelper.castToInteger(ODSFileHelper.extractColumn(table, "RARITY"));

        // TODO Check if column-titles are matching (see HeroGenerator);

        // Calculate rarity.
        int seed = (int) (Math.random() * 100);
        final int rarity =
                seed <= 70 ? 1 :
                        seed <= 90 ? 2 : 3;

        // Calculate index.
        int numEntries = ODSFileHelper.extractColumn(table, "NAME").size();

        do {
            index = new Random().nextInt(numEntries);
        } while (rarity != rarities.get(index)
                || isBiomeSelectionInvalid(biome.name(), ODSFileHelper.extractMultipleValuesInSingleCells(restrictedBiomes, ",").get(index))
                || ! ODSFileHelper.extractColumn(table, "DIFFICULTY").get(index).equals(difficulty.name()));
    }

    private static boolean isBiomeSelectionInvalid(String validBiome, ArrayList<String> restrictedBiomes) {
        for (String s : restrictedBiomes)
            if (s.equals(validBiome))
                return true;

        return false;
    }

    public MonsterDifficulty getDifficulty(MonsterDifficulty difficulty) {

        // Calculate difficulty.
        switch (difficulty) {
            case MEDIUM:
                difficulty = (int) (Math.random() * 100) >= 33 ? MonsterDifficulty.MEDIUM : MonsterDifficulty.EASY;
                break;
            case STRONG:
                difficulty = (int) (Math.random() * 100) >= 33 ? MonsterDifficulty.STRONG : MonsterDifficulty.MEDIUM;
        }

        return difficulty;
    }

    public String getName() {
        return ODSFileHelper.extractColumn(table, "NAME").get(index);
    }

    public String getCheckout() {
        return ODSFileHelper.extractColumn(table, "CHECKOUT").get(index);
    }

    public String getName() {
        return ODSFileHelper.extractColumn(table, "NAME").get(index);
    }

    public String getName() {
        return ODSFileHelper.extractColumn(table, "NAME").get(index);
    }

    public String getName() {
        return ODSFileHelper.extractColumn(table, "NAME").get(index);
    }

    public String getName() {
        return ODSFileHelper.extractColumn(table, "NAME").get(index);
    }

}
