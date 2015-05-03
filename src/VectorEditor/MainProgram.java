package VectorEditor;

import javax.swing.UIManager;

import Core.MainConrtoller;

public class MainProgram {

	public static void main(String[] args) {
		
		 try {
       // Set cross-platform Java L&F (also called "Metal")
		   UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		   UIManager.put("ScrollBarUI", "Interface.DarkScrollBar"); 
		   UIManager.put("SpinnerUI", "Interface.DarkSpinner"); 
		} 
		catch (Exception e) {
		  // handle exception
		}
		
		MainConrtoller mc = new MainConrtoller();

	}

}
