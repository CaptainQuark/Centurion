package playground;

import dao.DAO;
import dao.SerialDAO;
import enumerations.HeroType;
import enumerations.MonsterType;
import factory.HeroFactory;
import factory.MonsterFactory;
import helper.Preference;
import helper.StandardPathHelper;

import java.io.File;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class DAOSample {

    static DAO dao = new SerialDAO();

    public static void main(String...args){
        savePreferences();
        loadPreferences();
    }

    private static void delete(){
        if(new File(StandardPathHelper.getInstance().getRootPath() + "\\Preference.ser").delete()){
            System.out.println("File has been removed");
        }
    }

    private static void loadPreferences(){
        Preference.setInstance(dao.getSingleElement(Preference.class));
        Preference.getInstance().monsters.forEach(System.out::println);
        Preference.getInstance().heros.forEach(System.out::println);
    }

    private static void savePreferences(){

        for(int i = 0; i < 3; i++)
            Preference.getInstance().monsters.add(MonsterFactory.getInstance().produce(MonsterType.DEBUG_MONSTER));


        for(int i = 0; i < 3; i++)
            Preference.getInstance().heros.add(HeroFactory.getInstance().produce(HeroType.DEBUG_HERO));

        dao.saveObject(Preference.class, Preference.getInstance());
    }
}
