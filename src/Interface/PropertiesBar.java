package Interface;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Interface.Config.Actions;
import VectorEditor.VectorShape;

/**
 * v1.1.1
 * PropertiesBar is a GUI class. It constructs the bar which allows a user
 * update the properties of the canvas shapes and updates itself due to the
 * changes of canvas.
 * Class implements a Canvas observer class which allows changes in a class
 * due to the changes of observable
 * @author Þilvinas
 *
 */
public class PropertiesBar implements CanvasObserver {
	
	//Local constants
	private final Border LABEL_PADDING = BorderFactory.createEmptyBorder(0,10,0,10);
	private final Border PANEL_PADDING = BorderFactory.createEmptyBorder(6,0,0,0);
	private final int SPINNER_GAP = 15;
	private final int CONTAINER_WIDTH = 900;
	private final int CONTAINER_HEIGHT = 900;
	//Defines the role. Either Observer or Observable
	private boolean isObserver = false;
	//List of observers
	private List<UserActivityObserver> observers = new ArrayList<UserActivityObserver>();
	
	//GUI elements
	private JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JSpinner strokeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
	private JSpinner opacitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
	private JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
	private JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
	private JSpinner ySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
	private JSpinner xSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
	private JLabel selection = new JLabel("No Selection");
	
	/**
	 * Construct the property bar's GUI
	 */
	public PropertiesBar() {
		//Build a bar's layout
		container.setPreferredSize(new Dimension(CONTAINER_WIDTH, CONTAINER_HEIGHT));
		container.setBackground(Config.PANEL_COLOR);
		Box box = new Box(BoxLayout.X_AXIS);
		box.setBorder(PANEL_PADDING);
		
		//Build a selection section
		selection.setForeground(Config.FONT_COLOR);
		selection.setBorder(LABEL_PADDING);
		selection.setPreferredSize(new Dimension(100, 10));
		box.add(selection);
		
		//Build a stroke section
		JLabel strokeLabel = new JLabel("stroke:");
		strokeLabel.setForeground(Config.FONT_COLOR);
		strokeLabel.setBorder(LABEL_PADDING);
		box.add(strokeLabel);
  	
  	strokeSpinner.getEditor().getComponent(0).setBackground(Config.INPUT_AREA_COLOR);
  	strokeSpinner.setPreferredSize(new Dimension(55, 20));
  	strokeSpinner.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Config.SPINNER_TEXT_COLOR));
  	strokeSpinner.getEditor().setFont(Config.SMALL_FONT);
  	strokeSpinner.getEditor().setForeground(Config.SPINNER_TEXT_COLOR);
  	strokeSpinner.setUI(new DarkSpinner());
  	strokeSpinner.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
      	if(widthSpinner.isEnabled() & !isObserver) {
					notifyAllObservers(getData());
	    	}
		  }
		});
    box.add(strokeSpinner);
  
    box.add(Box.createHorizontalStrut(SPINNER_GAP));
     
    //Build an opacity section
		JLabel opacityLabel = new JLabel("opacity:");
		opacityLabel.setForeground(Config.FONT_COLOR);
		opacityLabel.setBorder(LABEL_PADDING);
		box.add(opacityLabel);
		
	  opacitySpinner.getEditor().getComponent(0).setBackground(Config.INPUT_AREA_COLOR);
	  opacitySpinner.setPreferredSize(new Dimension(55, 20));
	  opacitySpinner.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Config.SPINNER_TEXT_COLOR));
	  opacitySpinner.getEditor().setFont(Config.SMALL_FONT);
	  opacitySpinner.getEditor().setForeground(Config.SPINNER_TEXT_COLOR);
	  opacitySpinner.setUI(new DarkSpinner());
	  opacitySpinner.addChangeListener(new ChangeListener() {
	  	public void stateChanged(ChangeEvent e) {
	  		if(widthSpinner.isEnabled() & !isObserver) {
					notifyAllObservers(getData());
	    	}
	  	}
	  });
		box.add(opacitySpinner);
		
		box.add(Box.createHorizontalStrut(SPINNER_GAP));
     
		//Build a width section
		JLabel widthLabel = new JLabel("width:");
		widthLabel.setForeground(Config.FONT_COLOR);
		widthLabel.setBorder(LABEL_PADDING);
		box.add(widthLabel);
		
  	widthSpinner.getEditor().getComponent(0).setBackground(Config.INPUT_AREA_COLOR);
  	widthSpinner.setPreferredSize(new Dimension(55, 20));
  	widthSpinner.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Config.SPINNER_TEXT_COLOR));
  	widthSpinner.getEditor().setFont(Config.SMALL_FONT);
  	widthSpinner.getEditor().setForeground(Config.SPINNER_TEXT_COLOR);
  	widthSpinner.setUI(new DarkSpinner());
  	widthSpinner.addChangeListener(new ChangeListener() {
  		public void stateChanged(ChangeEvent e) {
	    	if(widthSpinner.isEnabled()  & !isObserver) {
					notifyAllObservers(getData());
	    	}
	  	}
	  });
    box.add(widthSpinner);
    
    box.add(Box.createHorizontalStrut(SPINNER_GAP));
    
    //Build a height section
		JLabel heightLabel = new JLabel("height:");
    heightLabel.setForeground(Config.FONT_COLOR);
    heightLabel.setBorder(LABEL_PADDING);
		box.add(heightLabel);
		
  	heightSpinner.getEditor().getComponent(0).setBackground(Config.INPUT_AREA_COLOR);
  	heightSpinner.setPreferredSize(new Dimension(55, 20));
  	heightSpinner.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Config.SPINNER_TEXT_COLOR));
  	heightSpinner.getEditor().setFont(Config.SMALL_FONT);
  	heightSpinner.getEditor().setForeground(Config.SPINNER_TEXT_COLOR);
  	heightSpinner.setUI(new DarkSpinner());
  	heightSpinner.addChangeListener(new ChangeListener() {
  		public void stateChanged(ChangeEvent e) {
  			if(widthSpinner.isEnabled()  & !isObserver) {
					notifyAllObservers(getData());
	    	}
	  	}
	  });
    box.add(heightSpinner);
    
    box.add(Box.createHorizontalStrut(SPINNER_GAP));
    
    //Build an x-position section
    JLabel xLabel = new JLabel("x:");
    xLabel.setForeground(Config.FONT_COLOR);
    xLabel.setBorder(LABEL_PADDING);
		box.add(xLabel);
	
  	xSpinner.getEditor().getComponent(0).setBackground(Config.INPUT_AREA_COLOR);
  	xSpinner.setPreferredSize(new Dimension(55, 20));
  	xSpinner.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Config.SPINNER_TEXT_COLOR));
  	xSpinner.getEditor().setFont(Config.SMALL_FONT);
  	xSpinner.getEditor().setForeground(Config.SPINNER_TEXT_COLOR);
  	xSpinner.setUI(new DarkSpinner());
  	xSpinner.addChangeListener(new ChangeListener() {
  		public void stateChanged(ChangeEvent e) {
  			if(widthSpinner.isEnabled()  & !isObserver) {
					notifyAllObservers(getData());
	    	}
	  	}
	  });
    box.add(xSpinner);
    
    box.add(Box.createHorizontalStrut(SPINNER_GAP));
    
    //Build a y-position section
    JLabel yLabel = new JLabel("y:");
    yLabel.setForeground(Config.FONT_COLOR);
    yLabel.setBorder(LABEL_PADDING);
		box.add(yLabel);
		
  	ySpinner.getEditor().getComponent(0).setBackground(Config.INPUT_AREA_COLOR);
  	ySpinner.setPreferredSize(new Dimension(55, 20));
  	ySpinner.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Config.SPINNER_TEXT_COLOR));
  	ySpinner.getEditor().setFont(Config.SMALL_FONT);
  	ySpinner.getEditor().setForeground(Config.SPINNER_TEXT_COLOR);
  	ySpinner.setUI(new DarkSpinner());
  	ySpinner.addChangeListener(new ChangeListener() {
  		public void stateChanged(ChangeEvent e) {
  			if(widthSpinner.isEnabled()  & !isObserver) {
					notifyAllObservers(getData());
	    	}
	  	}
	  });
    box.add(ySpinner);
		
    //Populate the container
		container.add(box);
	}
	
	/**
	 * Returns an instance of the properties bar
	 * @return
	 */
	public JPanel renderPanel() {
		return this.container;
	}
	
	/**
	 * Set the selection section's text
	 * @param text
	 */
	public void setSelection(String text) {
		this.selection.setText(text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase());
	}
	
	/**
	 * Disables or enables the spinners
	 * true - to enable, false - to disable
	 * @param setting
	 */
	public void setSpinnerEnabled(boolean setting) {
		this.strokeSpinner.setEnabled(setting);
		this.opacitySpinner.setEnabled(setting);
		this.widthSpinner.setEnabled(setting);
		this.heightSpinner.setEnabled(setting);
		this.xSpinner.setEnabled(setting);
		this.ySpinner.setEnabled(setting);
	}
	
	/**
	 * Adds observers to class
	 * @param observer
	 */
	public void attatch(UserActivityObserver observer) {
		this.observers.add(observer);
	}
	
	/**
	 * Notifies all observers and passes changed data
	 * @param data
	 */
	public void notifyAllObservers(ArrayList<Integer> data){
	   for (UserActivityObserver observer : observers) {
	      observer.update(data);
	   }
	}
	
	/**
	 * Sets a value of a stroke spinner
	 * @param value
	 */
	public void setStrokeSpinnerValue(int value) {
		this.strokeSpinner.setValue(value);
	}
	
	/**
	 * Sets a value of an opacity spinner
	 * @param value
	 */
	public void setOpacitySpinnerValue(int value) {
		this.opacitySpinner.setValue(value);
	}
	
	/**
	 * Sets a value of a width spinner
	 * @param value
	 */
	public void setWidthSpinnerValue(int value) {
		this.widthSpinner.setValue(value);
	}
	
	/**
	 * Sets a value of a height spinner
	 * @param value
	 */
	public void setHeightSpinnerValue(int value) {
		this.heightSpinner.setValue(value);
	}
	
	/**
	 * Sets a value of an x-position spinner
	 * @param value
	 */
	public void setxSpinnerValue(int value) {
		this.xSpinner.setValue(value);
	}
	
	/**
	 * Sets a value of a y-position spinner
	 * @param value
	 */
	public void setySpinnerValue(int value) {
		this.ySpinner.setValue(value);
	}
	
	/**
	 * Returns a selection section's value
	 * @return
	 */
	public String getSelection() {
		return this.selection.getText();
	}
	
	/**
	 * Returns a stroke spinner's value
	 * @return
	 */
	public int getStrokeSpinnerValue() {
		return (Integer) this.strokeSpinner.getValue();
	}
	
	/**
	 * Returns an opacity spinner's value
	 * @return
	 */
	public int getOpacitySpinnerValue() {
		return (Integer) this.opacitySpinner.getValue();
	}
	
	/**
	 * Returns a width spinner's value
	 * @return
	 */
	public int getWidthSpinnerValue() {
		return (Integer) this.widthSpinner.getValue();
	}
	
	/**
	 * Returns a height spinner's value
	 * @return
	 */
	public int getHeightSpinnerValue() {
		return (Integer) this.heightSpinner.getValue();
	}
	
	/**
	 * Returns an x-position spinner's value
	 * @return
	 */
	public int getxSpinnerValue() {
		return (Integer) this.xSpinner.getValue();
	}
	
	/**
	 * Returns a y-position spinner's value
	 * @return
	 */
	public int getySpinnerValue() {
		return (Integer) this.ySpinner.getValue();
	}
	
	/**
	 * Returns an array list of changed data
	 * @return
	 */
	private ArrayList<Integer> getData() {
		ArrayList<Integer> data = new ArrayList<Integer>();
		data.clear();
		data.add(getStrokeSpinnerValue());
		data.add(getOpacitySpinnerValue());
		data.add(getWidthSpinnerValue());
		data.add(getHeightSpinnerValue());
		data.add(getxSpinnerValue());
		data.add(getySpinnerValue());
		return data;
	}

	/**
	 * updates the properties bar's fields due to changes of 
	 * the observable classes
	 */
	@Override
	public void updateProperties(VectorShape v) {
		isObserver =  true;
		if(v.isSelected()) {
			setStrokeSpinnerValue(v.getStrokeSize());
			setOpacitySpinnerValue((int) v.getOpacity());
			setWidthSpinnerValue(v.getWidth());
			setHeightSpinnerValue(v.getHeight());
			setxSpinnerValue(v.getPosition().x);
			setySpinnerValue(v.getPosition().y);
			setSelection(v.getTypeAsString());
			setSpinnerEnabled(true);
		} else {
			setSpinnerEnabled(false);
			setStrokeSpinnerValue(0);
			setOpacitySpinnerValue(0);
			setWidthSpinnerValue(0);
			setHeightSpinnerValue(0);
			setxSpinnerValue(0);
			setySpinnerValue(0);
			setSelection("No selection");
		}
		isObserver = false;
	}

	@Override
	public void updateShapeList(ArrayList<VectorShape> shapes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHistoryList(Actions act) {
		// TODO Auto-generated method stub
		
	}
	
}
