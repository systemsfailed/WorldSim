package systemsfailed.world_sim.world.world_gen;

import java.util.Random;
public class WorldGenerator 
{
	public static byte[] generateMap()
	{
		Random rand = new Random();
		SimplexNoiseGenerator noiseGen = new SimplexNoiseGenerator(rand.nextLong());
	}
}
