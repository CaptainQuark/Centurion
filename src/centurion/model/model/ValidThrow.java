package centurion.model.model;

/**
 * Represents a valid throw on the dart board with at least
 *  a score of 1 and a maximum of 60.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class ValidThrow implements Throw {
    final int number;
    final Multiplier multi;

    /**
     * Constructor for a <code>ValidThrow</code>.
     *
     * @param number    The number-field hit.
     * @param multi     Multiplier to be used.
     */
    public ValidThrow(int number, Multiplier multi){
        if(number <= 0 || number > 20)
            throw new IllegalArgumentException("ValidThrow : constructor : Number isn't allowed to be <= 0 || > 20.");

        this.number = number;
        this.multi = multi;
    }

    public int getScore(){
        return number * multi.getMulti();
    }

    public int getNumber() {
        return number;
    }

    public Multiplier getMulti() {
        return multi;
    }

    public enum Multiplier{
        ONE(1), TWO(2), THREE(3);

        private final int multi;
        Multiplier(int i){
            multi = i;
        }

        public int getMulti() {
            return multi;
        }
    }
}
