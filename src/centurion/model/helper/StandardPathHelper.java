package centurion.model.helper;

import java.io.File;

/**
 * PathHelper for local, non-web Java app.
 *
 * @version %I%
 * @author Thomas Schönmann
 */
public class StandardPathHelper extends AbstractPathHelper {

    private static StandardPathHelper instance;

    private StandardPathHelper(){
        this.checkAndCreate(null);
    }

    public static StandardPathHelper getInstance(){
        return instance == null ? instance = new StandardPathHelper() : instance;
    }

    @Override
    <T> String createBasePath(T t) {
        return System.getProperty("user.dir");

        // If the path to '.../build/...' is required, use instead:
        // return this.getClass().getProtectionDomain().getCodeSource().getLocation()
    }

    public String getDataPath(){
        return getRootPath() + File.separatorChar + "data" + File.separator;
    }
}
