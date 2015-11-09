package systemsfailed.world_sim.utils;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import systemsfailed.world_sim.world.world_gen.SimplexNoiseGenerator;

public class Test
{
	public static void main(String[] args)
	{
		final int width = 640;
		final int height = 640;
		final double size = 16;
		final int octaves = 8; //Smoothe texture or blocky
		Random rand = new Random();
		
		SimplexNoiseGenerator noise1 = new SimplexNoiseGenerator(rand.nextLong());
		
		double[][] n1 = new double[height][width];

		
		
			for(int y = 0; y < height; y++)
			{	
				for(int x = 0; x < width; x++)
				{	
					double total = 0; 
					double maxVal = 0;
					double freq = 1;
					double amp = 1;
					double con = 2; //Bigger/smaller grouping of colors
					for(int i = 0; i < octaves; i++)
					{
						total += noise1.eval(x / freq, y / freq, 0.0) * amp;
						maxVal += amp;
						amp *= con;
						freq *=2;
					}
					n1[y][x] = total/maxVal;
				}
			}
			
					
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				int rgb = 0x010101 * (int)((n1[y][x] + 1) * 127.5);
				image.setRGB(x, y, rgb);
			}
		}
			
		JFrame frame = new JFrame();
		ImageIcon icon = new ImageIcon(image);
		JLabel label = new JLabel();
		label.setIcon(icon);
		frame.setLayout(new FlowLayout());
		frame.setSize(width, height);
		frame.add(label);
		frame.setVisible(true);
		
			
		}
}