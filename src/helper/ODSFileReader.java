package helper;

import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class ODSFileReader {

    /**
     * Read a given '.ods'-file at one of its sheets.
     *  (Starting with 0).
     *
     * @param file         The .ods-file to read from.
     * @param sheetIndex   Index of the internal sheet.
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
            for(int columnIndex = 0; columnIndex < nColCount; columnIndex++){

                ArrayList<String> columnEntries = new ArrayList<>();

                // Important: '1' as starting index was chosen because '0' is the column's name!
                for(int rowIndex = 1; rowIndex < nRowCount; rowIndex++){
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
}
