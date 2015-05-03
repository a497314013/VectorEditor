package Interface;

import java.awt.Color;
import java.awt.Font;

public final class Config {
	public static final Color BACKGROUND_COLOR = new Color(82, 82, 82);
	public static final Color PANEL_COLOR = new Color(73, 73, 73);
	public static final Color INPUT_AREA_COLOR = new Color(153, 153, 153);
	public static final Color FONT_COLOR = new Color(206, 206, 206);
	public static final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 12);
	public static final Font FONT = new Font("Arial", Font.PLAIN, 13);
	public static final Color SPINNER_TEXT_COLOR = new Color(48, 48, 48);
	public static final Color LABEL_COLOR = new Color(153, 153, 153);
	
	public static enum Tools {
		PICK,
		LINE,
		RECTANGLE,
		ELIPSE,
		POLYGON,
		EYEDROPPER
	}
	
	public static enum Actions {
		SHAPE_MOVED,
		SHAPE_RESIZED,
		SHAPE_OPACITY_CHANGE,
		SHAPE_COLOR_CHANGE,
		RECTANGLE_TOOL_USED,
		CIRCLE_TOOL_USED,
		CUSTOM_SHAPE_TOOL_USED,
		LINE_TOOL_USED,
		ARC_TOOL_USED,
		SHAPE_STROKE_CHANGE,
		SHAPE_REMOVED
	}
	
	public static int maxHistory = 5;
}

