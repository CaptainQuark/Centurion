package centurion.model.dao;

import centurion.model.helper.StandardPathHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DAO for serialized objects.
 *
 * @author Thomas Schömann
 * @version %I%
 */
public class SerialDAO implements DAO {

    public void dummy(){}

    @SuppressWarnings("unchecked")
    @Override
    public <T> ArrayList<T> getAllElements(Class<?> t) {
        ArrayList<T> elements;
        File f = getFileByType(t).getAbsoluteFile();

		/*
         * Hint: Calling '.exists' on a file also returns true if
		 *  the file is a path! I may have saved you some coffee ;)
		 */
        if (f.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(f.getAbsolutePath());
                ObjectInputStream ois = new ObjectInputStream(fis);
                elements = ((ArrayList<T>) ois.readObject());
                ois.close();
                fis.close();
                return elements;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ArrayList<T> getAllElements(String fileName) {
        Objects.requireNonNull(fileName);

        ArrayList<T> elements;
        File f = new File(StandardPathHelper.getInstance().getDataPath() + fileName + ".ser");

        if (f.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(f.getAbsolutePath());
                ObjectInputStream ois = new ObjectInputStream(fis);
                elements = ((ArrayList<T>) ois.readObject());
                ois.close();
                fis.close();

                return elements;
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public <T> boolean removeElement(T t) {
        @SuppressWarnings("unchecked")
        Class<T> cl = (Class<T>) t.getClass();
        ArrayList<T> elementsToSave = this.getAllElements(cl);
        for (T type : elementsToSave)
            if (type.equals(t)) {
                elementsToSave.remove(t);
                return this.saveList(cl, elementsToSave);
            }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> boolean saveList(Class<?> c, List<T> t) {
        System.out.println("Save file: " + getFileByType(c).getAbsolutePath());

        try {
            FileOutputStream fos = new FileOutputStream(getFileByType(c).toString());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            oos.close();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public <T> boolean saveList(String fileName, List<T> t) {
        File f = new File(StandardPathHelper.getInstance().getDataPath() + fileName + ".ser");
        System.out.println("Save file: " + f.getAbsolutePath());

        try {
            FileOutputStream fos = new FileOutputStream(f.toString());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            oos.close();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public <T> boolean removeElementContainer(T t) {
        return Objects.nonNull(t) && new File((String) t).delete();
    }

    /**
     * Little centurion.model.helper method to wire a provided class to its save file.
     *
     * @param t Parameter which is used to determine the right save file.
     * @return The file found at the path.
     */
    private <T> File getFileByType(Class<T> t) {
        return new File(StandardPathHelper.getInstance().getDataPath() + t.getSimpleName() + ".ser");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getSingleElement(Class<?> c) {
        T t;
        File f = getFileByType(c).getAbsoluteFile();
		/*
		 * Hint: Calling '.exists' on a file also returns true if the file is a
		 * path! I may have saved you some coffee ;)
		 */
        if (f.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(f.getAbsolutePath());
                ObjectInputStream ois = new ObjectInputStream(fis);
                t = (T) ois.readObject();
                ois.close();
                fis.close();
                return t;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public <T> boolean saveObject(Class<?> c, T t) {
        if (c == null || t == null)
            throw new NullPointerException("SerialDAO : saveObject() : c and/or t are null.");

        System.out.println("Save file: " + getFileByType(c).getAbsolutePath());

        try {
            FileOutputStream fos = new FileOutputStream(getFileByType(c).toString());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            oos.close();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
