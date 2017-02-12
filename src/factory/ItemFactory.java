package factory;

import mapper.ItemTypeMapper;
import model.Item;

/**
 * Factory for producing <tt>Hero</tt>-objects.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class ItemFactory {

    private static ItemFactory instance;

    private ItemFactory() {
    }

    public static ItemFactory getInstance() {
        return instance == null ? instance = new ItemFactory() : instance;
    }

    public <E extends Enum> Item produce(E e) {
        return new ItemTypeMapper().map(e);
    }
}
