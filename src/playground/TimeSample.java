package playground;

import java.util.Calendar;

/**
 * @author Thomas Schönmann
 * @version %I%
 */
public class TimeSample {

    public static void main(String... args) {
        millisUntilNextCycle();
    }

    private static void millisUntilNextCycle() {

        long now = System.currentTimeMillis();
        System.out.println("Now: " + now);
        System.out.println("Now: " + getNowInSec());
        //System.out.println("Difference to midnight: " + (getNextMidnight() - now));

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getNextMidnight() - now);

        System.out.println("Hours: " + (c.get(Calendar.HOUR_OF_DAY) - 1));
        System.out.println("Minutes: " + c.get(Calendar.MINUTE));
        System.out.println("Seconds: " + c.get(Calendar.SECOND));

        //System.out.println("Difference to next cycle in milliseconds: " + ());
    }

    private static long getNowInSec(){
        return (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) * 60 *60
                + Calendar.getInstance().get(Calendar.MINUTE) * 60
                + Calendar.getInstance().get(Calendar.SECOND);
    }

    private static long getNextMidnight(){

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DAY_OF_MONTH, 1);

        return c.getTimeInMillis();

        /*
        return (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) * 60 *60
                + Calendar.getInstance().get(Calendar.MINUTE) * 60
                + Calendar.getInstance().get(Calendar.SECOND);
                */
    }
}
