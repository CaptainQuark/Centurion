package centurion.model.model;

import centurion.model.dao.DAO;
import centurion.model.helper.TimeHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Trades objects of a given type.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public abstract class AbstractMerchant<T> {

    private ArrayList<T> merchantElements;
    private ArrayList<T> userElements;
    private long timeToLeaveInMillis;
    private final int maxElements;
    private final String name;
    private DAO dao;

    AbstractMerchant(DAO dao, final String name, final int maxNumOfElements) {
        System.out.println("AbstractMerchant constructor called.");

        this.dao = dao;
        this.name = name;
        this.maxElements = maxNumOfElements;
        merchantElements = retrieveElementsFromDisk(getMerchantElementsFileName()) != null
                ? retrieveElementsFromDisk(getMerchantElementsFileName())
                : initializeMerchantElements();
        userElements = retrieveElementsFromDisk(getUserElementsFileName()) != null
                ? retrieveElementsFromDisk(getUserElementsFileName())
                : new ArrayList<>();

        System.out.println("merchantElement: " + merchantElements.size());
        System.out.println("userElements: " + userElements.size());
        System.out.println("retrieveElementsFromDisk: " + retrieveElementsFromDisk(getMerchantElementsFileName()).size());

        if (retrieveElementsFromDisk(getMerchantElementsFileName()) == null)
            System.out.println("retrieveElementsFromDisk(getMerchantElementsFileName()) is null.");

        setTimeToLeaveNextMidnight();
    }

    /**
     * Create a new list of merchant-elements and save it persistently.
     *
     * @return New list containing new merchant-elements.
     */
    private ArrayList<T> initializeMerchantElements() {
        System.out.println("initializeMerchantElements() called.");
        ArrayList<T> list = new ArrayList<>();

        for (int i = 0; i < maxElements; i++) {
            list.add(createElement());
            saveElementsToDisk(getMerchantElementsFileName(), list);
        }

        return list;
    }

    /**
     * Set the merchant's time to leave to the next midnight.
     */
    private void setTimeToLeaveNextMidnight() {
        timeToLeaveInMillis = TimeHelper.getNextMidnightInMillis();
    }

    /**
     * Starting point from where a custom time to leave can be build.
     */
    public TimeCalculator buildTimeToLeave() {
        return new TimeCalculator();
    }

    /**
     * Automatically finds the next free slot in {@code merchantElements} and
     * assigns it the parameter {@code t}.
     *
     * @param t The value to add to the slot. Not allowed to be null.
     * @return The filled slot's index or null if an error occurred.
     */
    @Nullable
    public Integer addElementToNextFreeSlot(T t) {
        Objects.requireNonNull(t, "The element to add to the merchant isn't allowed to be null.");

        for (int i = 0; i < maxElements; i++)
            if (merchantElements.get(i) == null) {
                merchantElements.set(i, t);
                return saveElementsToDisk(getMerchantElementsFileName(), merchantElements) ? i : null;
            }

        return null;
    }

    /**
     * Remove an entry in {@code merchantElements} persistently.
     *
     * @return Result if removal and following saving is successful.
     */
    public boolean removeElementAtNextUsedSlot() {
        for (int i = 0; i < maxElements; i++)
            if (merchantElements.get(i) != null)
                return removeElement(i) && saveElementsToDisk(getMerchantElementsFileName(), merchantElements);

        return false;
    }

    /**
     * Remove an entry from {@code merchantElements} and save this entry
     * in {@code userElements} persistently.<br/>
     * <p>
     * Note: No operation regarding the transfer of currency is performed,
     * this is up to the caller.
     *
     * @param index The index in {@code merchantElements} of the element to sell.
     * @return Result if removal & following addition plus saving to user's file was successful.
     */
    public boolean sellElement(int index) {
        if (index >= maxElements || index < 0 || merchantElements.get(index) == null)
            throw new IllegalArgumentException("AbstractMerchant : removeElement : Index or value isn't allowed to be null.");

        userElements.add(merchantElements.get(index));
        merchantElements.set(index, null);

        return saveElementsToDisk(getMerchantElementsFileName(), merchantElements) && saveElementsToDisk(getUserElementsFileName(), userElements);
    }

    /**
     * Removes an element from {@code merchantElements} at the given index.
     *
     * @param index The index of {@code merchantElements} where the element to remove is located.
     * @return Result saving of updated {@code merchantElements} is successful.
     */
    public boolean removeElement(int index) {
        if (index >= maxElements || index < 0)
            throw new IllegalArgumentException("AbstractMerchant : removeElement : Index isn't allowed to be null.");

        merchantElements.set(index, null);
        return saveElementsToDisk(getMerchantElementsFileName(), merchantElements);
    }

    /**
     * Retrieve {@code merchantElements}.
     *
     * @return {@code merchantElements} and its content.
     */
    public ArrayList<T> getMerchantElements() {
        return merchantElements;
    }

    /**
     * Retrieve a single entry from {@code merchantElements} by index.
     *
     * @param index The index of the element to retrieve.
     * @return The element in {@code merchantElements} at the given index.
     */
    public T getElement(int index) {
        if (index < maxElements && index >= 0)
            throw new IllegalArgumentException("AbstractMerchant : getElement() : Provided index is out of bounds.");

        return merchantElements.get(index);
    }

    /**
     * Saves an {@code ArrayList} persistently to disk.
     *
     * @param fileName The name of the save-file.
     * @param elements {@code ArrayList} to save.
     * @return Result if saving is successful.
     */
    private boolean saveElementsToDisk(String fileName, ArrayList<T> elements) {
        if (dao == null || fileName == null)
            throw new NullPointerException("AbstractMerchant : saveMerchantElementsToDisk : DAO or items aren't allowed to be null.");

        return dao.saveList(fileName, elements);
    }

    /**
     * Read a file from disk.
     *
     * @param fileName The name of the save-file to read.
     * @return The read {@code ArrayList}.
     */
    private ArrayList<T> retrieveElementsFromDisk(String fileName) {
        if (dao == null || fileName == null)
            throw new NullPointerException("AbstractMerchant : retrieveMerchantElementsFromDisk : DAO isn't allowed to be null.");

        return dao.getAllElements(fileName);
    }

    /**
     * Retrieve the save-file's name for {@code merchantElements}.
     *
     * @return File's name of {@code merchantElements}.
     */
    protected abstract String getMerchantElementsFileName();

    /**
     * Retrieve the save-file's name for {@code userElements}.
     *
     * @return File's name of {@code userElements}.
     */
    protected abstract String getUserElementsFileName();

    /**
     * Create a single element to be used in {@code merchantElements}.
     *
     * @return Element of type {@code T} to save in {@code merchantElements}.
     */
    protected abstract T createElement();

    /**
     * Check if the merchant is overdue to leave.
     *
     * @return Result if the merchant's time is older than now.
     */
    public boolean isMerchantGone() {
        return timeToLeaveInMillis > System.currentTimeMillis();
    }

    /**
     * Retrieve {@code timeToLeaveInMillis}.
     *
     * @return Get {@code timeToLeaveInMillis}.
     */
    public long getTimeToLeaveInMillis() {
        return timeToLeaveInMillis;
    }

    /**
     * Retrieve the merchant's {@code name}.
     *
     * @return Get {@code name} from the merchant.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Builder for custom {@code timeToLeaveInMillis}.
     */
    public class TimeCalculator {
        private long time = 0;

        private TimeCalculator() {
        }

        public TimeCalculator addMilliseconds(long millis) {
            time += millis;
            return this;
        }

        public TimeCalculator addSeconds(long sec) {
            time += sec * 1000;
            return this;
        }

        public TimeCalculator addMinutes(long min) {
            time += min * 60 * 1000;
            return this;
        }

        public TimeCalculator addHours(long hours) {
            time += hours * 60 * 60 * 1000;
            return this;
        }

        public TimeCalculator addDays(long days) {
            time += days * 24 * 60 * 60 * 1000;
            return this;
        }

        public long result() {
            return timeToLeaveInMillis = time;
        }
    }
}
