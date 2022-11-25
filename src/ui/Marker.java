package ui;

import java.awt.Rectangle;

public class Marker {

	private Rectangle bounds;
	private boolean marked = false;
	
	public Marker(int x, int y, int width, int height) {
		this.bounds = new Rectangle(x, y, width, height);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public boolean marked() {
		return marked;
	}
	
	public void mark() {
		marked = !marked;
	}
}
