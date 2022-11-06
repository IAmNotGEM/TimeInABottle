package lumien.randomthings.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemBase extends Item
{
	public ItemBase(String name)
	{
		registerItem(name, this);
	}

	public static void registerItem(String name, Item item)
	{
		item.setRegistryName(name);
		item.setCreativeTab(CreativeTabs.MISC);
		item.setUnlocalizedName(name);

		ForgeRegistries.ITEMS.register(item);
	}
}
