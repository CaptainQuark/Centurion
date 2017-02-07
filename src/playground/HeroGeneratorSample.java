package playground;

import enumerations.HeroType;
import factory.HeroFactory;
import generator.HeroGenerator;
import helper.ODSFileHelper;
import helper.Preference;
import helper.StandardPathHelper;
import model.Hero;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class HeroGeneratorSample {

    private static File f = new File(StandardPathHelper.getInstance().getDataPath() + Preference.getInstance().HERO_DATA_FILE);
    private static ArrayList<HashMap<String, ArrayList<String>>> table = ODSFileHelper.readODSAtTab(f, 0);
    private static HeroGenerator generator = new HeroGenerator(table);

    public static void main(String... args) {
        factoryDemo();
    }

    static void factoryDemo(){
        Hero h = HeroFactory.getInstance().produce(HeroType.DEBUG_HERO);
        System.out.println(h);
    }

    static void simpleDemo(){
        System.out.println(generator.getName());
        System.out.println(generator.getFaction("NO_EFFECT", "Primary"));
        System.out.println(generator.getFaction("NO_EFFECT", "Secondary"));
        System.out.println("HP: " + generator.getHitpoints());
        System.out.println("Evasion: " + generator.getEvasion());
        System.out.println("Costs: " + generator.getPurchaseCosts());
    }
}
