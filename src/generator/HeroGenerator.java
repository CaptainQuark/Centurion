package generator;

import enumerations.HeroTableColumnNames;
import helper.ODSFileHelper;
import org.jdom2.IllegalDataException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Defines <tt>Hero</tt>'s values in a given boundary.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class HeroGenerator {

    private int primaryIndex = -1;
    private int secondaryIndex = -1;
    private ArrayList<HashMap<String, ArrayList<String>>> table;

    /**
     * Constructor who takes a table as argument and checks the column names for correctness.
     *
     * @param table Table of predefined hero data to read from.
     */
    public HeroGenerator(ArrayList<HashMap<String, ArrayList<String>>> table) {
        ArrayList<String> columnTitles = new ArrayList<>();

        // Get set of column names according to the HeroTableColumnNames-enum.
        for (int i = 0; i < HeroTableColumnNames.values().length; i++)
            columnTitles.add(HeroTableColumnNames.values()[i].toString());

        // Check if those names are different from the ones in the file. If so, throw an exception.
        for (String s : ODSFileHelper.extractColumnTitles(table))
            if (!columnTitles.contains(s))
                throw new IllegalDataException("Table's column-titles are different from what they should be.");

        this.table = table;
    }

    /**
     * Define a name.
     *
     * @return A selected name.
     */
    public String getName() {
        final String[] names = {"Gunther", "Gisbert", "Kamel", "Pepe", "Rudy", "Bow", "Joe",
                "Wiesgart", "Knöllchen", "Speck-O", "Toni", "Brieselbert", "Heinmar",
                "Beowulf", "Hartmut von Heinstein", "Konrad Käsebart", "Clayton Wiesel",
                "Jimmy 'Die Bohne'", "Rob Cross", "Aleksandr Oreshkin", "Joe Cullen", "Brichard Bösel",
                "Der Typ aus der Joghurt-Werbung"};

        // Bound between 0 (inclusive) and 'names.size()' (exclusive).
        return names[new Random().nextInt(names.length)];
    }

    /**
     * Used to generate both primary & secondary categories as well as faction.
     *
     * @return A selected faction.
     */
    public String getFaction(final String validBiome, final String category) {
        ArrayList<String> categories = ODSFileHelper.extractColumn(table, HeroTableColumnNames.CATEGORY.name());
        ArrayList<Integer> rarities = ODSFileHelper.castToInteger(ODSFileHelper.extractColumn(table, HeroTableColumnNames.RARITY.name()));
        ArrayList<String> restrictedBiomes = ODSFileHelper.extractColumn(table, HeroTableColumnNames.RESTRICTED_BIOME.name());

        if (categories == null || rarities == null || restrictedBiomes == null)
            throw new NullPointerException("Some data couldn't be read from the table, at least one column doesn't contain any.");

        if (categories.size() != rarities.size() || categories.size() != restrictedBiomes.size())
            throw new IllegalArgumentException("Lists are unequal in size.");

        //Wählt eine der Seltenheits-Klassen aus
        int r = (int) (Math.random() * 1000);

        int rarity =
                r <= 650 ? 1 :
                        r <= 850 ? 2 :
                                r <= 950 ? 3 : 4;

        int index;

        do {
            index = new Random().nextInt(categories.size());

        } while (!categories.get(index).equals(category)
                || rarity != rarities.get(index)
                || isBiomeSelectionInvalid(validBiome, ODSFileHelper.extractMultipleValuesInSingleCells(restrictedBiomes, ",").get(index)));

        switch (category) {
            case "Primary":
                primaryIndex = index;
                break;
            case "Secondary":
                secondaryIndex = index;
                break;
            default:
                throw new IllegalArgumentException("Wrong category name provided.");
        }

        return ODSFileHelper.extractColumn(table, HeroTableColumnNames.FACTION.name()) != null
                ? ODSFileHelper.extractColumn(table, HeroTableColumnNames.FACTION.name()).get(index) : null;
    }

    /**
     * Define the amount of hitpoints.
     *
     * @return Integer of hitpoints.
     */
    public int getHitpoints() {
        return generateRandomVal(
                ODSFileHelper.castToInteger(ODSFileHelper.extractColumn(table, HeroTableColumnNames.HP.name())).get(primaryIndex),
                ODSFileHelper.castToInteger(ODSFileHelper.extractColumn(table, HeroTableColumnNames.HP.name())).get(secondaryIndex),
                ODSFileHelper.castToDouble(ODSFileHelper.extractColumn(table, HeroTableColumnNames.HP_WEIGHT.name())).get(primaryIndex),
                ODSFileHelper.castToDouble(ODSFileHelper.extractColumn(table, HeroTableColumnNames.HP_WEIGHT.name())).get(secondaryIndex));
    }

    /**
     * Define how high the costs to purchase are.
     *
     * @return Amount of money it needs to buy a hero.
     */
    public int getPurchaseCosts() {
        return generateRandomVal(
                ODSFileHelper.castToInteger(ODSFileHelper.extractColumn(table, HeroTableColumnNames.COSTS.name())).get(primaryIndex),
                ODSFileHelper.castToInteger(ODSFileHelper.extractColumn(table, HeroTableColumnNames.COSTS.name())).get(secondaryIndex),
                ODSFileHelper.castToDouble(ODSFileHelper.extractColumn(table, HeroTableColumnNames.COSTS_WEIGHT.name())).get(primaryIndex),
                ODSFileHelper.castToDouble(ODSFileHelper.extractColumn(table, HeroTableColumnNames.COSTS_WEIGHT.name())).get(secondaryIndex));
    }

    /**
     * Define the hero's evasion.
     *
     * @return Integer representing the evasion value.
     */
    public int getEvasion() {
        return generateRandomVal(
                ODSFileHelper.castToInteger(ODSFileHelper.extractColumn(table, HeroTableColumnNames.EVASION.name())).get(primaryIndex),
                ODSFileHelper.castToInteger(ODSFileHelper.extractColumn(table, HeroTableColumnNames.EVASION.name())).get(secondaryIndex),
                ODSFileHelper.castToDouble(ODSFileHelper.extractColumn(table, HeroTableColumnNames.EVASION_WEIGHT.name())).get(primaryIndex),
                ODSFileHelper.castToDouble(ODSFileHelper.extractColumn(table, HeroTableColumnNames.EVASION_WEIGHT.name())).get(secondaryIndex));
    }

    /**
     * Check if a given biome is valid.
     *
     * @param validBiome       The biome to check.
     * @param restrictedBiomes List of biomes which
     * @return Result if at least one restricted biome matches.
     */
    private static boolean isBiomeSelectionInvalid(String validBiome, ArrayList<String> restrictedBiomes) {
        for (String s : restrictedBiomes)
            if (s.equals(validBiome))
                return true;

        return false;
    }

    /**
     * Little helper method to generate a random value restricted to certain values.
     *
     * @param pVal       Value of primary category.
     * @param sVal       Value of secondary category.
     * @param pValWeight Weight of primary category's value.
     * @param sValWeight Weight of secondary category's value.
     * @return The generated value within the given bounds.
     */
    private static int generateRandomVal(int pVal, int sVal, double pValWeight, double sValWeight) {

        Random random = new Random();
        int valMax = sVal;
        int valMin = pVal;
        double valMaxWeight = sValWeight;
        double valMinWeight = pValWeight;

        if (pVal > sVal) {
            valMax = pVal;
            valMin = sVal;
            valMaxWeight = pValWeight;
            valMinWeight = sValWeight;
        }

        //Man stelle sich einen Boxplot vor; um den Mittelwert herum wird ein Bereich geschaffen,
        //in dem dann die HP zufällig ermittelt werden
        int valMean = (valMax + valMin) / 2;
        int valMaxArea = (int) ((valMean - valMin) / valMaxWeight);
        int valMinArea = (int) ((valMax - valMean) / valMinWeight);
        int valMaxQuartile = valMean + valMaxArea;
        int valMinQuartile = valMean - valMinArea;


        //Wenn die Werte gleich, returne sie (randNext sonst fehlerhaft)
        if (valMaxQuartile == valMinQuartile)
            return valMaxQuartile;

        return random.nextInt(valMaxQuartile - valMinQuartile) + valMinQuartile;
    }
}
