package centurion.model.playground;

import centurion.model.dao.DAO;
import centurion.model.dao.SerialDAO;
import centurion.model.enumerations.HeroType;
import centurion.model.enumerations.MonsterType;
import centurion.model.factory.HeroFactory;
import centurion.model.factory.MonsterFactory;
import centurion.model.helper.Preference;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class DAOSample {

    private static DAO dao = new SerialDAO();

    public static void main(String...args){
        loadPreferences();
        savePreferences();
        loadPreferences();

        /*
        if(centurion.model.dao.removeElementContainer(StandardPathHelper.getInstance().getRootPath() + "Preference.ser"))
            System.out.println("Removal of file was successful.");
            */
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
