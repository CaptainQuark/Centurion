package centurion.model.generator;

import centurion.model.enumerations.Biome;
import centurion.model.enumerations.Checkout;
import centurion.model.enumerations.MonsterDifficulty;
import centurion.model.enumerations.MonsterValues;
import centurion.model.helper.ODSFileHelper;
import centurion.model.model.Monster;
import org.jdom2.IllegalDataException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class MonsterGenerator {

    private int index;
    private int bonusNumber = -1;
    private final ArrayList<HashMap<String, ArrayList<String>>> table;

    public MonsterGenerator(ArrayList<HashMap<String, ArrayList<String>>> table, Biome biome, MonsterDifficulty difficulty) {
        this.table = table;

        checkIfColumnTitlesAreCorrect();

        ArrayList<String> restrictedBiomes = ODSFileHelper.extractColumn(table, MonsterValues.ALLOWED_BIOME.name());
        ArrayList<Integer> rarities = ODSFileHelper.castToInteger(ODSFileHelper.extractColumn(table, MonsterValues.RARITY.name()));

        // Calculate rarity.
        int seed = (int) (Math.random() * 100);
        final int rarity =
                seed <= 70 ? 1 :
                        seed <= 90 ? 2 : 3;

        // Update difficulty.
        MonsterDifficulty newDifficulty = calculateDifficulty(difficulty);

        // Calculate index.
        int numEntries = ODSFileHelper.extractColumn(table, MonsterValues.NAME.name()).size();

        do {
            index = new Random().nextInt(numEntries);

        } while (rarity != rarities.get(index)
                || ! isBiomeSelectionValid(biome.name(), ODSFileHelper.extractMultipleValuesInSingleCells(restrictedBiomes, ",").get(index))
                || ! ODSFileHelper.extractColumn(table, MonsterValues.DIFFICULTY.name()).get(index).equals(newDifficulty.name()));
    }

    private static boolean isBiomeSelectionValid(String biomeToProof, ArrayList<String> allowedBiomes) {
        for (String s : allowedBiomes)
            if (s.equals(biomeToProof))
                return true;

        return false;
    }

    private MonsterDifficulty calculateDifficulty(MonsterDifficulty difficulty) {

        // Calculate difficulty.
        switch (difficulty) {
            case Medium:
                difficulty = (int) (Math.random() * 100) >= 33 ? MonsterDifficulty.Medium : MonsterDifficulty.Easy;
                break;
            case Strong:
                difficulty = (int) (Math.random() * 100) >= 33 ? MonsterDifficulty.Strong : MonsterDifficulty.Medium;
        }

        return difficulty;
    }

    private void checkIfColumnTitlesAreCorrect(){
        ArrayList<String> columnTitles = new ArrayList<>();

        // Get set of column names according to the HeroValues-enum.
        for (int i = 0; i < MonsterValues.values().length; i++)
            columnTitles.add(MonsterValues.values()[i].toString());

        // Check if those names are different from the ones in the file. If so, throw an exception.
        if(!ODSFileHelper.doColumnTitlesExist(columnTitles, table))
            throw new IllegalDataException("Table's column-titles are different from what they should be.");
    }

    private String getValAsString(MonsterValues v){
        return ODSFileHelper.extractColumn(table,v.name()).get(index);
    }

    private int getValAsInt(MonsterValues v){
        return Integer.parseInt(ODSFileHelper.extractColumn(table,v.name()).get(index));
    }

    private double getValAsDouble(MonsterValues v){
        return Double.parseDouble(ODSFileHelper.extractColumn(table,v.name()).get(index));
    }

    /**
     * Let the caller set a specific {@code bonusNumber} for the {@code Monster}.
     *
     * @param number The {@code bonusNumber} to set.
     * @return {@code MonsterGenerator}-reference to enable builder.
     */
    public MonsterGenerator setBonusNumber(int number) {
        if (number <= 0 || number > 180)
            throw new IllegalArgumentException("MonsterGenerator : setBonusNumber() : Given number is out of bounds.");

        this.bonusNumber = number;
        return this;
    }

    /**
     * If no specific {@code bonusNumber} was set, create one randomly within the game's bounds.
     *
     * @return {@code Integer} within the game's bounds.
     */
    private int generateBonusNumber() {

        // 'origin' is inclusive, 'bound' exclusive.
        return ThreadLocalRandom.current().nextInt(1, 180 + 1);
    }

    public Monster spawn() {
        return new Monster(getValAsString(MonsterValues.NAME),
                getValAsString(MonsterValues.TYPE),
                getValAsInt(MonsterValues.HP),
                bonusNumber != -1 ? bonusNumber : generateBonusNumber(),
                getValAsInt(MonsterValues.EVASION),
                Biome.Forest,
                Checkout.DEFAULT, MonsterDifficulty.Medium,
                getValAsDouble(MonsterValues.RESISTANCE),
                getValAsDouble(MonsterValues.CRIT_MULITPLIER),
                getValAsInt(MonsterValues.DMG_MIN),
                getValAsInt(MonsterValues.DMG_MAX),
                getValAsInt(MonsterValues.BLOCK),
                getValAsInt(MonsterValues.CRIT_CHANCE),
                getValAsInt(MonsterValues.BOUNTY),
                getValAsInt(MonsterValues.ACCURACY));
    }
}
