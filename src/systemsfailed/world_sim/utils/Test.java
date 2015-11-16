package systemsfailed.world_sim.utils;


import java.awt.Color;
import java.awt.Graphics2D;

import systemsfailed.world_sim.world.World;

public class Test
{
	public static int TILE_WIDTH = 60;
	public static int TILE_HEIGHT = 60;
	public static int SCREEN_WIDTH = 600;
	public static int SCREEN_HEIGHT = 600;
	
	public static World world;
	
	public static void main(String[] args)
	{
		world = new World(100,100);
		
		
	}
	
	public void draw(Graphics2D g)
	{
		for(int y = 0; y < 10; y++)
			for(int x = 0; x < 10; x++)
			{
				Color c = new Color();
			}
	}
}