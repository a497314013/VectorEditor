package Interface;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import VectorEditor.Rectangle;
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
	//Array list which stores shape instances
	private ArrayList<VectorShape> vectorShapes = new ArrayList<VectorShape>();
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
	
	//Temoporal tools count for demo
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
    super.setBackground(Color.WHITE); 
    super.addMouseListener(this);
    super.addMouseWheelListener(this);
    super.addMouseMotionListener(this);  	
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
	  		Collections.reverse(vectorShapes);
	      for(int i = 0; i < vectorShapes.size(); i++) {
	      	//If a shape in selection area
	      	if(vectorShapes.get(i).getShape().contains(e.getPoint())) {
	      		if(!vectorShapes.get(i).isSelected()) {
	      			//Deselect any selected shapes
	      			for(int j = 0; j < vectorShapes.size(); j++) {
	      				vectorShapes.get(j).deselect();
	      			}
		      		vectorShapes.get(i).select();
	      		} else if (!dragging){
	      			vectorShapes.get(i).deselect();	
	      		} else {
	      			dragging = false;
	      		}	      		
	      		repaint();
	      		break;
	        } else {
	        	vectorShapes.get(i).deselect();
	  	      repaint();
	        }
	      }
	      //Restore shape list order
	      Collections.reverse(vectorShapes);
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
	      	vectorShapes.add(rect);
	        drawStart = null;
	        drawEnd = null;
	        count++;
	        if(count == 3) {
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
	 		Collections.reverse(vectorShapes);
	     for(int i = 0; i < vectorShapes.size(); i++) {
	    	 if(vectorShapes.get(i).isSelected()) {
	     		if(vectorShapes.get(i).getShape().contains(e.getPoint()) && vectorShapes.get(i).getShape().contains(drawEnd)) {
		     		vectorShapes.get(i).drag(drawStart, e.getPoint());
		     		drawStart = new Point(e.getX(), e.getY());
	  	      drawEnd = drawStart;
	  	      dragging = true;
		     		repaint();
		     		break;
	     		}
	      }
	    }
	    //Restore shape list order
	    Collections.reverse(vectorShapes);
	    
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
  		Collections.reverse(vectorShapes);
      for(int i = 0; i < vectorShapes.size(); i++) {
      	//If shape in selection are
      	if(vectorShapes.get(i).isSelected()) {
      		if(e.getWheelRotation() > 0) {
      			vectorShapes.get(i).transform(5, 0);
      		} else {
      			vectorShapes.get(i).transform(5, 1);
      		}
      		repaint();
      		break;
      	}
      }
      //Restore shape list order
      Collections.reverse(vectorShapes);
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
    for(int i = 0; i < vectorShapes.size(); i++) {
    	graphSettings.setPaint(vectorShapes.get(i).getFillColor());
    	graphSettings.fill(vectorShapes.get(i).getShape());
    	
    	graphSettings.setPaint(vectorShapes.get(i).getStrokeColor());
    	graphSettings.setStroke(new BasicStroke(vectorShapes.get(i).getStrokeSize()));
    	graphSettings.draw(vectorShapes.get(i).getShape());
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
	
}
