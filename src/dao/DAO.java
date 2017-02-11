package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface to enable data access.
 *
 * @version %I%
 * @author Thomas Schönmann
 */
public interface DAO extends Serializable{

    /**
     * Retrieve every element of the class provided.
     *
     * @param c The class by which to look for the elements.
     * @return ArrayList of type <tt>T</tt>.
     */
    <T> ArrayList<T> getAllElements(Class<?> c);

    /**
     * Retrieve elements by file-name.
     *
     * @param fileName  Name of the file to look for.
     * @param <T>       Type of return-values.
     * @return          <code>ArrayList</code> of elements.
     */
    <T> ArrayList<T> getAllElements(String fileName);

    /**
     * Retrieve a single element from a file.
     *
     * @param c The class by which to look for the element.
     * @return Object of type <tt>T</tt>.
     */
    <T> T getSingleElement(Class<?> c);

    /**
     * Persistently save an object.
     *
     * @param c Parameter of any type through use of wildcard.
     * @param t Object which has to be saved.
     * @return Boolean if operation succeeded.
     */
    <T> boolean saveObject(Class<?> c, T t);

    /**
     * Remove the provided Element of type 'T' permanently.
     *
     * @param t Element to delete.
     * @return Boolean if operation succeeded.
     */
    <T> boolean removeElement(T t);

    /**
     * Persistently save a list of elements.
     *
     * @param c Parameter of any type through use of wildcard.
     * @param t List-parameter which has to be saved.
     * @return Boolean if operation succeeded.
     */
    <T> boolean saveList(Class<?> c, List<T> t);

    /**
     * Save an array of elements by file name.
     *
     * @param fileName  The name of the file to save.
     * @param t         The list of save.
     * @param <T>       The return-list's type.
     * @return          List of elements.
     */
    <T> boolean saveList(String fileName, List<T> t);

    /**
     * Removes the whole container (i.e. a file on disk).
     *
     * @param t     Container of elements.
     * @param <T>   Type of container.
     * @return      Result if removal was successful.
     */
    <T> boolean removeElementContainer(T t);
}
