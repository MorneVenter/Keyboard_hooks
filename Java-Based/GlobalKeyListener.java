import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.keyboard.SwingKeyAdapter;
import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.mouse.NativeMouseEvent;

public class GlobalKeyListener implements NativeKeyListener {
	boolean capslock = false;
	boolean shift = false;
	GlobalMouseListener mouse;
	int mouseSteps = 10;

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
			try {
				Robot robot = new Robot();
				int[] xy = mouse.getCoordinates();
				robot.mouseMove(xy[0], xy[1] - mouseSteps); // check if it is inverted for other OSes
			} catch (Exception m) {
				
			}
		} else if (keyP == "Down") {
			try {
				Robot robot = new Robot();
				int[] xy = mouse.getCoordinates();
				robot.mouseMove(xy[0], xy[1] + mouseSteps); // check if it is inverted for other OSes
			} catch (Exception m) {
				
			}
		} else if (keyP == "Left") {
			try {
				Robot robot = new Robot();
				int[] xy = mouse.getCoordinates();
				robot.mouseMove(xy[0] - mouseSteps, xy[1]); // check if it is inverted for other OSes
			} catch (Exception m) {
				
			}
		} else if (keyP == "Right") {
			try {
				Robot robot = new Robot();
				int[] xy = mouse.getCoordinates();
				robot.mouseMove(xy[0] + mouseSteps, xy[1]); // check if it is inverted for other OSes
			} catch (Exception m) {
				
			}
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

	public void setMouse(GlobalMouseListener m) {
		mouse = m;
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
