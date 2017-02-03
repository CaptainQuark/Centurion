package helper;

/**
 * PathHelper for local, non-web Java app.
 *
 * @version %I%
 * @author Thomas Schönmann
 */
public class StandardPathHelper extends AbstractPathHelper {

    @Override
    <T> String createBasePath(T t) {
        return System.getProperty("user.dir");

        // If the path to '.../build/...' is required, use instead:
        // return this.getClass().getProtectionDomain().getCodeSource().getLocation()
    }
}
