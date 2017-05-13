package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.ui.common.Decorator;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.ModDim2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.ScrollBar;
import com.gildedgames.util.ui.util.rect.RectCollection;
import com.gildedgames.util.ui.util.rect.RectGetter;

public class ScrollableGui extends GuiFrame implements Decorator<GuiFrame>
{

	protected ScrollBar scrollBar;

	protected ScissorableGui scrolledGui;

	protected GuiFrame backdrop, backdropEmbedded;

	protected final int padding;

	public ScrollableGui(Rect windowSize, Gui scrolledGui)
	{
		this(windowSize, scrolledGui, GuiFactory.createScrollBar());
	}

	public ScrollableGui(Rect windowSize, Gui scrolledGui, ScrollBar scrollBar)
	{
		this(windowSize, scrolledGui, scrollBar, GuiFactory.panel(Dim2D.flush()), GuiFactory.panelEmbedded(Dim2D.flush()), 7);
	}

	public ScrollableGui(Rect windowSize, Gui scrolledGui, ScrollBar scrollBar, GuiFrame backdrop, GuiFrame backdropEmbedded, int padding)
	{
		this.dim().set(windowSize);

		this.padding = padding;

		this.scrolledGui = new ScissorableGui(Dim2D.flush(), scrolledGui);
		this.scrollBar = scrollBar;

		this.backdrop = backdrop;
		this.backdropEmbedded = backdropEmbedded;
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		this.scrollBar.dim().add(this, ModifierType.HEIGHT).mod().resetPos().flush();
		
		int posPadding = this.padding + 1;
		int areaPadding = this.padding * 2 + 1;
		
		this.scrolledGui.getScissoredArea().add(this, ModifierType.ALL).mod().pos(posPadding, posPadding).area(-areaPadding, -areaPadding).flush();

		this.scrolledGui.dim().add(new RectGetter()
		{

			private float prevScrollPer, scrollPer;

			@Override
			public Rect assembleRect()
			{
				ScrollBar scrollBar = ScrollableGui.this.scrollBar;

				this.prevScrollPer = scrollBar.getScrollPercentage();

				double scrolledElementHeight = ModDim2D.clone(ScrollableGui.this.scrolledGui).clear(ModifierType.HEIGHT).height();
				double scissoredHeight = ScrollableGui.this.scrolledGui.getScissoredArea().height();

				int scrollValue = (int) -(this.prevScrollPer * (scrolledElementHeight - scissoredHeight));

				return ModDim2D.build().mod().y(scrollValue).flush();
			}

			@Override
			public boolean shouldReassemble()
			{
				ScrollBar scrollBar = ScrollableGui.this.scrollBar;

				this.scrollPer = scrollBar.getScrollPercentage();

				if (this.scrollPer != this.prevScrollPer)
				{
					this.prevScrollPer = this.scrollPer;

					return true;
				}

				return false;
			}

		}, ModifierType.Y).mod().resetPos().flush();

		this.scrollBar.dim().mod().resetPos().flush();

		this.scrollBar.dim().mod().center(false).pos(this.padding + 1, this.padding + 1).height(-this.padding * 2 - 2).flush();
		this.scrolledGui.dim().mod().center(false).pos(this.scrollBar.dim().maxX(), this.padding).addArea(-this.scrollBar.dim().width() - (this.padding * 2), -this.padding * 2).flush();

		RectCollection scrollingArea = RectCollection.build().addHolder(this).flush();

		this.scrollBar.setScrollingAreas(scrollingArea);
		this.scrollBar.setContentArea(this.scrolledGui);

		Rect backdropDim = ModDim2D.build().add(this, ModifierType.AREA).mod().flush();
		Rect embeddedDim = ModDim2D.build().add(this, ModifierType.AREA).mod().addArea(-this.padding * 2, -this.padding * 2).pos(this.padding, this.padding).flush();

		this.backdrop.dim().clear().set(backdropDim);
		this.backdropEmbedded.dim().clear().set(embeddedDim);

		this.content().set("backdrop", this.backdrop);
		this.content().set("backdropEmbedded", this.backdropEmbedded);

		this.content().set("scrolledGui", this.scrolledGui);
		this.content().set("scrollBar", this.scrollBar);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}

	@Override
	public GuiFrame getDecoratedElement()
	{
		return this.scrolledGui;
	}

}
