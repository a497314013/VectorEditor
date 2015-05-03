package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class DarkScrollBar extends BasicScrollBarUI {
	
	@Override
  protected JButton createDecreaseButton(int orientation) {
			JButton upButton = new JButton(new ImageIcon(getClass().getClassLoader()
	        .getResource("resources/scroll_arrow_up.png")));
			upButton.setPreferredSize(new Dimension(12, 12));
			upButton.setFocusable(false);
      return upButton;
  }

  @Override    
  protected JButton createIncreaseButton(int orientation) {
  	JButton downButton = new JButton(new ImageIcon(getClass().getClassLoader()
        .getResource("resources/scroll_arrow_down.png")));
  	downButton.setPreferredSize(new Dimension(12, 12));
  	downButton.setFocusable(false);
    return downButton;
  }

  @Override
  protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
  	if (trackBounds.isEmpty() || !scrollbar.isEnabled()) {
      return;
	  }
  	
  	BufferedImage img = null;
  	try {
  	    img = ImageIO.read(new File(getClass().getResource("/resources/scroll_back.png").toURI()));
  	} catch (IOException e) {
  		e.printStackTrace();
  	} catch (URISyntaxException e) {
			e.printStackTrace();
		}
  	
	  g.translate(trackBounds.x, trackBounds.y);
	  g.drawRect(0, 0, trackBounds.width - 2, trackBounds.height - 1);
	  AffineTransform transform = AffineTransform.getScaleInstance((double) trackBounds.width
	          / img.getWidth(null), (double) trackBounds.height / img.getHeight(null));
	  g.setColor(Color.RED);
	  ((Graphics2D) g).drawImage(img, transform, null);
	  g.translate(-trackBounds.x, -trackBounds.y);
  }

  @Override
  protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
  	
  	if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
      return;
	  }
  	
  	BufferedImage img = null;
  	try {
  	    img = ImageIO.read(new File(getClass().getResource("/resources/scroll_handle.png").toURI()));
  	} catch (IOException e) {
  		e.printStackTrace();
  	} catch (URISyntaxException e) {
			e.printStackTrace();
		}
  	
	  g.translate(thumbBounds.x, thumbBounds.y);
	  g.drawRect(0, 0, thumbBounds.width - 2, thumbBounds.height - 1);
	  AffineTransform transform = AffineTransform.getScaleInstance((double) thumbBounds.width
	          / img.getWidth(null), (double) thumbBounds.height / img.getHeight(null));
	  ((Graphics2D) g).drawImage(img, transform, null);
	  g.translate(-thumbBounds.x, -thumbBounds.y);
  }
}
