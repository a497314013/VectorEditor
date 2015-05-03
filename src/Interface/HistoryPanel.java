package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Interface.Config.Actions;
import VectorEditor.VectorShape;

/**
 * Class implements a layers panel which enables a user to see
 * currently drawn shapes, their types, names. Class also allows shape 
 * management such a z-index change, removing, setting as locked or invisible.
 * @author Þilvinas
 *
 */
public class HistoryPanel implements CanvasObserver {
	
	//Local constants
	private final int CONTAINER_WIDTH = 260;
	private final int CONTAINER_HEIGHT = 500;
	private final Font FONT = new Font("Arial", Font.PLAIN, 13);
	
	//Lists of observers
	private List<UserActivityObserver> observers = 
			new ArrayList<UserActivityObserver>();
	
	//Temporal shape list instance
	Config.Actions action;
	//Field list instance
	private List<HistoryField> fields = new ArrayList<HistoryField>();
	
	//GUI elements
	private JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private Box box = Box.createVerticalBox();
	private JScrollPane scrollPane;
	
	/**
	 * Dependent inner class which manages a single layer field
	 * Allows visibility, lock and activity change
	 * Extends JPanel
	 * @author Þilvinas
	 */
	private class HistoryField extends JPanel {
		private int id;
		private boolean active = false;
		
		/**
		 * Construct the field's layout
		 * @param name
		 */
		public HistoryField() {
			setPreferredSize(new Dimension(CONTAINER_WIDTH, 45));
			setMaximumSize(getPreferredSize());
			setBackground(new Color(73, 73, 73));
		}
		
		// Sets the field active
		public void setActive() {
			active = true;
			setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, 
					new Color(0, 224, 247)));
			setBackground(new Color(91, 91, 91));
		}
		
		// Sets the field inactive
		public void setInactive() {
			active = false;
			setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(112, 112, 112)));
    	setBackground(new Color(73, 73, 73));
		}
		
		/**
		 * Returns the fields ID
		 * @return
		 */
		public int getId() {
			return this.id;
		}
		
		/**
		 * Sets the fields ID
		 * @param id
		 */
		public void setId(int id) {
			this.id = id;
		}
		
		/**
		 * Returns fields activity state
		 * @return
		 */
		public boolean getActive() {
			return this.active;
		}
		
	}
	
	/**
	 * Constructs the layer panel's layout and set up event handlers
	 */
	public HistoryPanel() {
		
		container.setLayout(new BorderLayout());

		box.setSize(new Dimension(CONTAINER_WIDTH, CONTAINER_HEIGHT));
		box.setBackground(new Color(73, 73, 73));
		box.setBorder(null);

		//Initialize the scrollable box to hold all the layer fields
		scrollPane = new JScrollPane(box, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getViewport().setBackground(new Color(73, 73, 73));
    scrollPane.setPreferredSize(new Dimension(260,135));
    scrollPane.setBorder(BorderFactory.createEmptyBorder()); 
    scrollPane.getVerticalScrollBar().setUI(new DarkScrollBar());
    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));
    
    container.add(scrollPane);
    
	}
	
	/**
	 * Populates layers pannel with fields that represent currently
	 * drawn shapes on canvas
	 * @param shapes
	 */
	public void populate(Config.Actions act) {
		
			HistoryField field = new HistoryField();
			
			if (fields.size() < Config.maxHistory) {
				fields.add(field);
			} else {
				Collections.reverse(fields);
				Collections.rotate(fields, 1);
				Collections.reverse(fields);
				fields.set(Config.maxHistory - 1, field);
			}
			
			field.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for (HistoryField field : fields) {
      			field.setInactive();
	      	}
	        field.setActive();
	        notifyToRewindHistory(fields.indexOf(field));
	      }
			});
		
			JLabel actionName = new JLabel();
			actionName.setFont(FONT);
			actionName.setPreferredSize(new Dimension(180, 35));
			actionName.setForeground(new Color(206, 206, 206));
			String text = act.toString().replaceAll("_", " ").toLowerCase();
			text.substring(0,1).toUpperCase();
			text = Character.toUpperCase(text.charAt(0)) + text.substring(1);
			actionName.setText(text);
			field.add(actionName);
			
			field.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(112, 112, 112)));
			
			box.removeAll();
			
			Collections.reverse(fields);
			for(HistoryField f : fields) {
				box.add(f);
			}
			Collections.reverse(fields);	
			box.revalidate();
			box.repaint();
	}
	
	/**
	 * Returns an instance of the layers panel
	 * @return
	 */
	public JPanel renderPanel() {
		return this.container;
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
	 * Updates itself due to the changes on canvas
	 * @param shapes
	 */
	public void update(Config.Actions act) {
		this.action = act;
		populate(act);
		for(HistoryField field : fields) {
			field.setInactive();
		}
	}
	
	public void notifyToRewindHistory(int step) {
		for (UserActivityObserver observer : observers) {
      observer.rewindHistory(step);
   }
	}

	@Override
	public void updateProperties(VectorShape v) {
	}

	@Override
	public void updateShapeList(ArrayList<VectorShape> shapes) {
	}

	@Override
	public void updateHistoryList(Actions act) {
		update(act);
	}
	
	
}
