package model;

import dao.DAO;
import enumerations.FileNames;
import enumerations.ItemType;
import factory.ItemFactory;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class ItemMerchant extends AbstractMerchant<Item> {

    public ItemMerchant(DAO dao, String name, int maxNumOfElements) {
        super(dao, name, maxNumOfElements);
    }

    @Override
    protected String getMerchantElementsFileName() {
        return FileNames.MERCHANT_ITEMS.name();
    }

    @Override
    protected String getUserElementsFileName() {
        return FileNames.USER_ITEMS.name();
    }

    @Override
    protected Item createElement() {
        return ItemFactory.getInstance().produce(ItemType.DEBUG_ITEM);
    }
}
