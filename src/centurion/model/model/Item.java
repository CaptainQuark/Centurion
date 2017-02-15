package centurion.model.model;

import centurion.model.dao.DAO;
import centurion.model.dao.SerialDAO;
import centurion.model.enumerations.FileNames;
import centurion.model.generator.SequentialIDGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class Item extends SequentialIDGenerator<Item> {

    private final String name;
    private final ArrayList<Ability> abilities;

    /**
     * Maximum amount of throws the item can last. Use '-1'
     *  to represent persistent use throughout a combat.
     */
    private final int throwsToLastMax;

    /**
     * Current count of how many throws the item will last.
     */
    private int throwsUsed;

    public Item(String name, ArrayList<Ability> abilities, int throwsToLastMax){
        super(true);
        this.name = name;
        this.abilities = abilities;
        this.throwsToLastMax = throwsToLastMax;
        throwsUsed = throwsToLastMax;
    }

    public Item(String name, Ability ability, int throwsToLastMax) {
        super(true);

        ArrayList<Ability> list = new ArrayList<>();
        list.addAll(Arrays.asList(ability));

        this.name = name;
        this.abilities = list;
        this.throwsToLastMax = throwsToLastMax;
        throwsUsed = throwsToLastMax;
    }

    public int decrementThrowsCount(){
        return --throwsUsed;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public int getThrowsToLastMax() {
        return throwsToLastMax;
    }

    public int getThrowsUsed() {
        return throwsUsed;
    }

    @Override
    protected List<Item> provideAllElements() {
        ArrayList<Item> items = new ArrayList<>();
        DAO dao = new SerialDAO();

        if (dao.getAllElements(FileNames.USER_ITEMS.name()) != null)
            items.addAll(dao.getAllElements(FileNames.USER_ITEMS.name()));

        if (dao.getAllElements(FileNames.MERCHANT_ITEMS.name()) != null)
            items.addAll(dao.getAllElements(FileNames.MERCHANT_ITEMS.name()));

        return items;
    }

    @Override
    public String toString() {
        String n = System.getProperty("line.separator");
        return super.toString()
                + "Name: " + name + n
                + "Max. number of uses: " + throwsToLastMax + n;
    }
}
