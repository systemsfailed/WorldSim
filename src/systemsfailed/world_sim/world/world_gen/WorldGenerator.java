package systemsfailed.world_sim.world.world_gen;

import java.util.Random;

import systemsfailed.world_sim.utils.SimplexNoiseGenerator;
import systemsfailed.world_sim.world.World;
public class WorldGenerator 
{
	private SimplexNoiseGenerator generator;
	private long seed;
	
	public WorldGenerator()
	{
		seed = System.currentTimeMillis();
		generator = new SimplexNoiseGenerator(seed);
	}
	
	public WorldGenerator(String seed)
	{
		this.seed = seed.hashCode();
		generator = new SimplexNoiseGenerator(seed.hashCode());
	}
	
	public World generateWorld()
	{
		
		return new World();
	}
	
	
	
	
}
