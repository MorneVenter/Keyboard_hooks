package com.itrw316.keyboardhook;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileFilter;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
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
			switch (keyP) {
				case "Unknown keyCode: 0xe4e":
					writeOther("+");
					break;
				case "Unknown keyCode: 0xe4a":
					writeOther("-");
					break;
				case "Print Screen":
					break;
				case "Num Lock":
					break;
				case "Undefined":
					break;
				case "Insert":
					break;
				case "Delete":
					break;
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
				case "Unknown keyCode: 0xe36":
					shift = true;
					break;
				case "Semicolon":
					writeOther(";");
					break;
				case "Minus":
					if (shift) {
						writeOther("=");
					} else {
						writeOther("-");
					}
					break;
				case "Back Quote":
					if (shift) {
						writeOther("`");
					} else {
						writeOther("@");
					}
					break;
				case "Open Bracket":
					if (shift) {
						writeOther("{");
					} else {
						writeOther("[");
					}
					break;
				case "Close Bracket":
					if (shift) {
						writeOther("}");
					} else {
						writeOther("]");
					}
					break;
				case "Equals":
					writeOther("=");
					break;
				case "Quote":
					if (shift) {
						writeOther("\"");
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
				case "Ctrl":
					break;
				case "Escape":
					break;
				case "Alt":
					break;
				case "Tab":
					break;
				case "Backspace":
					break;
				case "Period":
					if (shift) {
						writeOther(">");
					} else {
						writeOther(".");
					}
					break;
				case "Comma":
					if (shift) {
						writeOther("<");
					} else {
						writeOther(",");
					}
					break;
				case "Clear":
					break;
				case "1":
					if (shift) {
						writeOther("!");
					} else {
						writeOther(keyP);
					}
					break;
				case "2":
					if (shift) {
						writeOther("\"");
					} else {
						writeOther(keyP);
					}
					break;
				case "3":
					if (shift) {
						writeOther("#");
					} else {
						writeOther(keyP);
					}
					break;
				case "4":
					if (shift) {
						writeOther("$");
					} else {
						writeOther(keyP);
					}
					break;
				case "5":
					if (shift) {
						writeOther("%");
					} else {
						writeOther(keyP);
					}
					break;
				case "6":
					if (shift) {
						writeOther("&");
					} else {
						writeOther(keyP);
					}
					break;
				case "7":
					if (shift) {
						writeOther("'");
					} else {
						writeOther(keyP);
					}
					break;
				case "8":
					if (shift) {
						writeOther("(");
					} else {
						writeOther(keyP);
					}
					break;
				case "9":
					if (shift) {
						writeOther(")");
					} else {
						writeOther(keyP);
					}
					break;
				case "0":
					if (!shift) {
						writeOther("0");
					}
					break;
				case "P":
					if (!shift) {
						writeOther("0");
					}
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
				case "F1":
					f1 = true;
					shortcut();
					writeOther(keyP);
					break;
				case "F4":
					f4 = true;
					shortcut();
					writeOther(keyP);
					break;
				case "F5":
					f5 = true;
					shortcut();
					writeOther(keyP);
					break;
				default:
					if (shift) {
						writeAlphabet(!capslock, keyP);
					} else {
						writeAlphabet(capslock, keyP);
					}
					break;
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
		mouse.executeMouseEvent(move); 
		
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
			case "F1":
				f1 = false;
				break;
			case "F4":
				f4 = false;
				break;
			case "F5":
				f5 = false;
				break;
			case "Shift":
				shift = false;
				break;
			case "Unknown keyCode: 0xe36":
				shift = false;
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
		if(f1 && f5) {
			selectFile();
		}
		if(f1 && f4) {
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
		if (listen) {
			while (filename.isEmpty()) {
				selectFile();
			}
			try {
				FileWriter write = new FileWriter(filename, true);
				PrintWriter print = new PrintWriter(write);
				print.print(s);
				print.close();
			} catch (IOException e) { 
			} catch (Exception ex) { }
		} else {
			System.out.println(s);
		}
	}

	public void setFrame(Main main) {
		ui = main;
	}
}
