package ru.viktprog.cgoban_wrapper.dbus;

import ru.viktprog.cgoban_wrapper.CGoban;

public class MDIAppImpl implements MDIApp {
    public static final String OBJECT_PATH = "/ru/viktprog/cgoban_wrapper/MDIApp";

    private CGoban app;

    public MDIAppImpl(String jarLocation) {
        app = new CGoban(jarLocation);
    }

    @Override
    public void openFile(String arg) {
        app.openFile(arg);
    }

    @Override
    public boolean isRemote() {
        return false;
    }

    @Override
    public String getObjectPath() {
        return OBJECT_PATH;
    }
}
