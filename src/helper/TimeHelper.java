package helper;

import java.util.Calendar;

/**
 * Provides some helpful methods regarding the use of time
 *  in the context of the application.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
public class TimeHelper {

    public static long getNextMidnightInMillis(){

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DAY_OF_MONTH, 1);

        return c.getTimeInMillis();
    }
}
