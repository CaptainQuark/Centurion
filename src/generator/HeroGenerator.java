package generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class HeroGenerator {

    int indexForValuesOfPrimaryFaction = - 1;
    int IndexForValuesOfSecondaryFaction = -1;
    private final ArrayList<HashMap<String, ArrayList<Object>>> table;

    public HeroGenerator(ArrayList<HashMap<String, ArrayList<Object>>> table){
        this.table = table;
    }

    public String generateName(ArrayList<String> names){

        // Bound between 0 (inclusive) and 'names.size()' (exclusive).
        return names.get(new Random().nextInt(names.size()));
    }

    /**
     * Used to generate both primary & secondary classes.
     *
     * @param factions  List of factions.
     * @return          A selected faction.
     */
    public String generateFaction(String validBiome, ArrayList<String> factions, ArrayList<Integer> rarities, ArrayList<String> restrictedBiomes){

        if(factions.size() != rarities.size() || factions.size() != restrictedBiomes.size())
            throw new IllegalArgumentException("Lists are unequal in size.");

        //Wählt eine der Seltenheits-Klassen aus
        int r = (int) (Math.random() * 1000);

        int rarity = r <= 650 ? 1 :
                r <= 850 ? 2 :
                        r <= 950 ? 3 : 4;

        int index;

        do {
            index = new Random().nextInt(factions.size());

        } while (rarity != rarities.get(index)
                && isBiomeSelectionInvalid(validBiome, cleanBiomes(restrictedBiomes).get(index)));

        return factions.get(index);
    }

    public HeroGenerator generateHitpoints(){
        return null;
    }

    public HeroGenerator generatePurchaseCosts(){
        return null;
    }

    public HeroGenerator generateEvasion(){
        return null;
    }

    private static ArrayList<ArrayList<String>> cleanBiomes(ArrayList<String> biomes){

        ArrayList<ArrayList<String>> cleanBiomes = new ArrayList<>();

        for(String s : biomes){

            // Remove whitespace, tabs, etc.
            s = s.replaceAll("\\s+","");
            ArrayList<String> singleBiomes = new ArrayList<>();

            for(String biome : s.split(","))
                singleBiomes.add(biome);

            cleanBiomes.add(singleBiomes);
        }

        return cleanBiomes;
    }

    private static boolean isBiomeSelectionInvalid(String validBiome, ArrayList<String> restrictedBiomes){
        for(String s : restrictedBiomes)
            if(!s.equals(validBiome))
                return false;

        return true;
    }

    /**
     * Little helper method to generate a random value restricted to certain values.
     *
     * @param pVal
     * @param sVal
     * @param pValWeight
     * @param sValWeight
     * @return
     */
    private static int generateRandomVal(int pVal, int sVal, double pValWeight, double sValWeight) {

        Random random = new Random();
        int finalVal;
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

        int valMean = (valMax + valMin) / 2;
        int valMaxArea = (int) ((valMean - valMin) / valMaxWeight);
        int valMinArea = (int) ((valMax - valMean) / valMinWeight);
        int valMaxQuartile = valMean + valMaxArea;
        int valMinQuartile = valMean - valMinArea;
        //Man stelle sich einen Boxplot vor; um den Mittelwert herum wird ein Bereich geschaffen,
        //in dem dann die HP zufällig ermittelt werden

        if(valMaxQuartile == valMinQuartile){
            return valMaxQuartile;
            //Wenn die Werte gleich, returne sie (randNext sonst fehlerhaft)
        }
        finalVal = random.nextInt(valMaxQuartile - valMinQuartile) + valMinQuartile;

        return finalVal;
    }
}
