package lumien.randomthings.client.models;

import lumien.randomthings.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemModels
{
	public static void register() {

		registerItem(ModItems.timeInABottle);
	}

	private static void registerItem(Item i)
	{
		ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
	}
}
