package ru.viktprog.cgoban_wrapper.dbus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MDIAppImpl implements MDIApp {
    public static final String OBJECT_PATH = "/ru/viktprog/cgoban_wrapper/MDIApp";

    @Override
    public void openFile(String arg) {
        log.info("Open file: {}", arg);
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
