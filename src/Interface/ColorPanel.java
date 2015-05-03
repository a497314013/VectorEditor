package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ColorPanel {
	
	private JPanel container = new JPanel();
	private JColorChooser colorChooser = new JColorChooser();
	private JPanel currentColor = new JPanel();
	private JPanel colorWheelContainer = new JPanel();
	private JSpinner red = new JSpinner(new SpinnerNumberModel(255, 0, 255, 1));
	private JSpinner green = new JSpinner(new SpinnerNumberModel(255, 0, 255, 1));
	private JSpinner blue = new JSpinner(new SpinnerNumberModel(255, 0, 255, 1));
	private JLabel hexColor = new JLabel("FFFFFF");
	
	
	public ColorPanel() {
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(206, 168));
		container.setBackground(new Color(73, 73, 73));
		container.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(112, 112, 112)));
		colorChooser.setBackground(new Color(73, 73, 73));
		JLabel colorWheel = new JLabel(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/color_wheel.png")));
		colorWheel.setPreferredSize(new Dimension(130, 120));
		colorWheel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		colorWheelContainer.setPreferredSize(new Dimension(160, 168));
		colorWheelContainer.setMaximumSize(colorWheelContainer.getPreferredSize());
		colorWheelContainer.setBackground(Config.PANEL_COLOR);
		colorWheelContainer.add(colorWheel);
		
		currentColor.setPreferredSize(new Dimension(125, 25));
		currentColor.setBackground(new Color(0, 75, 180));
		currentColor.setBorder(BorderFactory.createMatteBorder(0, 22, 0, 0, Config.PANEL_COLOR));
		
		colorWheelContainer.add(currentColor);
		
		container.add(colorWheelContainer);
		
		Box colorSwatchContainer = Box.createVerticalBox();
		
		colorSwatchContainer.add(Box.createVerticalStrut(15));
		
		JPanel swatchRed = new JPanel();
		swatchRed.setPreferredSize(new Dimension(130, 25));
		swatchRed.setBackground(Config.PANEL_COLOR);
		swatchRed.setMaximumSize(swatchRed.getPreferredSize());
		JLabel swatchRedLabel = new JLabel("R: ");
		swatchRedLabel.setForeground(Config.LABEL_COLOR);
		swatchRedLabel.setFont(Config.FONT);
		
		red = new JSpinner(new SpinnerNumberModel(255, 0, 255, 1));
		red.getEditor().getComponent(0).setBackground(Config.INPUT_AREA_COLOR);
		red.setPreferredSize(new Dimension(55, 20));
		red.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Config.SPINNER_TEXT_COLOR));
		red.getEditor().setFont(Config.SMALL_FONT);
		red.getEditor().setForeground(Config.SPINNER_TEXT_COLOR);
		red.setUI(new DarkSpinner());
		
		swatchRed.add(swatchRedLabel);
		swatchRed.add(red);
		colorSwatchContainer.add(swatchRed);
		
		colorSwatchContainer.add(Box.createVerticalStrut(10));
		
		JPanel swatchGreen = new JPanel();
		swatchGreen.setPreferredSize(new Dimension(120, 25));
		swatchGreen.setBackground(Config.PANEL_COLOR);
		swatchGreen.setMaximumSize(swatchGreen.getPreferredSize());
		JLabel swatchGreenLabel = new JLabel("G: ");
		swatchGreenLabel.setForeground(Config.LABEL_COLOR);
		swatchGreenLabel.setFont(Config.FONT);
		
		green = new JSpinner(new SpinnerNumberModel(255, 0, 255, 1));
		green.getEditor().getComponent(0).setBackground(Config.INPUT_AREA_COLOR);
		green.setPreferredSize(new Dimension(55, 20));
		green.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Config.SPINNER_TEXT_COLOR));
		green.getEditor().setFont(Config.SMALL_FONT);
		green.getEditor().setForeground(Config.SPINNER_TEXT_COLOR);
		green.setUI(new DarkSpinner());
		
		swatchGreen.add(swatchGreenLabel);
		swatchGreen.add(green);
		colorSwatchContainer.add(swatchGreen);
		
		colorSwatchContainer.add(Box.createVerticalStrut(10));
		
		JPanel swatchBlue = new JPanel();
		swatchBlue.setPreferredSize(new Dimension(130, 25));
		swatchBlue.setBackground(Config.PANEL_COLOR);
		swatchBlue.setMaximumSize(swatchGreen.getPreferredSize());
		JLabel swatchBlueLabel = new JLabel("B: ");
		swatchBlueLabel.setForeground(Config.LABEL_COLOR);
		swatchBlueLabel.setFont(Config.FONT);
		
		blue = new JSpinner(new SpinnerNumberModel(255, 0, 255, 1));
		blue.getEditor().getComponent(0).setBackground(Config.INPUT_AREA_COLOR);
		blue.setPreferredSize(new Dimension(55, 20));
		blue.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Config.SPINNER_TEXT_COLOR));
		blue.getEditor().setFont(Config.SMALL_FONT);
		blue.getEditor().setForeground(Config.SPINNER_TEXT_COLOR);
		blue.setUI(new DarkSpinner());
		
		swatchBlue.add(swatchBlueLabel);
		swatchBlue.add(blue);
		colorSwatchContainer.add(swatchBlue);
		
		colorSwatchContainer.add(Box.createVerticalStrut(20));
		
		JPanel hexColorContainer = new JPanel();
		hexColorContainer.setPreferredSize(new Dimension(130, 25));
		hexColorContainer.setBackground(Config.PANEL_COLOR);
		swatchBlue.setMaximumSize(swatchGreen.getPreferredSize());
		JLabel hexColorLabel = new JLabel("#  ");
		hexColorLabel.setForeground(Config.LABEL_COLOR);
		hexColorLabel.setFont(Config.FONT);
		
		hexColor.setForeground(Config.LABEL_COLOR);
		hexColor.setFont(Config.FONT);
		
		hexColorContainer.add(hexColorLabel);
		hexColorContainer.add(hexColor);
		colorSwatchContainer.add(hexColorContainer);
		
		container.add(colorSwatchContainer, BorderLayout.EAST);
    /*
    ColorSelectionModel model = colorChooser.getSelectionModel();
    colorChooser.setBackground(Config.PANEL_COLOR);
    colorChooser.setPreviewPanel(new JPanel());
		
		JFrame c = new JFrame();*/
		
		//c.add(colorChooser);
		//c.setVisible(true);
	}
	
	public JPanel renderPanel() {
		return container;
	}
}
