package com.itrw316.keyboardhook;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import java.awt.Robot;

public class GlobalMouseListener implements NativeMouseInputListener {
	int x = 0;
	int y = 0;
	int mouseSteps = 10;
	Main ui;

	public void nativeMouseClicked(NativeMouseEvent e) { /* not implemented */ }

	public void nativeMousePressed(NativeMouseEvent e) { /* not implemented */ }

	public void nativeMouseReleased(NativeMouseEvent e) { /* not implemented */ }

	public void nativeMouseMoved(NativeMouseEvent e) {
		setCoordinates(e.getX(), e.getY());
		ui.updateMouse(e.getX(), e.getY());
	}

	public void nativeMouseDragged(NativeMouseEvent e) {
		setCoordinates(e.getX(), e.getY());
		ui.updateMouse(e.getX(), e.getY());
	}

	public int[] getCoordinates() {
		return new int[] {x,y};
	}

	public void setCoordinates(int X, int Y) {
		x = X;
		y = Y;
	}

	public void setFrame(Main main) {
		ui = main;
	}

	public GlobalMouseListener returnMouse() {
		return this;
	}

	public void executeMouseEvent(String selected) {
		try {
			Robot robot = new Robot();
			int X = x;
			int Y = y;
			if ("UP" == selected) {
				Y = y - mouseSteps;
			} else if ("DOWN" == selected) {
				Y = y + mouseSteps; 
			} else if ("LEFT" == selected) {
				X = x - mouseSteps;
			} else {
				X = x + mouseSteps; 
			}
			robot.mouseMove(X,Y);
		} catch (Exception e) {}

	}

	public enum mouseDirections {
		UP, 
		DOWN,
		RIGHT,
		LEFT
	}
}
