package com.justinangel.hijckr;

import java.util.HashMap;

public class HijckrClassLoader extends ClassLoader {

    public HijckrClassLoader(ClassLoader wrappedClassLoader) {
        super(wrappedClassLoader);
    }


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // Log.d("[foo]", name);

        if (instanceClassRouting.containsKey(name)) {
            return instanceClassRouting.get(name);
        }

        if (HijckrClassLoader.globalClassRouting.containsKey(name)) {
            return HijckrClassLoader.globalClassRouting.get(name);
        }

        return super.loadClass(name);
    }

    private static HashMap<String, Class> globalClassRouting = new HashMap<>();

    public static void addGlobalClassRouting(Class fromClass, Class toClass) {
        HijckrClassLoader.globalClassRouting.put(fromClass.getName(), toClass);
    }

    private HashMap<String, Class> instanceClassRouting = new HashMap<>();

    public void addClassRouting(Class fromClass, Class toClass) {
        instanceClassRouting.put(fromClass.getName(), toClass);
    }


    public HijckrClassLoader withClassRouting(Class fromClass, Class toClass) {
        instanceClassRouting.put(fromClass.getName(), toClass);
        return this;
    }
}