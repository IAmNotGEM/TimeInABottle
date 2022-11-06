package lumien.randomthings.config;

import lumien.randomthings.lib.ConfigOption;

public class Numbers
{
	@ConfigOption(category = "Numbers", name = "TimeInABottlePerSecond", comment = "How many ticks have to pass for a Time in a Bottle to gain 1 second (20 = 1 Second)")
	public static int TIME_IN_A_BOTTLE_SECOND = 20;
}
