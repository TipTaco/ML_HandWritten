package tiptaco.image;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Display {

	JFrame frame;
	
    public Display(String title) throws IOException
    {
        frame=new JFrame(title);
        frame.setLayout(new FlowLayout());
        frame.setSize(500, 500);
               
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void addImage(int pixels[][]) {
    	
    	BufferedImage img = new BufferedImage(pixels.length, pixels[0].length, BufferedImage.TYPE_INT_RGB);//BufferedImage.TYPE_BYTE_GRAY);
        
        for (int yy = 0; yy < pixels[0].length; yy++) {
        	for (int xx = 0; xx < pixels.length; xx++) {
        		img.setRGB(xx, yy, pixels[xx][yy]);
        	}
        }
        
        ImageIcon icon=new ImageIcon( img.getScaledInstance(2*28, 2*28, img.SCALE_FAST));
        
        JLabel lbl=new JLabel();
        
        lbl.setSize(2*28,2*28);
        lbl.setIcon(icon);
        frame.add(lbl);
    }
	
}
