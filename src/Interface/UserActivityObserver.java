package Interface;

import java.util.ArrayList;

import VectorEditor.VectorShape;

public interface UserActivityObserver {
	public void update(ArrayList<Integer> data);
	public void updateShapeListItem(int id, boolean visible, boolean locked, boolean selected);
	public void updateShapeList(ArrayList<VectorShape> data);
	public void rewindHistory(int step);
}
