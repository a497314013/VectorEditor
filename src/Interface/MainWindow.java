package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * v1.1.1
 * Main window class to hold the workspaces and the canvas
 * @author Þilvinas
 *
 */
public class MainWindow extends JFrame {
	
	public MainWindow() {
		setTitle("File name");
		setSize(900, 600);
		//setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Instantiate a workspace
		Workspace workspace = new Workspace();
		
		workspace.addArtboard(1, 800, 500);
		JScrollPane ScrollWindow = new JScrollPane(workspace);
		
		LayersPannel layersPannel = new LayersPannel();
		
		this.add(ScrollWindow, BorderLayout.CENTER);
		this.add(layersPannel, BorderLayout.EAST);
		
		setVisible(true);
	}
}
	
