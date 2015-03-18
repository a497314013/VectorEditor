package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * v1.1.1
 * Workspace class to hold canvas and vorkspace tools
 * @author Þilvinas
 *
 */
public class Workspace extends JPanel {
	
	private enum Tools {
		PICK,
		LINE,
		RECTANGLE,
		ELIPSE,
		POLYGON,
		EYEDROPPER
	}
	
	public Workspace() {
		this.setLayout(new GridBagLayout());
		this.setBackground(new Color(61,61,61));
		
		
	}
	
	/**
	 * Instantiates an artboard with a canvas
	 * @param id
	 * @param width
	 * @param height
	 */
	public void addArtboard(int id, int width, int height) {
		Canvas canvas = new Canvas(width, height);
		canvas.setId(id);
		
		this.add(canvas, new GridBagConstraints());
	}
	
}
