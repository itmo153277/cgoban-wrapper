package ru.viktprog.cgoban_wrapper;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;
import ru.viktprog.cgoban_wrapper.utils.Loader;

@Slf4j
public class CGoban {

    private final Constructor<?> eventCtor;
    private final Method eventMethod;
    private final Object instance;

    public CGoban(String jarLocation) {
        try {
            ClassLoader classLoader = Loader.setClassLoader(jarLocation);
            Class<?> cgobanClass = Class.forName("com.gokgs.igoweb.cgoban.CGoban", true, classLoader);
            Constructor<?> ctor = cgobanClass.getDeclaredConstructor(String[].class);
            ctor.setAccessible(true);
            Class<?> eventClass = Class.forName("com.gokgs.igoweb.util.Event", true, classLoader);
            eventCtor = eventClass.getDeclaredConstructor(Object.class, int.class, Object.class);
            eventMethod = cgobanClass.getDeclaredMethod("handleEvent", eventClass);
            instance = ctor.newInstance((Object) new String[] {});
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException
                | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void openFile(String arg) {
        try {
            String[] args = new String[] { "-open", arg };
            Object event = eventCtor.newInstance(null, 0, args);
            eventMethod.invoke(instance, event);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
