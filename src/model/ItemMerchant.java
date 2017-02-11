package model;

import dao.DAO;
import enumerations.FileNames;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class ItemMerchant extends AbstractMerchant<Item> {

    ItemMerchant(DAO dao, String name, int maxNumOfElements) {
        super(dao, name, maxNumOfElements);
    }

    @Override
    protected String getFileName() {
        return FileNames.MERCHANT_ITEMS.name();
    }
}
