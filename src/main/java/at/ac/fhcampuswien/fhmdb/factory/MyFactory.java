package at.ac.fhcampuswien.fhmdb.factory;


import javafx.util.Callback;

public class MyFactory implements Callback<Class<?>, Object> {
    private static MyFactory instance;

    private MyFactory() {}

    public static synchronized MyFactory getInstance() {
        if (instance == null) {
            instance = new MyFactory();
        }
        return instance;
    }

    @Override
    public Object call(Class<?> aClass) {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
