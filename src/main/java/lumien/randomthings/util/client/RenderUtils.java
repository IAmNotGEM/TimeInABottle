package lumien.randomthings.util.client;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;

public class RenderUtils
{
	public static void enableDefaultBlending()
	{
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
}
