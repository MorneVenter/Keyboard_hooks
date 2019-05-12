import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.keyboard.SwingKeyAdapter;
import org.jnativehook.keyboard.NativeKeyAdapter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class GlobalKeyListener implements NativeKeyListener {
	boolean capslock = false;
	boolean shift = false;

	public void nativeKeyPressed(NativeKeyEvent e) {
		String keyP = NativeKeyEvent.getKeyText(e.getKeyCode());
		if (keyP == "Enter") {
			System.out.print("\n");
		} else if (keyP == "Space") {
			System.out.print(" ");
		} else if (keyP == "Caps Lock") {
			capslock = !capslock;
		} else if (keyP == "Shift" || NativeKeyEvent.getKeyText(e.getKeyCode()) == "Unknown keyCode: 0xe36") {
			shift = true;	
		} else if (keyP == "Minus") {
			System.out.print("-");
		} else if (keyP == "Equals") {
			System.out.print("=");
		} else if (keyP == "Quote") {
			if (shift) {
				System.out.print("\"");
				shift = false;
			} else {
				System.out.print("'");
			}
		} else if (keyP == "Back Slash") {
			System.out.print("\\");
		} else if (keyP == "Slash") {
			System.out.print("/");
		} else if (keyP == "Page Down") {
		} else if (keyP == "Page Up") {
		} else if (keyP == "End") {
		} else if (keyP == "Home") {
		} else if (keyP == "Print Screen") {
		} else if (keyP == "Backspace") {
		} else if (keyP == "Clear") {
		} else if (keyP == "Up") {
		} else if (keyP == "Down") {
		} else if (keyP == "Left") {
		} else if (keyP == "Right") {
		} else if (keyP == "Escape") {
		} else if (keyP == "Tab") {
		} else if (keyP == "Ctrl") {
		} else {		
			if (shift) {
				alphabet(!capslock, keyP);
				shift = false;
			} else {
				alphabet(capslock, keyP);
			}
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		// can be used to overwrite when key is released
	}

	public void nativeKeyTyped(NativeKeyEvent e) {
		// can be used to overwrite when key is typed
	}

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
		GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
	}

	public void alphabet(boolean logic, String keyPressed) {
		if (logic) {
			System.out.print(keyPressed);
		} else {
			char[] pressed = keyPressed.toCharArray();
			if (pressed.length > 1) {
				System.out.print(keyPressed);
			} else {
				System.out.print((char)(pressed[0] + 32));
			}
		}
	}
}