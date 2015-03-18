package VectorEditor;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import javax.swing.JComponent;

/**
 * v1.1.1
 * VectorShape in an abstract class which defines a list of generic 
 * features any vector shape can have
 * @author Þilvinas
 */
public abstract class VectorShape extends JComponent {
	
	// Constants for default shape style
	protected final Color DEFAULT_FILL_COLOR = new Color(93, 169, 232);
	protected final Color DEFAULT_STROKE_COLOR = new Color(65, 134, 191);
	protected final int DEFAULT_STROKE_SIZE = 3;
	protected final Color SELECTED_STROKE_COLOR = new Color(0, 255, 64);
	protected final int SELECTED_STROKE_SIZE = 1;

	//Shape types
	protected enum Types {
		UNSPECIFIED,
		RECTANGLE,
		LINE,
		ELIPSE,
		POLYGON,
		CUSTOM
	}
	private Types type = Types.UNSPECIFIED;
	
	private static int id = 0;
	
	private Point position;
	private int width, height;
	private double angle;
	
	private float opacity = 1;
	private Color fillColor = DEFAULT_FILL_COLOR;
	private Color strokeColor = DEFAULT_STROKE_COLOR;
	private Color strokeColorBckp = DEFAULT_STROKE_COLOR;
	private int strokeSize = DEFAULT_STROKE_SIZE;
	
	private boolean isSelected = false;
	
	private Shape instance;
	
	/**
	 * Constructs a shape
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param status 0 defines a canvas shape, else defines a guideline
	 */
	public VectorShape(int x1, int y1, int x2, int y2, int status) {
		this.width = Math.abs(x1 - x2);
		this.height = Math.abs(y1 - y2);
		this.position = new Point(Math.min(x1, x2), Math.min(y1, y2));
		if(status == 0) {
			++VectorShape.id;
		}
	}
	
	/**
	 * Useless constructor
	 */
	public VectorShape() {
		this(0, 0, 0, 0, 0);
	}
	
	/**
	 * Sets a shape's type
	 * @param type
	 */
	public final void setType(Types type) {
		this.type = type;
	}
	
	/**
	 * Sets a shape's width
	 * @param width
	 */
	public final void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Sets a shape's height
	 * @param height
	 */
	public final void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Sets shape's position
	 * @param p
	 */
	public final void setPosition(Point p) {
		this.position = p;
	}
	
	/**
	 * Sets a shape's position
	 * @param x
	 * @param y
	 */
	public final void setPosition(int x, int y) {
		this.position.x = x;
		this.position.y = y;
	}
	
	/**
	 * Sets a shape's fill color
	 * @param color
	 */
	public final void setfillColor(Color color) {
		this.fillColor = color;
	}
	
	/**
	 * Sets a shape's stroke Color
	 * @param color
	 */
	public final void setStrokeColor(Color color) {
		this.strokeColor = color;
		this.strokeColorBckp = color;
	}
	
	/**
	 * Sets a shape's stroke size
	 * @param size
	 */
	public final void setStrokeSize(int size) {
		this.strokeSize = size;
	}
	
	/**
	 * Sets a shape's opacity
	 * @param opacity (range = [0..1])
	 */
	public final void setOpeacity(float opacity) {
		this.opacity = opacity;
	}
	
	/**
	 * Sets an angle to origin for a shape
	 * @param angle (in degrees)
	 * @param status (true to count from 0, false from the last value set)
	 */
	public final void setAngle(double angle, boolean status) {
		if (status) {
			++this.angle;
		} else {
			this.angle = angle; 
		}
	}
	
	/**
	 * Defines a shape instance
	 * @param instance
	 */
	public void setShape(Shape instance) {
		this.instance = instance;
	}
	
	/**
	 * Returns a shape's name
	 * @return
	 */
	public final String getName() {
		return getType().toString() + " " + VectorShape.id;
	}
	
	/**
	 * Returns a type of a shape
	 * @return
	 */
	public final Types getType() {
		return this.type;
	}
	
	/**
	 * Returns as shapes's position
	 * (Upper left corner)
	 * @return
	 */
	public final Point getPosition() {
		return this.position;
	}
	
	/**
	 * Returns a shapes's width
	 * @return
	 */
	public final int getWidth() {
		return this.width;
	}
	
	/**
	 * Returns a shape's height
	 * @return
	 */
	public final int getHeight() {
		return this.height;
	}
	
	/**
	 * Returns a shape's fill color
	 * @return
	 */
	public final Color getFillColor() {
		return this.fillColor;
	}
	
	/**
	 * Returns a shape's stroke color
	 * @return
	 */
	public final Color getStrokeColor() {
		return this.strokeColor;
	}
	
	/**
	 * Returns a shape's stroke size
	 * @return
	 */
	public final int getStrokeSize() {
		return this.strokeSize;
	}
	
	/**
	 * Returns a shape's opacity value
	 * @return
	 */
	public final float getOpacity() {
		return this.opacity;
	}
	
	/**
	 * Returns a shape's angle to origin
	 * @return
	 */
	public final double getAngle() {
		return this.angle;
	}
	
	/**
	 * Returns a shape instance
	 * @return
	 */
	public Shape getShape() {
		return this.instance;
	}
	
	/**
	 * Selects a shape
	 */
	public final void select() {
		this.strokeColorBckp = getStrokeColor(); 
		this.strokeColor = SELECTED_STROKE_COLOR;
		this.strokeSize = SELECTED_STROKE_SIZE;
		this.isSelected = true;
	}
	
	/**
	 * Deselects a shape
	 */
	public final void deselect() {
		this.strokeColor = this.strokeColorBckp;
		this.strokeSize = DEFAULT_STROKE_SIZE;
		this.isSelected = false;
	}
	
	/**
	 * Checks a shape's selection state
	 * @return true if shape is selected
	 */
	public final boolean isSelected() {
		return this.isSelected;
	}
	
	/**
	 * Displays shape object data in a string block
	 */
	public String toString() {
		String result = new String();
		result += "VectorShape[\n\ttype=" + getType() + "\n\tname=\"" + getName() + 
				"\",\n\twidth=" + getWidth() + ",\n\theight=" + getHeight()+ ",\n\tposition=" + 
				getPosition().toString() + ",\n\tfill=" + getFillColor().toString() + 
				",\n\tstroke=[color=" + getStrokeColor().toString() + ", " + "size=" + 
				getStrokeSize() + "],\n\topacity=" + getOpacity() + ",\n\tselected=" + 
				isSelected() + "\n]";
		return result;
	}
	
	/**
	 * Prints shape object data to a standard stream
	 */
	public void println() {
		String result = new String();
		result += "VectorShape[\n\ttype=" + getType() + "\n\tname=\"" + getName() + 
				"\",\n\twidth=" + getWidth() + ",\n\theight=" + getHeight()+ ",\n\tposition=" + 
				getPosition().toString() + ",\n\tfill=" + getFillColor().toString() + 
				",\n\tstroke=[color=" + getStrokeColor().toString() + ", " + "size=" + 
				getStrokeSize() + "],\n\topacity=" + getOpacity() + ",\n\tselected=" + 
				isSelected() + "\n]";
		System.out.println(result);
	}
	
	/**
	 * @return Area of a shape
	 */
	public abstract double getArea();
	
	/**
	 * Transforms shape in all directions
	 * @param step
	 * @param direction 0 if zooming in, else if zooming out
	 */
	public abstract void transform(int step, int direction);
	
	/**
	 * Allows dragging a shape on canvas
	 * @param start
	 * @param end
	 */
	public abstract void drag(Point start, Point end);
		
}
