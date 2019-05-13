package com.itrw316.keyboardhook;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Main extends JFrame implements NativeKeyListener, WindowListener {
    JLabel active = new JLabel();
    JLabel shortcut = new JLabel();
    JLabel mouseMove = new JLabel();
    JButton fileSelect = new JButton();
    JLabel fileDir = new JLabel();
    boolean updateUI = false;

	public Main() {
        super("Java Based Keyboard Hook");

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF); // turns logger off on console output
        logger.setUseParentHandlers(false);
        
		// Set the event dispatcher to a swing safe executor service.
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        
        // Construct the example object.
		GlobalMouseListener m = new GlobalMouseListener();
		GlobalKeyListener k = new GlobalKeyListener();
        k.setMouse(m);
        k.setFrame(this);
        m.setFrame(this);
	
		// Add the appropriate listeners.
		GlobalScreen.addNativeMouseListener(m);
		GlobalScreen.addNativeMouseMotionListener(m);
        GlobalScreen.addNativeKeyListener(k);
        
        // GUI
        setSize(300, 150);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.add(active);
        updateActive(false);
        panel.add(shortcut);
        setupShortcut();
        panel.add(mouseMove);
        setupMouse();
        panel.add(fileSelect);
        panel.add(fileDir);
        add(panel);
        setSize(400, 200);
        setResizable(false);
        setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(this);
		setVisible(true);
	}

	public void windowOpened(WindowEvent e) {
		// Initialze native hook.
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			ex.printStackTrace();

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(this);
	}

	public void windowClosed(WindowEvent e) {
		//Clean up the native hook.
		try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            ex.printStackTrace();
        }
		System.runFinalization();
		System.exit(0);
	}

	public void windowClosing(WindowEvent e) { /* Unimplemented */ }
	public void windowIconified(WindowEvent e) { /* Unimplemented */ }
	public void windowDeiconified(WindowEvent e) { /* Unimplemented */ }
	public void windowActivated(WindowEvent e) { /* Unimplemented */ }
	public void windowDeactivated(WindowEvent e) { /* Unimplemented */ }
	public void nativeKeyReleased(NativeKeyEvent e) { /* Unimplemented */ }
    
    public void setupMouse() {
        mouseMove.setText("Mouse Coordinate: ");
    }

    public void setupShortcut() {
        shortcut.setText("Shortcut: CTRL + F1 + F4");
    }

    public void updateActive(boolean activated) {
        if (activated) {
            active.setText("<html>Status: <font color='green'>ACTIVATED</font>");
        } else {
            active.setText("<html>Status: <font color='red'>DEACTIVATED</font>");
        }
        revalidate();
        repaint();
    }

    public void updateMouse(int x, int y) {
        if (updateUI) {
            mouseMove.setText("Mouse Coordinate: (" + x + "," + y + ")");
            revalidate();
            repaint();
        }
    }

	public void nativeKeyPressed(NativeKeyEvent e) { /* Unimplemented */ }
	public void nativeKeyTyped(NativeKeyEvent e) { /* Unimplemented */ }

	public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}
}