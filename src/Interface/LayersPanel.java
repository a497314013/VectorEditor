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
public class LayersPanel implements CanvasObserver {
	
	//Local constants
	private final int CONTAINER_WIDTH = 260;
	private final int CONTAINER_HEIGHT = 500;
	private final Font FONT = new Font("Arial", Font.PLAIN, 13);
	private final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 12);
	private int currentNumber = 0;
	
	//Lists of observers
	private List<UserActivityObserver> observers = 
			new ArrayList<UserActivityObserver>();
	
	//Temporal shape list instance
	private ArrayList<VectorShape> shapes;
	//Field list instance
	private List<Field> fields = new ArrayList<Field>();
	
	//GUI elements
	private JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private Box box = Box.createVerticalBox();
	private JScrollPane scrollPane;
	private JPanel layersOptions = new JPanel();
	private JLabel layersNumber = new JLabel("0 Layers");
	private JButton removeButton;
	private JButton forwardButton;
	private JButton backButton;
	
	/**
	 * Dependent inner class which manages a single layer field
	 * Allows visibility, lock and activity change
	 * Extends JPanel
	 * @author Þilvinas
	 */
	private class Field extends JPanel {
		private int id;
		public JButton visibilityButton;
		public JButton lockButton;
		public JLabel shapeType;
		
		private boolean lock = false;
		private boolean visible = true; 
		private boolean active = false;
		
		/**
		 * Construct the field's layout
		 * @param name
		 */
		public Field(String name) {
			setPreferredSize(new Dimension(CONTAINER_WIDTH, 45));
			setMaximumSize(getPreferredSize());
			setBackground(new Color(73, 73, 73));
			setName(name);
			// Visibility button
			visibilityButton = new JButton(new ImageIcon(getClass().getClassLoader()
	        .getResource("resources/visible.png")));
			visibilityButton.setPreferredSize(new Dimension(20, 35));
			visibilityButton.setFocusable(false);
			// Lock button
			lockButton = new JButton(new ImageIcon(getClass().getClassLoader()
	        .getResource("resources/blanc.png")));
			lockButton.setPreferredSize(new Dimension(16, 35));
			lockButton.setFocusable(false);
			// Shape type icon
			shapeType = new JLabel("",  new ImageIcon(getClass().getClassLoader()
	        .getResource("resources/" + getName() + ".png")), JLabel.CENTER);
			shapeType.setPreferredSize(new Dimension(35, 35));
		}
		
		// Sets the field active
		public void setActive() {
			active = true;
			shapeType.setIcon(new ImageIcon(getClass().getClassLoader()
	        .getResource("resources/" + getName() + "_hover.png")));
			if(visible) {
				visibilityButton.setIcon(new ImageIcon(getClass().getClassLoader()
		        .getResource("resources/visible_hover.png")));
			} else {
				visibilityButton.setIcon(new ImageIcon(getClass().getClassLoader()
		        .getResource("resources/blanc_hover.png")));
			}
			
			if(lock) {
				lockButton.setIcon(new ImageIcon(getClass().getClassLoader()
		        .getResource("resources/lock_hover.png")));
			} else {
				lockButton.setIcon(new ImageIcon(getClass().getClassLoader()
		        .getResource("resources/blanc_hover.png")));
			}
			
			setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, 
					new Color(0, 224, 247)));
			setBackground(new Color(91, 91, 91));
		}
		
		// Sets the field inactive
		public void setInactive() {
			active = false;
			shapeType.setIcon(new ImageIcon(getClass().getClassLoader()
	        .getResource("resources/" + getName() + ".png")));
			
			if(visible) {
				visibilityButton.setIcon(new ImageIcon(getClass().getClassLoader()
		        .getResource("resources/visible.png")));
			} else {
				visibilityButton.setIcon(new ImageIcon(getClass().getClassLoader()
		        .getResource("resources/blanc.png")));
			}
			
			if(lock) {
				lockButton.setIcon(new ImageIcon(getClass().getClassLoader()
		        .getResource("resources/lock.png")));
			} else {
				lockButton.setIcon(new ImageIcon(getClass().getClassLoader()
		        .getResource("resources/blanc.png")));
			}
			
			setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(112, 112, 112)));
    	setBackground(new Color(73, 73, 73));
		}
		
		/**
		 * Defines in the shape is visible
		 * @param visible
		 */
		public void setVisible(boolean visible) {
			this.visible = visible;
			if(active) {
				setActive();
			} else {
				setInactive();
			}
		}
		
		/**
		 * Defines if the shape is locked
		 * @param lock
		 */
		public void setLock(boolean lock) {
			this.lock = lock;
			if(active) {
				setActive();
			} else {
				setInactive();
			}
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
	public LayersPanel() {
		
		container.setLayout(new BorderLayout());

		box.setSize(new Dimension(CONTAINER_WIDTH, CONTAINER_HEIGHT));
		box.setBackground(new Color(73, 73, 73));
		box.setBorder(null);
		
		
		// Bottom panel which allows layer order management and deletion
		layersOptions.setPreferredSize(new Dimension(CONTAINER_WIDTH, 37));
		layersOptions.setBackground(new Color(73, 73, 73));
		
		// Layers count
		layersNumber.setPreferredSize(new Dimension(150, 31));
		layersNumber.setFont(SMALL_FONT);
		layersNumber.setForeground(new Color(153, 153, 153));
		// Add layers number count label to the options panel
		layersOptions.add(layersNumber);
		
		// Remove button (trash can)
		removeButton = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/remove.png")));
		removeButton.setPreferredSize(new Dimension(20, 31));
		removeButton.setFocusable(false);
	
		// On remove button click
		removeButton.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = 0;
				boolean removed = false;
				for(Field field : fields) {
					// If there are active fields, we will delete
					if(field.getActive()) {
						field.setInactive();
						index = fields.indexOf(field);
						// Removes shape
						shapes.remove(index);
						currentNumber--;
						// Removes field from the box
						box.remove(field);
						// Canvas has to update cause the shape was deleted
						notifyToUpdateShapeList();
						layersNumber.setText(shapes.size() + " Layers");
						box.revalidate();
						box.repaint();
						// We remove the field from fields list
						removed = true;
						break;
					}
				}
				if(removed) {
					fields.remove(index);
				}
			}
		});
		
		// Add remove button to the options panel
		layersOptions.add(removeButton);
		layersOptions.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, 
				new Color(93, 93, 93)));
		
		// Move forward button
		forwardButton = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/forward.png")));
		forwardButton.setPreferredSize(new Dimension(20, 31));
		forwardButton.setFocusable(false);
		
		forwardButton.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean moved = false;
				for(Field field : fields) {
					// If there are active fields, we will move them
					if(field.getActive()) {
						// Defines swap bounds
						int index = fields.indexOf(field);
						int maxIndex = fields.size() - 1; 
						// Bring shape forward
						if(index < maxIndex) {
							Collections.swap(shapes, index, index + 1);
							Collections.swap(fields, index, index + 1);
							fields.get(index + 1).setId(fields.get(index).getId());
							// Canvas has to update cause the shape was moved
							notifyToUpdateShapeList();
							moved = true;
							break;
						}
					}
				}
				// If there were shapes rearranged, we update the layers panel
				if(moved) {
					box.removeAll();
					Collections.reverse(fields);
					for(Field field : fields) {
						box.add(field);
					}
					Collections.reverse(fields);
					box.revalidate();
					box.repaint();
				}
			}
		});
		
		// Add move forward button to the options panel
		layersOptions.add(forwardButton);
		
		// Move back button
		backButton = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/back.png")));
		backButton.setPreferredSize(new Dimension(20, 31));
		backButton.setFocusable(false);
		// Add move back button to the options panel
		layersOptions.add(backButton);
		
		backButton.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean moved = false;
				for(Field field : fields) {
					// If there are active fields, we will move them
					if(field.getActive()) {
						int index = fields.indexOf(field);
						// Bring shape back
						if(index > 0) {
							Collections.swap(shapes, index, index - 1);
							Collections.swap(fields, index, index - 1);
							fields.get(index - 1).setId(fields.get(index).getId());
							// Canvas has to update cause the shape was moved
							notifyToUpdateShapeList();
							moved = true;
							break;
						}
					}
				}
				// If there were shapes rearranged, we update the layers panel
				if(moved) {
					box.removeAll();
					Collections.reverse(fields);
					for(Field field : fields) {
						box.add(field);
					}
					Collections.reverse(fields);
					box.revalidate();
					box.repaint();
				}
			}
		});

		//Initialize the scrollable box to hold all the layer fields
		scrollPane = new JScrollPane(box, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getViewport().setBackground(new Color(73, 73, 73));
    scrollPane.setPreferredSize(new Dimension(260,225));
    scrollPane.setBorder(BorderFactory.createEmptyBorder()); 
    scrollPane.getVerticalScrollBar().setUI(new DarkScrollBar());
    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));
    
    // Complete the layout of the layers panel
    container.add(layersOptions, BorderLayout.SOUTH);
    container.add(scrollPane);
    
	}
	
	/**
	 * Populates layers pannel with fields that represent currently
	 * drawn shapes on canvas
	 * @param shapes
	 */
	public void populate(ArrayList<VectorShape> shapes) {
		fields.clear();
		for(int i = 0; i < shapes.size(); i++) {
			
			Field field = new Field(shapes.get(i).getTypeAsString().toLowerCase());
			fields.add(field);
			field.setId(fields.indexOf(field));
			
			layersNumber.setText(shapes.size() + " Layers");
			
			field.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        	int id = fields.indexOf(field);
        	for (Field field : fields) {
        			int index = fields.indexOf(field);
        			field.setInactive();
	        		notifyToUpdateShapeListItem(index, shapes.get(index).isVisible(), shapes.get(index).isLocked(), false);
        	}
        	notifyToUpdateShapeListItem(id, shapes.get(id).isVisible(), shapes.get(id).isLocked(), true);
	        field.setActive();
        }
			});
			
			field.visibilityButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        	int index = fields.indexOf(field);
        	if(shapes.get(index).isVisible()) {
        		notifyToUpdateShapeListItem(index, false, shapes.get(index).isLocked(), shapes.get(index).isSelected());
        		field.setVisible(false);
        	} else {
        		notifyToUpdateShapeListItem(index, true, shapes.get(index).isLocked(), shapes.get(index).isSelected());
        		field.setVisible(true);
        	}
        }
			});
			
			field.add(field.visibilityButton);
			
			field.lockButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        	int index = fields.indexOf(field);
        	if(shapes.get(index).isLocked()) {
        		notifyToUpdateShapeListItem(index, shapes.get(index).isVisible(), false,  shapes.get(index).isSelected());
        		field.setLock(false);
        	} else {
        		notifyToUpdateShapeListItem(index, shapes.get(index).isVisible(), true, shapes.get(index).isSelected());
        		field.setLock(true);
        	}
        }
			});
			
			field.add(field.lockButton);
			
			field.add(field.shapeType);
			
			JLabel shapeName = new JLabel();
			shapeName.setFont(FONT);
			shapeName.setPreferredSize(new Dimension(105, 35));
			shapeName.setForeground(new Color(206, 206, 206));
			String text = shapes.get(i).getName().toLowerCase();
			text.substring(0,1).toUpperCase();
			text = Character.toUpperCase(text.charAt(0)) + text.substring(1);
			shapeName.setText(text);
			field.add(shapeName);
			
			field.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(112, 112, 112)));
			box.revalidate();
			box.repaint();
		}
		box.removeAll();
		Collections.reverse(fields);
		for(Field field : fields) {
			box.add(field);
		}
		Collections.reverse(fields);
		currentNumber = shapes.size();	
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
	public void update(ArrayList<VectorShape> shapes) {
		this.shapes = shapes;
		populate(shapes);
		for(VectorShape shape : shapes) {
			int index = shapes.indexOf(shape);
			if(shape.isSelected()) {
				fields.get(index).setActive();
				if(shape.isLocked()) {
					fields.get(index).setLock(true);
				}
				if(!shape.isVisible()) {
					fields.get(index).setVisible(false);
				}
			} else {
				fields.get(index).setInactive();
				if(shape.isLocked()) {
					fields.get(index).setLock(true);
				}
				if(!shape.isVisible()) {
					fields.get(index).setVisible(false);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param id
	 * @param visible
	 * @param locked
	 * @param selected
	 */
	public void notifyToUpdateShapeListItem(int id, boolean visible, boolean locked, boolean selected) {
		for (UserActivityObserver observer : observers) {
      observer.updateShapeListItem(id, visible, locked, selected);
   }
	}
	
	public void notifyToUpdateShapeList() {
		for (UserActivityObserver observer : observers) {
      observer.updateShapeList(this.shapes);
   }
	}

	@Override
	public void updateProperties(VectorShape v) {
	}

	@Override
	public void updateShapeList(ArrayList<VectorShape> shapes) {
		update(shapes);
	}

	@Override
	public void updateHistoryList(Actions act) {
		// TODO Auto-generated method stub
		
	}
}
