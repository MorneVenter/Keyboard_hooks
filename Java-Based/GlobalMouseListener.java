import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class GlobalMouseListener implements NativeMouseInputListener {
	int x = 0;
	int y = 0;

	public void nativeMouseClicked(NativeMouseEvent e) {
		//System.out.println("Mouse Clicked: " + e.getClickCount());
	}

	public void nativeMousePressed(NativeMouseEvent e) {
		//System.out.println("Mouse Pressed: " + e.getButton());
	}

	public void nativeMouseReleased(NativeMouseEvent e) {
		//System.out.println("Mouse Released: " + e.getButton());
	}

	public void nativeMouseMoved(NativeMouseEvent e) {
		setCoordinates(e.getX(), e.getY());
		//System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
	}

	public void nativeMouseDragged(NativeMouseEvent e) {
		setCoordinates(e.getX(), e.getY());
		//System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
	}

	public int[] getCoordinates() {
		return new int[] {x,y};
	}

	public void setCoordinates(int X, int Y) {
		x = X;
		y = Y;
	}
}