package VectorEditor;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 * v1.1.1
 * Class Rectangle extends and implements its parent VectorShape
 * It allows creating instances of a rectangle, basic transformations
 * @author Þilvinas
 */
public class Rectangle extends VectorShape {
	
	/**
	 * Constructs a canvas shape
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public Rectangle(int x1, int y1, int x2, int y2) {
		this(x1,y1, x2, y2, 0);
	}

	/**
	 * Constructs main shape features
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param status 0 for canvas shape (default), else for guideline
	 */
	public Rectangle(int x1, int y1, int x2, int y2, int status) {
		super(x1,y1, x2, y2, status);
		super.setType(Types.RECTANGLE);
		super.setShape(new Rectangle2D.Float(getPosition().x, getPosition().y, 
				getWidth(), getHeight()));
	}

	@Override
	public double getArea() {
		return getWidth() * getHeight();
	}
	
	@Override
	public void drag(Point start, Point end) {
		//Changes in y and x while dragging
		int changeX = end.x - start.x;
		int changeY = end.y - start.y;
		
		//Reseting the shape's features after dragging
		setPosition(getShape().getBounds().x + changeX, getShape().getBounds().y + changeY);
		setShape(new Rectangle2D.Float(getPosition().x, getPosition().y, 
				getWidth(), getHeight()));
	}
	
	@Override
	public void transform(int step, int direction) {
		//Step to adjust multidirectional transformation
		int stepW, stepH;
		//New dimensions
		int newW, newH;
		//Shape's width and height ratio
		float ratio = (float) getWidth() / (float) getHeight();
		Point currentPosition = getPosition();
		
		//If zooming in
		if (direction == 0) {
			newW = getWidth() + step;
			newH = Math.round(newW / ratio);
			stepW = Math.round(step / 2);
			stepH = Math.round((newH - getHeight()) / 2);
			setPosition(currentPosition.x - stepW, currentPosition.y - stepH);
			setWidth(newW);
			setHeight(newH);
		//If zooming out and still not too small
		} else if (getWidth() > 10 && getHeight() > 10) {
			newW = getWidth() - step;
			newH = Math.round(newW / ratio);
			stepW = Math.round(step / 2);
			stepH = Math.round((getHeight() - newH) / 2);
			setPosition(currentPosition.x + stepW, currentPosition.y + stepH);
			setWidth(newW);
			setHeight(newH);
		}
	//Reseting the shape's features after transformation
		setShape(new Rectangle2D.Float(getPosition().x, getPosition().y, 
				getWidth(), getHeight()));
	}
	
}
