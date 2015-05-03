package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import VectorEditor.VectorShape;

/**
 * v1.1.1
 * Workspace class to hold canvas and workspace tools and panels
 * @author Þilvinas
 *
 */
public class Workspace extends JPanel implements UserActivityObserver {
	
	//Main layout areas
	private JPanel managerContainer = new JPanel();
	private JPanel toolBarContainer = new JPanel();
	private JPanel propertiesContainer = new JPanel();
	private JPanel statusBarContainer = new JPanel();
	private JPanel canvasContainer = new JPanel();
	
	private ToolBar toolBar = new ToolBar();
	private PropertiesBar propertiesBar = new PropertiesBar();
	private HistoryPanel historyPanel = new HistoryPanel();
	private LayersPanel layersPanel = new LayersPanel();
	
	private Canvas canvas = new Canvas(0,0);
	
	/**
	 * Sets up the main layout and renders the tool bars and panels
	 */
	public Workspace() {
		this.setLayout(new BorderLayout());
	
		//Set up properties container
		propertiesContainer.setBackground(Config.PANEL_COLOR);
		propertiesContainer.setPreferredSize(new Dimension(0, 42));
		propertiesContainer.setLayout(new BorderLayout());
		
		propertiesContainer.add(propertiesBar.renderPanel(), BorderLayout.WEST);
		propertiesBar.setSpinnerEnabled(false);
		propertiesBar.attatch(this);
		this.add(propertiesContainer, BorderLayout.NORTH);
		
		//Set up status bar container
		statusBarContainer.setBackground(Config.PANEL_COLOR);
		statusBarContainer.setPreferredSize(new Dimension(0, 26));
		this.add(statusBarContainer, BorderLayout.SOUTH);
		
		//Set up canvas container
		canvasContainer.setBackground(Config.BACKGROUND_COLOR);
		canvasContainer.setLayout(new GridBagLayout());
		
		//Set up tool bar container
		toolBarContainer.setPreferredSize(new Dimension(50, 0));
		toolBarContainer.setBackground(Config.BACKGROUND_COLOR);
		toolBarContainer.setLayout(new GridBagLayout());
		this.add(toolBarContainer, BorderLayout.WEST);
		
		toolBarContainer.add(toolBar, new GridBagConstraints());
		
		
		//Set up manager container 
		managerContainer.setPreferredSize(new Dimension(260, 0));
		managerContainer.setBackground(Config.BACKGROUND_COLOR);
		
		//Create area for three panels
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(Box.createVerticalStrut(10));
		// Create color panel
		ColorPanel colorPanel = new ColorPanel();
		box.add(colorPanel.renderPanel());
		box.add(Box.createVerticalStrut(10));
		//Create history panel
		historyPanel.attatch(this);
		box.add(historyPanel.renderPanel());
		box.add(Box.createVerticalStrut(10));
		
		//Create layers panel
		layersPanel.attatch(this);
		box.add(layersPanel.renderPanel());
		
		//Add the three panels to the manager container
		managerContainer.add(box, BorderLayout.NORTH);
		
		this.add(managerContainer, BorderLayout.EAST);	
	}
	
	/**
	 * Instantiates an artboard with a canvas
	 * @param id
	 * @param width
	 * @param height
	 */
	public void addArtboard(int id, int width, int height) {
		canvas = new Canvas(width, height);
		canvas.setId(id);
		canvas.attatch(propertiesBar);
		canvas.attatch(layersPanel);
		canvas.attatch(historyPanel);
		canvasContainer.add(canvas, new GridBagConstraints());
		this.add(canvasContainer, BorderLayout.CENTER);
	}

	@Override
	public void update(ArrayList<Integer> data) {
		canvas.update(data);
		canvas.revalidate();
		canvas.repaint();
	}

	@Override
	public void updateShapeListItem(int id, boolean visible, boolean locked, boolean selected) {
		canvas.updateShapeListItem(id, visible, locked, selected);
		canvas.revalidate();
		canvas.repaint();
	}

	@Override
	public void updateShapeList(ArrayList<VectorShape> data) {
		canvas.updateShapeList(data);
		canvas.revalidate();
		canvas.repaint();
	}

	@Override
	public void rewindHistory(int step) {
		canvas.rewindHistory(step);
		canvas.revalidate();
		canvas.repaint();
	}
	
}
