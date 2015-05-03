package Interface;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import Interface.Config.Actions;
import VectorEditor.VectorShape;

public class StatusBar implements CanvasObserver {
	
	//Local constants
	private final Border LABEL_PADDING = BorderFactory.createEmptyBorder(0,10,0,10);
	private final Border PANEL_PADDING = BorderFactory.createEmptyBorder(6,0,0,0);
	private final int SPINNER_GAP = 15;
	private final int COTAINER_WIDTH = 900;
	private final int COTAINER_HEIGHT = 900;
	
	@Override
	public void updateProperties(VectorShape v) {
		// TODO Auto-generated method stub
		
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
