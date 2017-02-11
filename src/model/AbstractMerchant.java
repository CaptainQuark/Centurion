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

    //private T[] elements;
    private ArrayList<T> elements;
    private long timeToLeaveInMillis;
    private final int maxElements;
    private final String name;
    private DAO dao;

    @SuppressWarnings("unchecked")
    AbstractMerchant(DAO dao, final String name, final int maxNumOfElements) {
        this.dao = dao;
        this.name = name;
        this.maxElements = maxNumOfElements;
        elements = getFromDisk() != null ? getFromDisk() : new ArrayList<>(maxElements);
        setTimeToLeaveNextMidnight();
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
            if(elements.get(i) == null)
                return (elements.set(i, t) != null) && saveItemsToDisk() ? i : null;

        return null;
    }

    public boolean removeElementAtNextUsedSlot(){
        for(int i = 0; i < maxElements; i++)
            if(elements.get(i) != null)
                return removeElementAtIndex(i) && saveItemsToDisk();

        return false;
    }

    public boolean removeElementAtIndex(int index){
        if (index >= maxElements || index < 0)
            throw new IllegalArgumentException("AbstractMerchant : removeElementAtIndex : Index isn't allowed to be null.");

        return (elements.set(index, null) == null) && saveItemsToDisk();
    }

    public ArrayList<T> getElements(){
        return elements;
    }

    public T getElement(int index){
        if(index < maxElements && index >= 0)
            throw new IllegalArgumentException("AbstractMerchant : getElement() : Provided index is out of bounds.");

        return elements.get(index);
    }

    private boolean saveItemsToDisk(){
        if(dao == null || elements == null)
            throw new NullPointerException("AbstractMerchant : saveItemsToDisk : DAO or items aren't allowed to be null.");

        return dao.saveList(getFileName(), elements);
    }

    private ArrayList<T> getFromDisk(){
        if(dao == null || elements == null)
            throw new NullPointerException("AbstractMerchant : getFromDisk : DAO isn't allowed to be null.");

        return dao.getAllElements(getFileName());
    }

    protected abstract String getFileName();

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

        private TimeCalculator(){
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
