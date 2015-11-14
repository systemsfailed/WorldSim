package systemsfailed.world_sim.world;

import systemsfailed.world_sim.utils.SimplexNoiseGenerator;

public class World 
{
	short[] heightmap;
	byte[] heatmap;
	short maxheight; 
	byte maxtemp;
	private long seed;
	
	public World(String seed, int height, int width)
	{
		this.seed = seed.hashCode();
		SimplexNoiseGenerator generator = new SimplexNoiseGenerator(seed.hashCode());
		generateHeightmap(height, width, generator);
		generateHeatmap(height, width, generator);
	}
	
	public World(final int height, final int width)
	{
		this.seed = System.currentTimeMillis();
		final SimplexNoiseGenerator generator = new SimplexNoiseGenerator(seed);
		SimplexNoiseGenerator generator2 = new SimplexNoiseGenerator(seed);
		
		Thread t1 = new Thread()
		{	
			@Override
			public void run()
			{
				generateHeightmap(height, width, generator);
				this.interrupt();
			}
		};
		Thread t2 = new Thread()
		{	
			@Override
			public void run()
			{
				generateHeatmap(height, width, generator);
				this.interrupt();
			}
		};
		
		t1.start();
		t2.start();
		
		while(!t1.isInterrupted() && !t2.isInterrupted()){};
		
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
		short max = 50;
		
		heightmap = new short[height * width];
		
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{	
				heightmap[x + y * width] = (short)gen.sumOctave(16, x, y, 1, .5, .007, 255);
				if(heightmap[x + y * width] > max)
					max = heightmap[x + y * width];
			}	
		}
		this.maxheight = max;
	}
	
	private void generateHeatmap(int height, int width, SimplexNoiseGenerator generator)
	{
		heatmap = new byte [height * width];
		byte max = 50;  
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				{						
					heatmap[x + y * width] = (byte)((generator.sumOctave(8, x, y, 0, .5, .007, 100)) * Math.pow(Math.E, -Math.abs(y - (height/2) ) / (height * .95) ) );
					if(heatmap[x + y * width] > max)
						max = heatmap[x + y * width];
				}
		this.maxtemp = max;
	}
	
	public short[] getHeightmap()
	{
		return heightmap;
	}
	
	public byte[] getHeatmap()
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
