package systemsfailed.world_sim.world;

import systemsfailed.world_sim.utils.SimplexNoiseGenerator;

public class World 
{
	int[][] heightmap,heatmap;
	int max;
	
	public World(String seed, int height, int width)
	{
		SimplexNoiseGenerator generator = new SimplexNoiseGenerator(seed.hashCode());
		generateHeightmap(height, width, generator);
		
		
	}
	
	
	/**
	 * Generates the heightmap that the world map will be based on
	 * @param height
	 * The Y axis height that the world will be based on
	 * @param width
	 * The X axis width that the world will be based on
	 * @param gen
	 * A simplex noise generator object used to perform the procedural generation
	 */
	private void generateHeightmap(int height, int width, SimplexNoiseGenerator gen)
	{
		double[][] map = new double[height][width];
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{	
				heightmap[y][x] = (int)gen.sumOctave(16, x, y, .5, .007, 255);
			}	
		}
	}
	
	private void generateHeatmap(int height, int width, SimplexNoiseGenerator generator)
	{
		double val;
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				{
					val = generator.eval(x, y, 1);
					if(y <= height * .25)
						val *= .3;
					else if(y <= height * .4)
						val *= .75;
					else if(y <= height * .6)
						val *= 1.25;
					else if(y <= height * .75)
						val *= .75;
					else if(y <= height)
						val *= .3;
					
					heatmap[y][x] = (int) (val + 1) / 2 * 100;
				}
		
	}
	
	
}
