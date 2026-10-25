package ru.viktprog.cgoban_wrapper.dbus;

import org.freedesktop.dbus.interfaces.DBusInterface;

public interface MDIApp extends DBusInterface {
    void openFile(String arg);
}
