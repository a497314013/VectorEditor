package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class LayersPannel extends JPanel {
	public LayersPannel() {
		
		setPreferredSize(new Dimension(100, 200));
		JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(10, 101, 742, 276);
    //scrollPane.add(this);
		this.add(scrollPane, BorderLayout.EAST);
    
	}
}
