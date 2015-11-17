package systemsfailed.world_sim.world.world_gen.tile;

public class Ocean extends Tile 
{
	public Ocean(int id)
	{
		super(id);
		color = 0x0000EE;
	}
	
	public boolean canPass()
	{
		return false;
	}
	
}
