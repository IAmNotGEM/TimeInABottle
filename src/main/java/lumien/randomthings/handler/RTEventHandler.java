package lumien.randomthings.handler;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class RTEventHandler
{
	public static int clientAnimationCounter;

	@SubscribeEvent
	public void tick(TickEvent tickEvent)
	{

		if ((tickEvent.type == TickEvent.Type.CLIENT))
		{
			clientAnimationCounter++;
		}
	}
}
