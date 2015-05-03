package Interface;
import java.util.ArrayList;

import VectorEditor.VectorShape;

public interface CanvasObserver {
	public void updateProperties(VectorShape v);
	public void updateShapeList(ArrayList<VectorShape> shapes);
	public void updateHistoryList(Config.Actions act);
}
