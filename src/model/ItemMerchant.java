package model;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class ItemMerchant extends AbstractMerchant<Item> {

    ItemMerchant(Class<Item> c, String name, int maxNumOfElements) {
        super(c, name, maxNumOfElements);
    }
}
