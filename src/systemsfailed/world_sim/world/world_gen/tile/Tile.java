package systemsfailed.world_sim.world.world_gen.tile;

public class Tile 
{
	
	public static Tile[] tileTypes = new Tile[255];
	
	
	public final byte id;
	public int color;
	
	public Tile(int id)
	{
		this.id = (byte) id;
		if(tileTypes[id] != null)
			throw new RuntimeException("Duplicate tile IDs found");
	}
	
	public boolean canCross()
	{
		return true;
	}
	
	
	
	
}
