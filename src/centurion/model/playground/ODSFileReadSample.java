package centurion.model.playground;

import centurion.model.helper.ODSFileHelper;
import centurion.model.helper.Preference;
import centurion.model.helper.StandardPathHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class ODSFileReadSample {

    public static void main(String...args){

        File f = new File(StandardPathHelper.getInstance().getDataPath() + Preference.getInstance().HERO_DATA_FILE);
        //ArrayList<String> values = ODSFileHelper.extractColumn(ODSFileHelper.readODSAtTab(f, 0), "FACTION");
        ArrayList<String> values = ODSFileHelper.extractColumnTitles(ODSFileHelper.readODSAtTab(f, 0));

        values.forEach(System.out::println);
    }
}
