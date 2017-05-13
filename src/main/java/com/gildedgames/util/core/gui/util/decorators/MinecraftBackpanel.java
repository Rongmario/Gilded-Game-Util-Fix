package com.gildedgames.util.core.gui.util.decorators;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiDecorator;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.TextureElement;

public class MinecraftBackpanel extends GuiDecorator<Gui>
{
	
	private final float leftPadding, rightPadding, topPadding, bottomPadding;
	
	public MinecraftBackpanel(Gui gui, final float padding)
	{
		this(gui, padding, padding, padding, padding);
	}
	
	public MinecraftBackpanel(Gui gui, final float leftPadding, final float rightPadding, final float topPadding, final float bottomPadding)
	{
		super(gui);
		
		this.leftPadding = leftPadding;
		this.rightPadding = rightPadding;
		
		this.topPadding = topPadding;
		this.bottomPadding = bottomPadding;
	}

	@Override
	protected void preInitContent(InputProvider input)
	{
		final TextureElement backPanel = GuiFactory.panel(Dim2D.build().flush());
		
		backPanel.dim().mod().area(this.leftPadding  + this.rightPadding, this.topPadding + this.bottomPadding).pos(-this.leftPadding, -this.topPadding).flush();
		
		backPanel.dim().add(this, ModifierType.AREA);

		this.content().set("backPanel", backPanel);
	}

	@Override
	protected void postInitContent(InputProvider input)
	{
		
	}

}
