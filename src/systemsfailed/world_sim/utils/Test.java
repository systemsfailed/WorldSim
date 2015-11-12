package systemsfailed.world_sim.utils;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import systemsfailed.world_sim.world.World;

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
	
		int height = 1024;
		int width = 1024;
		
		World world = new World("TestWorld", height, width);
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[][] heightmap = world.getHeightmap();
		int[][] heatmap = world.getHeatmap();
		int largest = world.getMaxHeight();
		int maxtemp = world.getMaxTemp();
		
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				int rgb = 0;
				
				if(heightmap[y][x] < largest * .65)
					rgb = 0x0000EE;
				else if(heightmap[y][x] < largest * .85)
				{
					if(heatmap[y][x] < maxtemp * .4)
						rgb = 0xFCFCFC;
					else if(heatmap[y][x] < maxtemp * .50)
						rgb = 0x2264800;
					else if(heatmap[y][x] < maxtemp * .75)
						rgb = 0x008B00;
					else
						rgb = 0xF4A460;
				}
				else if(heightmap[y][x] <= 255)
					rgb = 0x808A87;
				
				
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