package centurion.model.playground;

import centurion.model.enumerations.Biome;
import centurion.model.enumerations.MonsterDifficulty;
import centurion.model.enumerations.MonsterType;
import centurion.model.factory.MonsterFactory;
import centurion.model.generator.MonsterGenerator;
import centurion.model.helper.ODSFileHelper;
import centurion.model.helper.Preference;
import centurion.model.helper.StandardPathHelper;
import centurion.model.model.Monster;

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
        System.out.println(centurion.model.generator.getValue(MonsterValues.NAME));
        System.out.println("Evasion: " + centurion.model.generator.getValue(MonsterValues.EVASION));
        System.out.println("HP: " + centurion.model.generator.getValue(MonsterValues.HP));
        System.out.println("Checkout: " + centurion.model.generator.getValue(MonsterValues.CHECKOUT));
        System.out.println("Crit chance: " + centurion.model.generator.getValue(MonsterValues.CRIT_CHANCE));
        System.out.println("Crit multiplier: " + centurion.model.generator.getValue(MonsterValues.CRIT_MULITPLIER));
        System.out.println("Accuracy: " + centurion.model.generator.getValue(MonsterValues.ACCURACY));
        System.out.println("Allowed biome: " + centurion.model.generator.getValue(MonsterValues.ALLOWED_BIOME));
        System.out.println("Scaling dmg: " + centurion.model.generator.getValue(MonsterValues.SCALING_DMG));
        System.out.println("Difficulty: " + centurion.model.generator.getValue(MonsterValues.DIFFICULTY));
        System.out.println("Type: " + centurion.model.generator.getValue(MonsterValues.TYPE));
    }
    */
}
