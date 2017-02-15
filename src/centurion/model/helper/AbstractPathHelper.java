package centurion.model.helper;

import java.io.File;

/**
 * Enables the correct setting of the save-files' path
 *  at runtime.
 *
 * @author Thomas Schönmann
 */
public abstract class AbstractPathHelper {

    /**
     * Path where every save-file will be stored.
     */
    private static String pathToRoot = "";
    private static final String fileSuffix = ".ser";

    private <T> void setEveryPathForSaveFiles(T t){
        pathToRoot = createBasePath(t) + File.separator;
    }

    /**
     * Simple getter to retrieve the path where every
     *  save file will be stored.
     *
     * @return  The root path.
     */
    public String getRootPath(){
        return pathToRoot;
    }

    /**
     * Create the path where every save file will be stored.
     *
     * @param t	Necessary to create path during runtime.
     * @return	The root path to every save file.
     */
    abstract <T> String createBasePath(T t);

    /**
     * Check if paths to save files are already set.
     *
     * @return Boolean if paths exist or not.
     */
    private boolean doesRootPathExist(){
        if(pathToRoot.equals("")){
            System.out.println("Path isn't known yet.");
            return false;
        }

        return true;
    }

    /**
     * Little method to conveniently create paths
     *  if they don't exists yet (only called by servlets).
     *
     * @param t	Variable to enable path-setting at runtime.
     */
    <T> void checkAndCreate(T t){
        if(!doesRootPathExist()){
            setEveryPathForSaveFiles(t);
            System.out.println("Every path is now registered @ " + pathToRoot);
        }
    }

    /**
     * Get the path to a specific save file with its '.'-ending.
     *
     * @param fileName	The file's name without the '.'-ending.
     * @return			Complete path to file.
     */
    public String getRootPathAndAppendFileEnding(String fileName){
        return getRootPath() + File.separator + fileName + fileSuffix;
    }
}
