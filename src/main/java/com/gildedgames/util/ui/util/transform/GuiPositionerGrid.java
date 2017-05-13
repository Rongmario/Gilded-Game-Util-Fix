package com.gildedgames.util.ui.util.transform;

import java.util.List;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.data.rect.Rect;

public class GuiPositionerGrid implements GuiPositioner
{
	
	private final int paddingX, paddingY, startX, startY;

	public GuiPositionerGrid()
	{
		this(0);
	}
	
	public GuiPositionerGrid(int padding)
	{
		this(padding, padding);
	}

	public GuiPositionerGrid(int paddingX, int paddingY)
	{
		this.paddingX = paddingX;
		this.paddingY = paddingY;
		
		this.startX = this.paddingX;
		this.startY = this.paddingY;
	}

	@Override
	public List<Gui> positionList(List<Gui> guis, Rect listDimensions)
	{
		int currentX = this.startX;
		int currentY = this.startY;

		for (Gui gui : guis)
		{
			gui.dim().mod().pos(currentX, currentY).center(false).flush();

			currentX += gui.dim().width() + this.paddingX;
			if (currentX + gui.dim().width() + this.paddingX > listDimensions.width())
			{
				double maxHeight = gui.dim().height();
				for (Gui view1 : guis)
				{
					if (view1.dim().y() == currentY)
					{
						maxHeight = Math.max(view1.dim().height(), maxHeight);
					}
				}
				currentY += maxHeight + this.paddingY;
				currentX = this.startX;
			}
		}

		return guis;
	}
}
