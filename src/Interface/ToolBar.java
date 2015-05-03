package Interface;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolBar extends JPanel {
	
	private final Color PANEL_COLOR = new Color(73, 73, 73);
	
	public ToolBar() {
		this.setPreferredSize(new Dimension(50, 500));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setBackground(PANEL_COLOR);
		this.setLayout(new BorderLayout());
		
		//Set up tool buttons
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(Box.createVerticalStrut(20));
		
		JButton pickTool = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/pick_tool.png")));
		pickTool.setPreferredSize(new Dimension(50, 30));
		pickTool.setFocusable(false);
		box.add(pickTool);
		
		box.add(Box.createVerticalStrut(5));
		
		JButton selectTool = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/marquee_tool.png")));
		selectTool.setPreferredSize(new Dimension(50, 35));
		selectTool.setFocusable(false);
		box.add(selectTool);
		
		box.add(Box.createVerticalStrut(5));
		
		JButton transformTool = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/transform_tool.png")));
		transformTool.setPreferredSize(new Dimension(50, 35));
		transformTool.setFocusable(false);
		box.add(transformTool);
		
		box.add(Box.createVerticalStrut(5));
		
		JButton rectangleTool = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/rectangle_tool.png")));
		rectangleTool.setPreferredSize(new Dimension(50, 35));
		rectangleTool.setFocusable(false);
		box.add(rectangleTool);
		
		box.add(Box.createVerticalStrut(5));
		
		JButton circleTool = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/circle_tool.png")));
		circleTool.setPreferredSize(new Dimension(50, 35));
		circleTool.setFocusable(false);
		box.add(circleTool);
		
		box.add(Box.createVerticalStrut(5));
		
		JButton customTool = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/custom_tool.png")));
		customTool.setPreferredSize(new Dimension(50, 35));
		customTool.setFocusable(false);
		box.add(customTool);
		
		box.add(Box.createVerticalStrut(5));
		
		JButton lineTool = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/line_tool.png")));
		lineTool.setPreferredSize(new Dimension(50, 35));
		lineTool.setFocusable(false);
		box.add(lineTool);
		
		box.add(Box.createVerticalStrut(5));
		
		JButton arcTool = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/arc_tool.png")));
		arcTool.setPreferredSize(new Dimension(50, 35));
		arcTool.setFocusable(false);
		box.add(arcTool);
		
		box.add(Box.createVerticalStrut(5));
		
		JButton eyeDropperTool = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/eyedropper_tool.png")));
		eyeDropperTool.setPreferredSize(new Dimension(50, 35));
		eyeDropperTool.setFocusable(false);
		box.add(eyeDropperTool);
		
		// Set up color tool
		JPanel colorTool = new JPanel();
		colorTool.setPreferredSize(new Dimension(50, 80));
		colorTool.setBackground(PANEL_COLOR);
		box.add(colorTool);
		
		box.add(Box.createVerticalStrut(20));
		
		this.add(box);
	}
}
