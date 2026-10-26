package ru.viktprog.cgoban_wrapper;

import java.io.IOException;

import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnectionBuilder;
import org.freedesktop.dbus.exceptions.DBusException;

import lombok.extern.slf4j.Slf4j;
import ru.viktprog.cgoban_wrapper.dbus.MDIApp;
import ru.viktprog.cgoban_wrapper.dbus.MDIAppImpl;

@Slf4j
public class App {

    private static final String BUS_NAME = "ru.viktprog.cgoban_wrapper";
    private static DBusConnection connection = null;

    public static void main(String[] args) {
        if (args.length != 1 && args.length != 2) {
            System.err.print("Usage:\n\n" +
                    "java -jar cgoban_wrapper.jar path_to_cgoban.jar [sgf_file]\n");
            System.exit(1);
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (IOException e) {
                        // do nothing
                    }
                }
            }
        });
        try {
            connection = DBusConnectionBuilder.forSessionBus().build();
            MDIApp app;
            try {
                connection.requestBusName(BUS_NAME);
                log.info("Creating new instance {}", BUS_NAME);
                app = new MDIAppImpl(args[0]);
                connection.exportObject(app);
            } catch (DBusException e) {
                log.info("Connecting to existing instance {}", BUS_NAME);
                app = connection.getRemoteObject(BUS_NAME, MDIAppImpl.OBJECT_PATH, MDIApp.class);
            }
            if (args.length > 1) {
                app.openFile(args[1]);
            }
        } catch (DBusException e) {
            log.error("DBus connection error", e);
            System.exit(1);
        } catch (RuntimeException e) {
            log.error("Runtime error", e);
            System.exit(1);
        }
    }
}
