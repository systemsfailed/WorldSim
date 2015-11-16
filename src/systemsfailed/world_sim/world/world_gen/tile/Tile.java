package systemsfailed.world_sim.world.world_gen.tile;

public class Tile 
{
	public static Tile[] tileTypes = new Tile[255];
	
	
	
	
	public final byte id;
	public int color;
	public byte resource;
	
	public Tile(int id, int res)
	{
		this.id = (byte) id;
		this.resource = (byte) res;
		if(tileTypes[id] != null)
			throw new RuntimeException("Duplicate tile IDs found");
	}
	
	public int getResource()
	{
		return resource;
	}
	
	
	
	
}
