package helper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class TableHelper<T> {

    public ArrayList<T> extractColumn(ArrayList<HashMap<String, ArrayList<String>>> table, String columnName){
        for(HashMap<String, ArrayList<String>> column : table){
            if(column.containsKey(columnName))
                return (ArrayList<T>) column.get(columnName);
        }

        return null;
    }
}
