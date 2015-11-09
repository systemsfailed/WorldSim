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
	
	public class container
	{
		public int num;
		
		public container(int i)
		{
			num = i;
		}
	}
	
	public static void main(String[] args)
	{
		/*
		int height = 1024;
		int width = 1024;
		
		SimplexNoiseGenerator gen = new SimplexNoiseGenerator(System.currentTimeMillis());
		double[][] n1 =  new double[height][width];
		
		double largest = 0;
		double smallest = 0;
		
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				n1[y][x] = sumOctave(32, x, y, .5, .007, -1, 1, gen);
				
				if(n1[x][y] > largest)
					largest = n1[x][y];
				if(n1[x][y] < smallest)
					smallest = n1[x][y];
				
			}
		}
		
		System.out.println("Largest: " + largest + " Smallest: " + smallest);
		
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				int rgb = 0;
				
				if(n1[y][x] < .55)
					rgb = 0x0000EE;
				else if(n1[y][x] <= 1)
					rgb = 0x008B00;
			
				
				
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
		
		*/
		
		
		
	}
	
	static double sumOctave(int octaves, int x, int y,  double persistence, double scale, int low, int high, SimplexNoiseGenerator gen)
	{
		double maxAmp = 0;
		double amp = 1;
		double freq = scale;
		double noise = 0;
		
		for(int i = 0; i < octaves; i++)
		{
			noise += gen.eval(x * freq, y * freq, 1) * amp;
			maxAmp += amp;
			amp *= persistence;
			freq *= 2;
		}
		
		noise /= maxAmp;
		noise = (noise - low) / (high - low);
	
		return noise;
	}
	
}