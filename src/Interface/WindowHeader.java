package Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WindowHeader {
	
	private JPanel container = new JPanel(new BorderLayout());
	private JLabel title;
	private JButton cyg;
	private JButton save;
	private JButton open;
	public JButton minimize;
	private JButton cloud;
	public JButton close;
	
	private String name;
	
	public WindowHeader() {
		container.setPreferredSize(new Dimension(100, 35));
		container.setBackground(Config.PANEL_COLOR);
		
		Box buttonBoxLeft = Box.createHorizontalBox();
		Box buttonBoxRight = Box.createHorizontalBox();
		JPanel buttonBoxCenter = new JPanel(new GridBagLayout());
		buttonBoxCenter.setBackground(Config.PANEL_COLOR);
		
		cyg = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/logo.png")));
		cyg.setPreferredSize(new Dimension(57,31));
		cyg.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 15, Config.PANEL_COLOR));
		cyg.setFocusable(false);
		buttonBoxLeft.add(cyg);
		
		save = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/save.png")));
		save.setPreferredSize(new Dimension(45,30));
		save.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, Config.PANEL_COLOR));
		save.setFocusable(false);
		buttonBoxLeft.add(save);
		
		open = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/open.png")));
		open.setPreferredSize(new Dimension(45,30));
		open.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, Config.PANEL_COLOR));
		open.setFocusable(false);
		buttonBoxLeft.add(open);
		
		cloud = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/cloud.png")));
		cloud.setPreferredSize(new Dimension(45,30));
		cloud.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, Config.PANEL_COLOR));
		cloud.setFocusable(false);
		buttonBoxLeft.add(cloud);
		
		title = new JLabel();
		title.setForeground(Config.FONT_COLOR);
		title.setFont(Config.FONT);
		buttonBoxCenter.add(title, new GridBagConstraints());
		
		minimize = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/resize_windows.png")));
		minimize.setPreferredSize(new Dimension(35,30));
		minimize.setFocusable(false);
		buttonBoxRight.add(minimize);
		
		close = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/close.png")));
		close.setPreferredSize(new Dimension(35,30));
		close.setFocusable(false);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				System.exit(0);
			}
		});
		buttonBoxRight.add(close);
		
		container.add(Box.createVerticalStrut(5), BorderLayout.NORTH);
		container.add(buttonBoxLeft, BorderLayout.WEST);
		container.add(buttonBoxCenter, BorderLayout.CENTER);
		container.add(buttonBoxRight, BorderLayout.EAST);
		
	}
	
	public JPanel renderPanel() {
		return this.container;
	}
	
	public void setName(String name) {
		this.name = name;
		title.setText(name);
	}
	public String getName() {
		return this.name;
	}
}
