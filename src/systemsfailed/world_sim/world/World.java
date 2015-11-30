package systemsfailed.world_sim.world;

import systemsfailed.world_sim.utils.SimplexNoiseGenerator;

public class World 
{
	short[] heightmap; //Stores the height value for each (x,y) coordinate
	byte[] heatmap,resmap; //Stores temperature data for each (x,y) coordinate
	int[] datamap; //Stores additional data for each (x,y) coordinate such as resources and city
	short maxheight; 
	byte maxtemp;
	private long seed; //The seed used to generate the world
	private int height,width;
	
	/**
	 * Generates a new world defined by a seed value
	 * Worlds generated in this way will be recreateable by repeating the seed
	 * value in another generation attempt so long as the size is equal
	 * @param seed
	 * 	The seed value to feed the simplex noise generator that forms the world
	 * @param height
	 * 	The length of the y axis of the world's array
	 * @param width
	 * 	The length of the x axis of the world's array
	 */
	public World(String seed, final int height, final int width)
	{
		this.height = height;
		this.width = width;
		this.seed = seed.hashCode();
		
		generate();
	}
	
	/**
	 * Generates a new world without a user defined seed value
	 * the generator will take the current system time in milliseconds
	 * for use as a seed, worlds generated this way won't be recreated 
	 * unless the user retrieves their seed value
	 * 
	 * @param seed
	 * 	The seed value to feed the simplex noise generator that forms the world
	 * @param height
	 * 	The length of the y axis of the world's array
	 * @param width
	 * 	The length of the x axis of the world's array
	 */
	public World(final int height, final int width)
	{
		this.height = height;
		this.width = width;
		this.seed = System.currentTimeMillis();
		
		generate();
	}
	
	/**
	 * Helper method for constructor
	 */
	private void generate()
	{
		final SimplexNoiseGenerator generator = new SimplexNoiseGenerator(seed);
		
		Thread t1 = new Thread()
		{	
			@Override
			public void run()
			{
				System.out.println("Starting 1");
				generateHeightmap(height, width, generator);
				System.out.println("Finishing 1");
			}
		};
		Thread t2 = new Thread()
		{	
			@Override
			public void run()
			{
				System.out.println("Starting 2");
				generateHeatmap(height, width, generator);
				System.out.println("Finishing 2");
			}
		};
		
		t1.start();
		t2.start();
		
		try
		{
		t1.join();
		t2.join();
		}catch(Exception e)
		{e.printStackTrace();}
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
	
	/**
	 * Generates the heatmap that determines the biome placement of the world
	 * Runs on the same simplex noise generator and seed
	 * @param height
	 * 	How large the Y axis of the array will be
	 * @param width
	 * 	How large the X axis of the array will be
	 * @param generator
	 * A Simplex noise generator to generate the base pattern of the heatmap
	 */
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
	
	private void generateResmap(int height, int width, SimplexNoiseGenerator gen)
	{
		resmap = new byte[height * width];
	}
	
	/**
	 * @return
	 *	The heightmap array of the world
	 */
	public short[] getHeightmap()
	{
		return heightmap;
	}
	
	/**
	 * @return
	 * 	The heatmap array of the world
	 */
	public byte[] getHeatmap()
	{
		return heatmap;
	}
	
	/**
	 * @return
	 * 	The datamap array of the world
	 */
	public int[] getDatamap()
	{
		return datamap;
	}
	/**
	 * @return
	 * 	The largest height value within the world array
	 */
	public int getMaxHeight()
	{
		return maxheight;
	}
	/**
	 * @return
	 * 	The largest heat value within the world array
	 */
	public int getMaxTemp()
	{
		return maxtemp;
	}
	/**
	 * @return
	 * 	The Y axis length of the world's array
	 */
	public int getHeight()
	{
		return height;
	}
	/**
	 * @return
	 * 	The X axis length of the world's array
	 */
	public int getWidth()
	{
		return width;
	}
	
}
