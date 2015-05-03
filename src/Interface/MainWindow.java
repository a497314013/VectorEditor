package Interface;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
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
		//setLayout(null);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		
		WindowHeader windowHeader = new WindowHeader();
		windowHeader.setName("Untitled-1.cyg");
		add(windowHeader.renderPanel(), BorderLayout.NORTH);
		
		windowHeader.minimize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				setState(Frame.ICONIFIED);
			}
     });
		
		//Instantiate a workspace
		Workspace workspace = new Workspace();
		
		workspace.addArtboard(1, 929, 549);
		JScrollPane scrollWindow = new JScrollPane(workspace);
		scrollWindow.setBorder(BorderFactory.createEmptyBorder()); 
		
		this.add(scrollWindow);
		this.setVisible(true);
	}

	private ActionListener ActionEvent() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
	
