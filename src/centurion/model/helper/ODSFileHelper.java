package centurion.model.helper;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Reads '.ods'-files and provided multiple methods to work with them.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class ODSFileHelper {

    /**
     * Read a given '.ods'-file at one of its sheets.
     * (Starting with 0).
     *
     * @param file       The .ods-file to read from.
     * @param sheetIndex Index of the internal sheet.
     */
    public static ArrayList<HashMap<String, ArrayList<String>>> readODSAtTab(File file, final int sheetIndex) {

        ArrayList<HashMap<String, ArrayList<String>>> columns = new ArrayList<>();

        try {
            //Getting the 0th sheet for manipulation| pass sheet name as string
            Sheet sheet = SpreadSheet.createFromFile(file).getSheet(sheetIndex);

            //Get row count and column count
            int nColCount = sheet.getColumnCount();
            int nRowCount = sheet.getRowCount();

            // Start reading data by column.
            for (int columnIndex = 0; columnIndex < nColCount; columnIndex++) {

                ArrayList<String> columnEntries = new ArrayList<>();

                // Important: '1' as starting index was chosen because '0' is the column's name!
                for (int rowIndex = 1; rowIndex < nRowCount; rowIndex++) {
                    columnEntries.add(sheet.getCellAt(columnIndex, rowIndex).getTextValue());
                }

                String name = sheet.getCellAt(columnIndex, 0).getTextValue();
                HashMap<String, ArrayList<String>> entries = new HashMap<>();

                entries.put(name, columnEntries);
                columns.add(entries);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return columns;
    }

    public static ArrayList<String> extractColumn(final ArrayList<HashMap<String, ArrayList<String>>> table, final String columnName) {
        for (HashMap<String, ArrayList<String>> column : table) {
            if (column.containsKey(columnName))
                return column.get(columnName);
        }

        // At this point, no valid table name matches the given one.
        throw new IllegalArgumentException("No matching column name was provided.");
    }

    public static ArrayList<String> extractColumnTitles(final ArrayList<HashMap<String, ArrayList<String>>> table){
        ArrayList<String> names = new ArrayList<>();
        table.forEach(c -> c.entrySet().forEach(e -> names.add(e.getKey())));

        return names;
    }

    public static ArrayList<Integer> castToInteger(final ArrayList<String> strings) {
        ArrayList<Integer> integers = new ArrayList<>();
        strings.forEach(s -> integers.add(Integer.parseInt(s)));

        return integers;
    }

    public static ArrayList<Double> castToDouble(final ArrayList<String> strings) {
        ArrayList<Double> doubles = new ArrayList<>();
        strings.forEach(s -> doubles.add(Double.parseDouble(s)));

        return doubles;
    }

    public static ArrayList<ArrayList<String>> extractMultipleValuesInSingleCells(final ArrayList<String> cells, final String splitter) {

        if(splitter == null || splitter.equals(""))
            throw new IllegalArgumentException("Splitter has to contain at least one character.");

        ArrayList<ArrayList<String>> cleanCells = new ArrayList<>();

        for (String s : cells) {

            // Remove whitespace, tabs, etc.
            s = s.replaceAll("\\s+", "");
            ArrayList<String> extractedValues = new ArrayList<>();

            Collections.addAll(extractedValues, s.split(splitter));

            cleanCells.add(extractedValues);
        }

        return cleanCells;
    }

    public static boolean doColumnTitlesExist(ArrayList<String> titles, ArrayList<HashMap<String, ArrayList<String>>> table) {
        for (String s : ODSFileHelper.extractColumnTitles(table))
            if (!titles.contains(s))
                return false;

        return true;
    }
}
