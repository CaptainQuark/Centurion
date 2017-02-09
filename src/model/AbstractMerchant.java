package model;

import helper.TimeHelper;

import java.lang.reflect.Array;
import java.util.Objects;

/**
 * Trades objects of a given type.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public abstract class AbstractMerchant<T> {

    private T[] elements;
    private long timeToLeaveInMillis;
    private final int maxElements;
    private final String name;

    AbstractMerchant(Class<T> c, String name, int maxNumOfElements) {
        elements = (T[]) Array.newInstance(c, maxNumOfElements);
        this.name = name;
        this.maxElements = maxNumOfElements;
    }

    public void setTimeToLeaveNextMidnight(){
        timeToLeaveInMillis = TimeHelper.getNextMidnightInMillis();
    }

    public TimeCalculator buildTimeToLeave() {
        return new TimeCalculator();
    }

    public Integer addElementToNextFreeSlot(T t){
        Objects.requireNonNull(t, "The element to add to the merchant isn't allowed to be null.");

        for(int i = 0; i < elements.length; i++)
            if(elements[i] == null){
                elements[i] = t;
                return i;
            }

        return null;
    }

    public boolean removeElementAtNextUsedSlot(){
        for(int i = 0; i < maxElements; i++)
            if(elements[i] != null)
                return (elements[i] = null) == null;

        return false;
    }

    public T[] getElements(){
        return elements;
    }

    public T getElement(int index){
        if(index < maxElements && index >= 0)
            throw new IllegalArgumentException("AbstractMerchant : getElement() : Provided index is out of bounds.");

        return elements[index];
    }

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
