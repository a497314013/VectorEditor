package Interface;

import java.util.ArrayList;

import VectorEditor.VectorShape;

public interface ShapeListObserver {
	public void update(ArrayList<VectorShape> shapes);
}
