package playground;

import helper.ODSFileReader;
import helper.StandardPathHelper;
import helper.TableHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class ODSFileReadSample {

    public static void main(String...args){

        File f = new File(StandardPathHelper.getInstance().getDataPath() + "sample.ods");
        TableHelper<String> entries = new TableHelper<>();
        ArrayList<String> names = entries.extractColumn(ODSFileReader.readODSAtTab(f, 0), "ID");

        names.forEach(System.out::println);
    }
}
