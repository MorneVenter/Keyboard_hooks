import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Main {
    public static void main(String[] args) {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF); // turns logger off on console output
		logger.setUseParentHandlers(false);
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
		// Construct the example object.
		GlobalMouseListener m = new GlobalMouseListener();
		GlobalKeyListener k = new GlobalKeyListener();
		k.setMouse(m);
	
		// Add the appropriate listeners.
		GlobalScreen.addNativeMouseListener(m);
		GlobalScreen.addNativeMouseMotionListener(m);
		GlobalScreen.addNativeKeyListener(k);
	}

}