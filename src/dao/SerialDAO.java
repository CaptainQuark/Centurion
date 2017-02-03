package dao;

import helper.AbstractPathHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for serialized objects.
 *
 * @version %I%
 * @author Thomas Schömann
 */
public class SerialDAO implements DAO{

    @SuppressWarnings("unchecked")
    @Override
    public <T> ArrayList<T> getAllElements(Class<?> t) {
        ArrayList<T> elements = null;
        File f = getFileByType(t).getAbsoluteFile();

		/*
		 * Hint: Calling '.exists' on a file also returns true if
		 *  the file is a path! I may have saved you some coffee ;)
		 */
        if(f != null && f.isFile()){
            try {
                FileInputStream fis = new FileInputStream(f.getAbsolutePath());
                ObjectInputStream ois = new ObjectInputStream(fis);
                elements = ((ArrayList<T>) ois.readObject());
                ois.close();
                fis.close();
            }catch(IOException e){} catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return elements;
    }

    @Override
    public <T> boolean removeElement(T t) {
        @SuppressWarnings("unchecked")
        Class<T> cl = (Class<T>) t.getClass();
        ArrayList<T> elementsToSave = this.getAllElements(cl);
        for(T type : elementsToSave)
            if(type.equals(t)){
                elementsToSave.remove(t);
                return this.saveList(cl , elementsToSave);
            }
        return false;
    }

    @Override
    public <T> boolean saveList(Class<?> c, List<T> t) {
        if(getFileByType(c).getAbsolutePath() != null)
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

    /**
     * Little helper method to wire a provided class to its save file.
     *
     * @param t	Parameter which is used to determine the right save file.
     * @return	The file found at the path.
     */
    private <T> File getFileByType(Class<T> t){
        return new File(AbstractPathHelper.getRootPathAndAppendFileEnding(t.getSimpleName()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getSingleElement(Class<?> c) {
        T t = null;
        File f = getFileByType(c).getAbsoluteFile();
		/*
		 * Hint: Calling '.exists' on a file also returns true if the file is a
		 * path! I may have saved you some coffee ;)
		 */
        if (f != null && f.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(f.getAbsolutePath());
                ObjectInputStream ois = new ObjectInputStream(fis);
                t = (T) ois.readObject();
                ois.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    @Override
    public <T> boolean saveObject(Class<?> c, T t) {
        if(getFileByType(c).getAbsolutePath() != null)
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
