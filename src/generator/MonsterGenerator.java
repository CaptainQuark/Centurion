package generator;

import enumerations.Biome;
import enumerations.MonsterDifficulty;
import enumerations.MonsterValues;
import helper.ODSFileHelper;
import org.jdom2.IllegalDataException;

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
    private final MonsterDifficulty newDifficulty;

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
        newDifficulty = calculateDifficulty(difficulty);

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

    public String getValAsString(MonsterValues v){
        return ODSFileHelper.extractColumn(table,v.name()).get(index);
    }

    public int getValAsInt(MonsterValues v){
        return Integer.parseInt(ODSFileHelper.extractColumn(table,v.name()).get(index));
    }

    public double getValAsDouble(MonsterValues v){
        return Double.parseDouble(ODSFileHelper.extractColumn(table,v.name()).get(index));
    }
}
