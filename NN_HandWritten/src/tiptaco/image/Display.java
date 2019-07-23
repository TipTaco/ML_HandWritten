package tiptaco.image;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Display {

    public Display(int pixels[][]) throws IOException
    {
        BufferedImage img = new BufferedImage(pixels.length, pixels[0].length, BufferedImage.TYPE_BYTE_GRAY);
       
        for (int yy = 0; yy < pixels[0].length; yy++) {
        	for (int xx = 0; xx < pixels.length; xx++) {
        		img.setRGB(xx, yy, pixels[xx][yy]);
        	}
        }
        
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(200,300);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
}
