package com.itrw316.keyboardhook;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileFilter;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GlobalKeyListener implements NativeKeyListener {
	boolean capslock = false;
	boolean shift = false;
	GlobalMouseListener mouse;
	int mouseSteps = 10;
	boolean f1 = false;
	boolean f4 = false;
	boolean f5 = false;
	boolean ctrl = false;
	boolean listen = false;
	Main ui;
	String filename = "";

	public void nativeKeyPressed(NativeKeyEvent e) {
		String keyP = NativeKeyEvent.getKeyText(e.getKeyCode());
		if(listen) {
			switch (keyP) {
				case "Enter":
					writeOther("\n");
					break;
				case "Space":
					writeOther(" ");
					break;
				case "Caps Lock":
					capslock = !capslock;
					break;
				case "Shift":
					shift = true;
					break;
				case "Minus":
					writeOther("-");
					break;
				case "Equals":
					writeOther("=");
					break;
				case "Quote":
					if (shift) {
						writeOther("\"");
						shift = false;
					} else {
						writeOther("'");
					}
					break;
				case "Back Slash":
					writeOther("\\");
					break;
				case "Slash":
					writeOther("/");
					break;
				case "Page Down":
					break;
				case "Page Up":
					break;
				case "End":
					break;
				case "Home":
					break;
				case "Escape":
					break;
				case "Tab":
					break;
				case "Backspace":
					break;
				case "Clear":
					break;
				case "Up":
					moveMouse(mouseDirections.UP);
					break;
				case "Down":
					moveMouse(mouseDirections.DOWN);
					break;
				case "Left":
					moveMouse(mouseDirections.LEFT);
					break;
				case "Right":
					moveMouse(mouseDirections.RIGHT);
					break;
				case "Ctrl":
					ctrl = true;
					shortcut();
					break;
				case "F1":
					f1 = true;
					shortcut();
					break;
				case "F4":
					f4 = true;
					shortcut();
					break;
				case "F5":
					f5 = true;
					shortcut();
					break;
				default:
					if (shift) {
						writeAlphabet(!capslock, keyP);
						shift = false;
					} else {
						writeAlphabet(capslock, keyP);
					}
					break;
			}
		} else {
			switch (keyP) {
				case "Ctrl":
					ctrl = true;
					shortcut();
					break;
				case "F1":
					f1 = true;
					shortcut();
					break;
				case "F4":
					f4 = true;
					shortcut();
					break;
				case "F5":
					f5 = true;
					shortcut();
					break;
				default:
					System.out.println(keyP);
					break;
			}
		}	
	}

	public enum mouseDirections {
		UP, 
		DOWN,
		RIGHT,
		LEFT
	}

	public void moveMouse(mouseDirections selected) {
		String move = "";
		if (mouseDirections.UP == selected) {
			move = "UP";
		} else if (mouseDirections.DOWN == selected) {
			move = "DOWN";
		} else if (mouseDirections.LEFT == selected) {
			move = "LEFT";
		} else {
			move = "RIGHT";
		}
		ui.postMouseMove(mouse.returnNativeMouseEvent(move)); 
		
	}

	public void selectFile() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		FileFilter type = new FileFilter(){
		
			@Override
			public String getDescription() {
				return "Text Files (*.txt)";
			}
		
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					String filename = f.getName().toLowerCase();
					return filename.endsWith(".txt") || filename.endsWith(".TXT");
				}
			}
		};
		jfc.setFileFilter(type);
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			filename = selectedFile.getAbsolutePath();
			ui.updateDirectory("File : " + selectedFile.getAbsolutePath());
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) { 
		String keyP = NativeKeyEvent.getKeyText(e.getKeyCode());
		switch (keyP) {
			case "Ctrl":
				ctrl = false;
				break;
			case "F1":
				f1 = false;
				break;
			case "F4":
				f4 = false;
				break;
			case "F5":
				f5 = false;
				break;
			default:
				break;
		}
	}

	public void nativeKeyTyped(NativeKeyEvent e) { /* not implemented */ }

	public void setMouse(GlobalMouseListener m) {
		mouse = m;
	}

	public void shortcut() {
		if(ctrl && f1 && f5) {
			selectFile();
		}
		if(ctrl && f1 && f4) {
			listen = !listen;
			ui.updateActive(listen);	
		} 
	}

	public void writeAlphabet(boolean logic, String keyPressed) {
    	if (logic) {
			write(keyPressed);
		} else {
			char[] pressed = keyPressed.toCharArray();
			if (pressed.length > 1) {
				write(keyPressed);
			} else {
				write("" + (char)(pressed[0] + 32));
			}
		}
	}

	public void writeOther(String keyPressed) {
		write(keyPressed); 
	}

	public void write(String s) {
		try {
			while (filename.isEmpty() || filename.isBlank()) {
				selectFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
    		writer.append(s);
    		writer.close();
		} catch (IOException e) { 
		} catch (Exception ex) { }
	}

	public void setFrame(Main main) {
		ui = main;
	}
}
