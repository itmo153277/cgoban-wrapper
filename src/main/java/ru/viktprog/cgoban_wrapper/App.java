package ru.viktprog.cgoban_wrapper;

import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnectionBuilder;
import org.freedesktop.dbus.exceptions.DBusException;

import lombok.extern.slf4j.Slf4j;
import ru.viktprog.cgoban_wrapper.dbus.MDIApp;
import ru.viktprog.cgoban_wrapper.dbus.MDIAppImpl;

@Slf4j
public class App {

    private static final String BUS_NAME = "ru.viktprog.cgoban_wrapper";

    public static void main(String[] args) {
        try (DBusConnection connection = DBusConnectionBuilder.forSessionBus().build()) {
            MDIApp app;
            try {
                connection.requestBusName(BUS_NAME);
                app = new MDIAppImpl();
                connection.exportObject(app);
            } catch (DBusException e) {
                app = connection.getRemoteObject(BUS_NAME, MDIAppImpl.OBJECT_PATH, MDIApp.class);
            }
            if (args.length > 0) {
                app.openFile(args[0]);
            }
            if (!app.isRemote()) {
                while (true) {
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            log.error("Failed", e);
        }
    }
}
