package com.gildedgames.util.core.gui.viewing;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.KeyboardInput;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.input.MouseMotion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public final class MinecraftGuiWrapper extends GuiScreen
{

	protected final static MinecraftGraphics2D GRAPHICS = new MinecraftGraphics2D(Minecraft.getMinecraft());

	protected final static MinecraftInputProvider INPUT = new MinecraftInputProvider(Minecraft.getMinecraft());

	private final GuiFrame frame;

	private boolean hasInit;

	public MinecraftGuiWrapper(GuiFrame frame)
	{
		this.frame = frame;
	}

	public GuiFrame getFrame()
	{
		return this.frame;
	}

	@Override
	public final void initGui()
	{
		this.updateScreen();

		INPUT.refreshResolution();

		if (!this.hasInit)
		{
			this.frame.init(INPUT);

			this.hasInit = true;
		}
		else
		{
			this.frame.onResolutionChange(INPUT);
		}
	}

	@Override
	protected final void keyTyped(char charTyped, int keyTyped)
	{
		KeyboardInputPool pool = new KeyboardInputPool(new KeyboardInput(keyTyped, charTyped, ButtonState.PRESS));

		this.frame.onKeyboardInput(pool, INPUT);
	}

	@Override
	protected final void mouseClicked(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseInputPool pool = new MouseInputPool(new MouseInput(MouseButton.fromIndex(mouseButtonIndex), ButtonState.PRESS), new MouseInput(MouseButton.fromIndex(mouseButtonIndex), ButtonState.PRESS));

		this.frame.onMouseInput(pool, INPUT);
	}

	@Override
	public final void mouseReleased(int mouseX, int mouseY, int mouseButtonIndex)
	{
		MouseInputPool pool = new MouseInputPool(new MouseInput(MouseButton.fromIndex(mouseButtonIndex), ButtonState.RELEASE));

		this.frame.onMouseInput(pool, INPUT);
	}

	@Override
	protected final void mouseClickMove(int mouseX, int mouseY, int mouseButtonIndex, long timeSinceLastClick)
	{
		MouseInputPool pool = new MouseInputPool(new MouseInput(MouseButton.fromIndex(mouseButtonIndex), ButtonState.HOLD));

		this.frame.onMouseInput(pool, INPUT);
	}

	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		int scrollDifference = Mouse.getDWheel();

		if (scrollDifference != 0)
		{
			this.frame.onMouseScroll(scrollDifference, INPUT);
		}

		this.frame.draw(GRAPHICS, INPUT);
	}

	public final void tick(TickInfo info)
	{
		this.frame.tick(info, INPUT);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	protected final void actionPerformed(GuiButton button)
	{
		
	}

}
