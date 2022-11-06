package lumien.randomthings.util.client;

import java.awt.Color;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import lumien.randomthings.client.render.magiccircles.ColorFunctions;
import lumien.randomthings.client.render.magiccircles.IColorFunction;
import lumien.randomthings.client.render.magiccircles.ITriangleFunction;
import lumien.randomthings.handler.RTEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.animation.Animation;

public class MKRRenderUtil
{
	// triCount <= 11
	public static void renderCircleDecTriInner(double r, ITriangleFunction triangleFunction, int triCount, Function<Integer, Integer> countFunction)
	{
		GlStateManager.glBegin(GL11.GL_TRIANGLE_FAN);
		GlStateManager.glVertex3f(0, 0, 0);

		int currentTriCount = 0;
		for (int c = 0; c < 10; c++)
		{
			int countForC = countFunction.apply(c);

			double winkelPart = 36 / countForC;

			double winkel = Math.PI * 2 / 360 * c * 36;
			double winkel2 = Math.PI * 2 / 360 * (c + 1) * 36;

			double pX = Math.sin(winkel) * (r);
			double pZ = Math.cos(winkel) * (r);

			double pX2 = Math.sin(winkel2) * (r);
			double pZ2 = Math.cos(winkel2) * (r);

			double dX = (pX2 - pX);
			double dZ = (pZ2 - pZ);

			double length = Math.sqrt(dX * dX + dZ * dZ);

			double partLength = length / countForC;

			double nX = dX / length;
			double nZ = dZ / length;

			GlStateManager.glVertex3f((float) (pX), 0, (float) (pZ));

			for (int p = 1; p <= countForC; p++)
			{
				if (currentTriCount < triCount)
				{
					ColorUtil.applyColor(triangleFunction.apply(currentTriCount));
					GlStateManager.glVertex3f((float) (pX + nX * partLength * p), 0, (float) (pZ + nZ * partLength * p));
					currentTriCount++;
				}
			}
		}

		GlStateManager.glEnd();
	}

	public static void renderCircleDecTriPart5Tri(double r1, double r2, ITriangleFunction triangleFunction, int triCount)
	{
		renderCircleDecTriPart5Tri(r1, r2, triangleFunction, triCount, 0, 10);
	}

	// Tricount <= 50
	public static void renderCircleDecTriPart5Tri(double r1, double r2, ITriangleFunction triangleFunction, int triCount, int cStart, int cEnd)
	{
		GlStateManager.glBegin(GL11.GL_TRIANGLE_STRIP);

		for (int c = cStart; c < cEnd; c++)
		{
			int winkelDeg1 = 36 * c;
			int winkelDeg2 = 36 * (c + 1);

			int triIndex = (c - cStart) * 5;

			double winkelRad1 = (Math.PI * 2) / 360D * winkelDeg1;
			double winkelRad2 = (Math.PI * 2) / 360D * winkelDeg2;

			double pX1 = Math.sin(winkelRad1) * (r1);
			double pZ1 = Math.cos(winkelRad1) * (r1);

			double pX2 = Math.sin(winkelRad2) * (r1);
			double pZ2 = Math.cos(winkelRad2) * (r1);

			double pX1H = Math.sin(winkelRad1) * (r2);
			double pZ1H = Math.cos(winkelRad1) * (r2);

			double pX2H = Math.sin(winkelRad2) * (r2);
			double pZ2H = Math.cos(winkelRad2) * (r2);

			double cXH = (pX2H + pX1H) / 2;
			double cZH = (pZ2H + pZ1H) / 2;

			double tX1 = pX1 + (pX2 - pX1) / 3;
			double tZ1 = pZ1 + (pZ2 - pZ1) / 3;

			double tX2 = pX1 + 2 * (pX2 - pX1) / 3;
			double tZ2 = pZ1 + 2 * (pZ2 - pZ1) / 3;

			if (triIndex < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex));
				GlStateManager.glVertex3f((float) pX1, 0, (float) pZ1);
				GlStateManager.glVertex3f((float) pX1H, 0, (float) pZ1H);
				GlStateManager.glVertex3f((float) tX1, 0, (float) tZ1);
			}

			if (triIndex + 1 < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex + 1));
				GlStateManager.glVertex3f((float) cXH, 0, (float) cZH);
			}

			if (triIndex + 2 < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex + 2));
				GlStateManager.glVertex3f((float) tX2, 0, (float) tZ2);
			}

			if (triIndex + 3 < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex + 3));
				GlStateManager.glVertex3f((float) pX2H, 0, (float) pZ2H);
			}

			if (triIndex + 4 < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex + 4));
				GlStateManager.glVertex3f((float) pX2, 0, (float) pZ2);
			}
		}

		GlStateManager.glEnd();
	}

	public static void renderCircleDecTriPart3Tri(double r1, double r2, ITriangleFunction triangleFunction, int triCount)
	{
		renderCircleDecTriPart3Tri(r1, r2, triangleFunction, triCount, 0, 10);
	}

	// triCount <= 30
	public static void renderCircleDecTriPart3Tri(double r1, double r2, ITriangleFunction triangleFunction, int triCount, int cStart, int cEnd)
	{
		GlStateManager.glBegin(GL11.GL_TRIANGLE_STRIP);

		for (int c = cStart; c < cEnd; c++)
		{
			int winkelDeg1 = 36 * c;
			int winkelDeg2 = 36 * (c + 1);

			int triIndex = (c - cStart) * 3;

			double winkelRad1 = (Math.PI * 2) / 360D * winkelDeg1;
			double winkelRad2 = (Math.PI * 2) / 360D * winkelDeg2;

			double pX1 = Math.sin(winkelRad1) * (r1);
			double pZ1 = Math.cos(winkelRad1) * (r1);

			double pX2 = Math.sin(winkelRad2) * (r1);
			double pZ2 = Math.cos(winkelRad2) * (r1);

			double pX1H = Math.sin(winkelRad1) * (r2);
			double pZ1H = Math.cos(winkelRad1) * (r2);

			double pX2H = Math.sin(winkelRad2) * (r2);
			double pZ2H = Math.cos(winkelRad2) * (r2);

			double cX = (pX2 + pX1) / 2;
			double cZ = (pZ2 + pZ1) / 2;

			if (triIndex < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex));
				GlStateManager.glVertex3f((float) pX1, 0, (float) pZ1);
				GlStateManager.glVertex3f((float) pX1H, 0, (float) pZ1H);
				GlStateManager.glVertex3f((float) cX, 0, (float) cZ);
			}

			if (triIndex + 1 < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex + 1));
				GlStateManager.glVertex3f((float) pX2H, 0, (float) pZ2H);
			}

			if (triIndex + 2 < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex + 2));
				GlStateManager.glVertex3f((float) pX2, 0, (float) pZ2);
			}
		}

		GlStateManager.glEnd();
	}

	public static void renderCircleDecTriPartCross(double r1, double r2, ITriangleFunction triangleFunction, int triCount)
	{
		for (int c = 0; c < 10; c++)
		{
			int winkelDeg1 = 36 * c;
			int winkelDeg2 = 36 * (c + 1);

			int triIndex = c * 6;

			double winkelRad1 = (Math.PI * 2) / 360D * winkelDeg1;
			double winkelRad2 = (Math.PI * 2) / 360D * winkelDeg2;

			double pX1 = Math.sin(winkelRad1) * (r1);
			double pZ1 = Math.cos(winkelRad1) * (r1);

			double pX2 = Math.sin(winkelRad2) * (r1);
			double pZ2 = Math.cos(winkelRad2) * (r1);

			double pX1N = Math.sin(winkelRad1) * (r2);
			double pZ1N = Math.cos(winkelRad1) * (r2);

			double pX2N = Math.sin(winkelRad2) * (r2);
			double pZ2N = Math.cos(winkelRad2) * (r2);

			double cXF = (pX2 + pX1) / 2;
			double cZF = (pZ2 + pZ1) / 2;

			double cXN = (pX2N + pX1N) / 2;
			double cZN = (pZ2N + pZ1N) / 2;

			double cX = (cXF + cXN) / 2;
			double cZ = (cZF + cZN) / 2;

			double uX1 = cX + (cX - pX2N);
			double uZ1 = cZ + (cZ - pZ2N);

			double uX2 = cX + (cX - pX1N);
			double uZ2 = cZ + (cZ - pZ1N);

			GlStateManager.glBegin(GL11.GL_TRIANGLE_FAN);

			GlStateManager.glVertex3f((float) cX, 0, (float) cZ);

			if (triIndex < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex));
				GlStateManager.glVertex3f((float) pX1N, 0, (float) pZ1N);
				GlStateManager.glVertex3f((float) uX1, 0, (float) uZ1);
			}

			if (triIndex + 1 < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex + 1));
				GlStateManager.glVertex3f((float) uX2, 0, (float) uZ2);
			}

			if (triIndex + 2 < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex + 2));
				GlStateManager.glVertex3f((float) pX2N, 0, (float) pZ2N);
			}

			if (triIndex + 3 < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex + 3));
				GlStateManager.glVertex3f((float) pX1N, 0, (float) pZ1N);
			}

			GlStateManager.glEnd();

			GlStateManager.glBegin(GL11.GL_TRIANGLES);
			if (triIndex + 4 < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex + 4));
				GlStateManager.glVertex3f((float) pX1, 0, (float) pZ1);
				GlStateManager.glVertex3f((float) uX1, 0, (float) uZ1);
				GlStateManager.glVertex3f((float) pX1N, 0, (float) pZ1N);
			}

			if (triIndex + 5 < triCount)
			{
				ColorUtil.applyColor(triangleFunction.apply(triIndex + 5));
				GlStateManager.glVertex3f((float) pX2, 0, (float) pZ2);
				GlStateManager.glVertex3f((float) pX2N, 0, (float) pZ2N);
				GlStateManager.glVertex3f((float) uX2, 0, (float) uZ2);
			}

			GlStateManager.glEnd();
		}
	}
}
