package Interface;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicSpinnerUI;

public class DarkSpinner extends BasicSpinnerUI {
	@Override
  protected Component createPreviousButton() {
		Component downButton = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/spinner_down.png")));
  	downButton.setPreferredSize(new Dimension(8, 12));
  	downButton.setFocusable(false);
  	installPreviousButtonListeners(downButton);
    return downButton;
  }

  @Override
  protected Component createNextButton() {
  	Component upButton = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/spinner_up.png")));
  	upButton.setPreferredSize(new Dimension(8, 12));
  	upButton.setFocusable(false);
  	installNextButtonListeners(upButton);
    return upButton;
  }
  
}
