package lumien.randomthings.item;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModItems
{
	public static ItemTimeInABottle timeInABottle;

	public static void load(FMLPreInitializationEvent event)
	{
		timeInABottle = new ItemTimeInABottle();
	}
}
