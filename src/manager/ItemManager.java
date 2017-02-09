package manager;

import dao.DAO;
import model.Item;

import java.util.ArrayList;

/**
 * Manager to handle operations regarding <code>Item</code>s.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class ItemManager {

    private ArrayList<Item> itemsInStock;
    private DAO dao;

    public ItemManager(DAO dao){
        this.dao = dao;
        this.itemsInStock = dao.getAllElements(Item.class) != null ? dao.getAllElements(Item.class) : new ArrayList<>();
    }

    public boolean add(Item i){
        return itemsInStock.add(i) && dao.saveList(Item.class, itemsInStock);
    }

    public ArrayList<Item> getAll(){
        return itemsInStock;
    }

    public boolean remove(Item i){
        return itemsInStock.remove(i) && dao.saveList(Item.class, itemsInStock);
    }
}
