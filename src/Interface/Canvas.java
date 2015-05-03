package Interface;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import VectorEditor.Rectangle;
import VectorEditor.VectorContainer;
import VectorEditor.VectorShape;



/**
 * v1.1.1
 * The class Canvas extends JPanel and implements MouseListener, MouseMotionListener, 
 * MouseWheelListener. This is the main class which provides an ability of drawing,
 * selecting, and interacting with vector shape instances
 * @author Þilvinas
 *
 */
public class Canvas extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	private int id;
	private int width, height;
	//Mouse motion indicators
	private Point drawStart, drawEnd;
	//Indicates mouse dragging state
	private boolean dragging = false;
	//List of available tools
	private enum Tools {
		PICK,
		LINE,
		RECTANGLE,
		ELIPSE,
		POLYGON,
		EYEDROPPER
	}
	private Tools tool = Tools.RECTANGLE;
	private List<CanvasObserver> observers = new ArrayList<CanvasObserver>();
	
	//Temporal tools count for demo
	int count;
	
	/**
	 * Construct a canvas and sets up layout
	 * @param width
	 * @param height
	 */
	public Canvas(int width, int height) {
		setLayout(null);
		this.width = width;
		this.height = height;
		super.setPreferredSize(new Dimension(this.width, this.height));
    super.setBackground(new Color(232, 232, 232)); 
    super.addMouseListener(this);
    super.addMouseWheelListener(this);
    super.addMouseMotionListener(this); 
	}
	
	public void attatch(CanvasObserver o) {
		this.observers.add(o);
	}
	
	public void notifyToUpdatePropertiesPanel(VectorShape v){
	   for (CanvasObserver observer : observers) {
	      observer.updateProperties(v);
	   }
	}
	public void notifyToUpdateLayersPanel() {
		for (CanvasObserver observer : observers) {
      observer.updateShapeList(VectorContainer.get());
   }
	}
	public void notifyToUpdateHistoryPanel(Config.Actions act) {
		for (CanvasObserver observer : observers) {
      observer.updateHistoryList(act);
   }
	}
	
	/**
	 * Sets canvas id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Sets canvas width
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Set's canvas height
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Returns canvas id
	 * @return
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Returns canvas width
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Returns canvas height
	 */
	public int getHeight() {
		return this.height;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		//On left button pressed:
		if(SwingUtilities.isLeftMouseButton(e)){
	  	drawStart = new Point(e.getX(), e.getY());
	  	drawEnd = drawStart;
	  	//Tool switch
	  	switch(tool) {
	  	
	  	case PICK:
	  		//Scan layers in order DESC by time created
	      for(int i = VectorContainer.lastIndex(); i >= 0; i--) {
	      	//If a shape in selection area
	      	if(VectorContainer.get(i).getShape().contains(e.getPoint()) && !VectorContainer.get(i).isLocked()) {
	      		//if(!VectorContainer.get(i).isSelected()) {
	      			//Deselect any selected shapes
	      			for(int j = VectorContainer.lastIndex(); j >= 0; j--) {
	      				VectorContainer.get(j).deselect();
	      			}
	      			VectorContainer.get(i).select();
	      			notifyToUpdateLayersPanel();
	      			notifyToUpdatePropertiesPanel(VectorContainer.get(i));
	      		/*} else if (!dragging){
	      			//vectorShapes.get(i).deselect();	
	      		} else {
	      			dragging = false;
	      		}	 */     		
	      		repaint();
	      		break;
	        } else {
	        	VectorContainer.get(i).deselect();
	        	notifyToUpdatePropertiesPanel(VectorContainer.get(i));
	        	notifyToUpdateLayersPanel();
	  	      repaint();
	        }
	      }
	      break;
	      
	  	default:
	  		break;
	  	}
		}
  }
	
	@Override
	public void mouseReleased(MouseEvent e) {
		//On left button pressed:
		if(SwingUtilities.isLeftMouseButton(e)){	
	  	switch(tool) {
	  	
	  	case PICK:
	      break;
	      
	  	case RECTANGLE:
	  		//Creates a canvas shape
	    	VectorShape rect = new Rectangle(drawStart.x, drawStart.y, e.getX(), e.getY());
	    	if (drawStart.x != e.getX() && drawStart.y != e.getY()) {
	    		VectorContainer.add(rect);
	    		
	    		notifyToUpdateHistoryPanel(Config.Actions.RECTANGLE_TOOL_USED);
	    		notifyToUpdateLayersPanel();
	    		
	        drawStart = null;
	        drawEnd = null;
	        count++;
	        if(count == 6) {
	        	this.tool = Tools.PICK;
	        }
		      repaint();
	      }
	      break;
	      
	    	default:
				break;
	    }
	  	
	  	
		}
	}
 
	@Override
	public void mouseDragged(MouseEvent e) {
	 	switch(tool) {
	 	case PICK:
	 		
	 		//Scan layers in order they were created
	     for(int i = VectorContainer.lastIndex(); i >= 0; i--) {
	    	 if(VectorContainer.get(i).isSelected() && !VectorContainer.get(i).isLocked()) {
	     		if(VectorContainer.get(i).getShape().contains(e.getPoint()) && VectorContainer.get(i).getShape().contains(drawEnd)) {
	     			VectorContainer.get(i).drag(drawStart, e.getPoint());
		     		drawStart = new Point(e.getX(), e.getY());
	  	      drawEnd = drawStart;
	  	      dragging = true;
	  	      notifyToUpdatePropertiesPanel(VectorContainer.get(i));
	  	      //notifyToUpdateHistoryPanel(Config.Actions.SHAPE_MOVED);
		     		repaint();
		     		break;
	     		}
	      }
	    }
	    break;
	 	case RECTANGLE:
	 	  //Create guides
	   	drawEnd = new Point(e.getX(), e.getY());
	    repaint();
	    break;
	 	default:
			break;  
	 	}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		switch(tool) {
  	case PICK:
  		//Scan layers in order they were created
      for(int i = VectorContainer.lastIndex(); i >= 0; i--) {
      	//If shape in selection are
      	if(VectorContainer.get(i).isSelected() && !(VectorContainer.get(i).isLocked())) {
      		if(e.getWheelRotation() > 0) {
      			VectorContainer.get(i).transform(5, 0);
      			notifyToUpdatePropertiesPanel(VectorContainer.get(i));
      		} else {
      			VectorContainer.get(i).transform(5, 1);
      			notifyToUpdatePropertiesPanel(VectorContainer.get(i));
      		}
      		repaint();
      		break;
      	}
      }
      break;
  	default:
  		break;
  	}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphSettings = (Graphics2D)g;
    graphSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    
    //Paints shapes on canvas
    for(int i = 0; i < VectorContainer.size(); i++) {
    	if(VectorContainer.get(i).isVisible()) {
	    	graphSettings.setPaint(VectorContainer.get(i).getFillColor());
	    	graphSettings.fill(VectorContainer.get(i).getShape());
	    	
	    	graphSettings.setPaint(VectorContainer.get(i).getStrokeColor());
	    	graphSettings.setStroke(new BasicStroke(VectorContainer.get(i).getStrokeSize()));
	    	graphSettings.draw(VectorContainer.get(i).getShape());
    	}
    }
    
    //Guide shape used for drawing
    if (drawStart != null && drawEnd != null) {
    	// Makes the guide shape transparent
      graphSettings.setComposite(AlphaComposite.getInstance(
      			AlphaComposite.SRC_OVER, 0.40f));
      // Make guide shape gray for professional look
      graphSettings.setStroke(new BasicStroke(1));
    	graphSettings.setPaint(Color.BLUE);
    	// Create a new rectangle using x & y coordinates
    	Rectangle rect = new Rectangle(drawStart.x, drawStart.y, drawEnd.x, drawEnd.y, 1);
      graphSettings.draw(rect.getShape());
    }  
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	public void update(ArrayList<Integer> data) {
		
		for(int i = VectorContainer.lastIndex(); i >= 0; i--) {
    	if(VectorContainer.get(i).isSelected()) {
    		
    		VectorContainer.get(i).setStrokeSize(data.get(0));
    		VectorContainer.get(i).setOpacity(data.get(1));
    		VectorContainer.get(i).resize(data.get(2), data.get(3));
    		VectorContainer.get(i).setPosition(data.get(4), data.get(5));
    		repaint();
    		break;
      } else {
      	VectorContainer.get(i).deselect();
      	notifyToUpdatePropertiesPanel(VectorContainer.get(i));
	      repaint();
      }
    }	
	}

	public void updateShapeListItem(int id, boolean visible, boolean locked, boolean selected) {
		VectorContainer.get(id).setVisible(visible);
		VectorContainer.get(id).setLocked(locked);
		if (selected) {
			VectorContainer.get(id).select();
		} else {
			VectorContainer.get(id).deselect();
		}
	}

	public void updateShapeList(ArrayList<VectorShape> data) {
		VectorContainer.set(data);
		notifyToUpdateLayersPanel();
		repaint();
	}
	
	public void rewindHistory(int step) {
		ArrayList<VectorShape> tmp = VectorContainer.getStep(step);
		for(VectorShape s : tmp) {
			s.deselect();
		}
		VectorContainer.set(tmp);
		notifyToUpdateLayersPanel();
	}
	
}
