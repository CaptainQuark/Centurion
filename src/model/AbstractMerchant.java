package model;

import dao.DAO;
import helper.TimeHelper;

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
            System.out.println("retrieveElementsFromDisk(getMerchantElementsFileName()) null.");

        setTimeToLeaveNextMidnight();
    }

    private ArrayList<T> initializeMerchantElements() {
        System.out.println("initializeMerchantElements() called.");
        ArrayList<T> list = new ArrayList<>();

        for (int i = 0; i < maxElements; i++) {
            list.add(createElement());
            saveElementsToDisk(getMerchantElementsFileName(), list);
        }

        return list;
    }

    private void setTimeToLeaveNextMidnight(){
        timeToLeaveInMillis = TimeHelper.getNextMidnightInMillis();
    }

    public TimeCalculator buildTimeToLeave() {
        return new TimeCalculator();
    }

    public Integer addElementToNextFreeSlot(T t){
        Objects.requireNonNull(t, "The element to add to the merchant isn't allowed to be null.");

        for(int i = 0; i < maxElements; i++)
            if (merchantElements.get(i) == null) {
                merchantElements.set(i, t);
                return saveElementsToDisk(getMerchantElementsFileName(), merchantElements) ? i : null;
            }

        return null;
    }

    public boolean removeElementAtNextUsedSlot(){
        for(int i = 0; i < maxElements; i++)
            if (merchantElements.get(i) != null)
                return removeElement(i) && saveElementsToDisk(getMerchantElementsFileName(), merchantElements);

        return false;
    }

    public boolean sellElement(int index) {
        if (index >= maxElements || index < 0 || merchantElements.get(index) == null)
            throw new IllegalArgumentException("AbstractMerchant : removeElement : Index or value isn't allowed to be null.");

        userElements.add(merchantElements.get(index));
        merchantElements.set(index, null);

        return saveElementsToDisk(getMerchantElementsFileName(), merchantElements) && saveElementsToDisk(getUserElementsFileName(), userElements);
    }

    public boolean removeElement(int index) {
        if (index >= maxElements || index < 0)
            throw new IllegalArgumentException("AbstractMerchant : removeElement : Index isn't allowed to be null.");

        merchantElements.set(index, null);
        return saveElementsToDisk(getMerchantElementsFileName(), merchantElements);
    }

    public ArrayList<T> getMerchantElements() {
        return merchantElements;
    }

    public T getElement(int index){
        if(index < maxElements && index >= 0)
            throw new IllegalArgumentException("AbstractMerchant : getElement() : Provided index is out of bounds.");

        return merchantElements.get(index);
    }

    private boolean saveElementsToDisk(String fileName, ArrayList<T> elements) {
        if (dao == null || fileName == null)
            throw new NullPointerException("AbstractMerchant : saveMerchantElementsToDisk : DAO or items aren't allowed to be null.");

        return dao.saveList(fileName, elements);
    }

    private ArrayList<T> retrieveElementsFromDisk(String fileName) {
        if (dao == null || fileName == null)
            throw new NullPointerException("AbstractMerchant : retrieveMerchantElementsFromDisk : DAO isn't allowed to be null.");

        return dao.getAllElements(fileName);
    }

    protected abstract String getMerchantElementsFileName();

    protected abstract String getUserElementsFileName();

    protected abstract T createElement();

    public boolean isMerchantGone(){
        return timeToLeaveInMillis > System.currentTimeMillis();
    }

    public long getTimeToLeaveInMillis(){
        return timeToLeaveInMillis;
    }

    public String getName(){
        return this.name;
    }

    public class TimeCalculator {
        private long time = 0;

        private TimeCalculator() {
        }

        public TimeCalculator addMilliseconds(long millis){
            time += millis;
            return this;
        }

        public TimeCalculator addSeconds(long sec){
            time += sec * 1000;
            return this;
        }

        public TimeCalculator addMinutes(long min){
            time += min * 60 * 1000;
            return this;
        }

        public TimeCalculator addHours(long hours){
            time += hours * 60 * 60 * 1000;
            return this;
        }

        public TimeCalculator addDays(long days){
            time += days * 24 * 60 * 60 * 1000;
            return this;
        }

        public long result(){
            return timeToLeaveInMillis = time;
        }
    }
}
