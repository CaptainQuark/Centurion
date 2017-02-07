package playground;

import enumerations.Biome;
import enumerations.MonsterDifficulty;
import enumerations.MonsterType;
import factory.MonsterFactory;
import generator.MonsterGenerator;
import helper.ODSFileHelper;
import helper.Preference;
import helper.StandardPathHelper;
import model.Monster;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class MonsterGeneratorSample {

    private static File f = new File(StandardPathHelper.getInstance().getDataPath() + Preference.getInstance().MONSTER_DATA_FILE);
    private static ArrayList<HashMap<String, ArrayList<String>>> table = ODSFileHelper.readODSAtTab(f, 0);
    private static MonsterGenerator generator = new MonsterGenerator(table, Biome.Forest, MonsterDifficulty.Medium);

    public static void main(String...args){

        demoFactory();
        //demoGenerator();
    }

    private static void demoFactory(){
        Monster m = MonsterFactory.getInstance().produce(MonsterType.DEBUG_MONSTER);
        System.out.println(m);
    }

    /*
    private static void demoGenerator(){
        System.out.println(generator.getValue(MonsterValues.NAME));
        System.out.println("Evasion: " + generator.getValue(MonsterValues.EVASION));
        System.out.println("HP: " + generator.getValue(MonsterValues.HP));
        System.out.println("Checkout: " + generator.getValue(MonsterValues.CHECKOUT));
        System.out.println("Crit chance: " + generator.getValue(MonsterValues.CRIT_CHANCE));
        System.out.println("Crit multiplier: " + generator.getValue(MonsterValues.CRIT_MULITPLIER));
        System.out.println("Accuracy: " + generator.getValue(MonsterValues.ACCURACY));
        System.out.println("Allowed biome: " + generator.getValue(MonsterValues.ALLOWED_BIOME));
        System.out.println("Scaling dmg: " + generator.getValue(MonsterValues.SCALING_DMG));
        System.out.println("Difficulty: " + generator.getValue(MonsterValues.DIFFICULTY));
        System.out.println("Type: " + generator.getValue(MonsterValues.TYPE));
    }
    */
}
