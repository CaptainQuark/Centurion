package mapper;

import enumerations.ItemType;
import model.Item;

/**
 * The place where every kind of <tt>Hero</tt> is defined.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class ItemTypeMapper extends AbstractMapper<Item> {

    @Override
    public Item map(Enum e) {

        // Cast 'e' to ItemType to access ItemType's elements.
        ItemType types = (ItemType) e;

        switch (types) {

            case DEBUG_ITEM:

                /*
                 * Debug item for demonstration purpose:
                 *
                 * The item's abilities (in this case only one) are described inline,
                 *  but could also be retrieved from the AbilityFactory.
                 *
                 * The item's max. number of uses is defined as 1, hence it can only
                 *  be used one time. Use '-1' to ignore the throw count.
                 */

                return new Item("Debug Item",
                        c -> {
                            if (c.peek().getHero().getHp() < 50)
                                c.peek().getHero().setHp(c.peek().getHero().getHp() + 50);
                        },
                        1);

            default:
                System.out.println("default @ switch @ map(...) @ ItemTypeMapper called.");
                return null;
        }
    }
}
