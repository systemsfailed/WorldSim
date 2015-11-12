package systemsfailed.world_sim.world;

import systemsfailed.world_sim.utils.SimplexNoiseGenerator;

public class World 
{
	int[][] heightmap,heatmap;
	int maxheight, maxtemp;
	private long seed;
	
	public World(String seed, int height, int width)
	{
		this.seed = seed.hashCode();
		SimplexNoiseGenerator generator = new SimplexNoiseGenerator(seed.hashCode());
		generateHeightmap(height, width, generator);
		generateHeatmap(height, width, generator);
	}
	
	public World(int height, int width)
	{
		this.seed = System.currentTimeMillis();
		SimplexNoiseGenerator generator = new SimplexNoiseGenerator(seed);
		generateHeightmap(height, width, generator);
		generateHeatmap(height, width, generator);
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
		int max = 50;
		
		heightmap = new int[height][width];
		heatmap = new int [height][width];
		
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{	
				heightmap[y][x] = (int)gen.sumOctave(16, x, y, 1, .5, .007, 255);
				if(heightmap[y][x] > max)
					max = heightmap[y][x];
			}	
		}
		this.maxheight = max;
	}
	
	private void generateHeatmap(int height, int width, SimplexNoiseGenerator generator)
	{
		int max = 50;  
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				{						
					heatmap[y][x] = (int)((generator.sumOctave(16, x, y, 0, .5, .007, 100)) * Math.pow(Math.E, -Math.abs(y - (height/2) ) / (height * .95) ) );
					if(heatmap[y][x] > max)
						max = heatmap[y][x];
				}
		this.maxtemp = max;
	}
	
	public int[][] getHeightmap()
	{
		return heightmap;
	}
	
	public int[][] getHeatmap()
	{
		return heatmap;
	}

	public int getMaxHeight()
	{
		return maxheight;
	}
	
	public int getMaxTemp()
	{
		return maxtemp;
	}
	
}
