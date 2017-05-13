package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.event.view.MouseEventGuiFocus;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;

public class Button extends GuiFrame
{

	protected final Gui defaultState, hoveredState, clickedState, disabledState;
	
	protected boolean singleState;

	public Button(Rect dim, Gui texture)
	{
		this(dim, texture, null, null);
		
		this.singleState = true;
	}

	public Button(Rect dim, Gui defaultState, Gui hoveredState, Gui clickedState)
	{
		this(dim, defaultState, hoveredState, clickedState, null);
	}
	
	public Button(Rect dim, Gui defaultState, Gui hoveredState, Gui clickedState, Gui disabledState)
	{
		super(dim);

		this.defaultState = defaultState;
		this.hoveredState = hoveredState;
		this.clickedState = clickedState;
		
		this.disabledState = disabledState;
	}


	@Override
	public void initContent(InputProvider input)
	{
		this.defaultState.setVisible(false);
		this.defaultState.dim().mod().center(false).resetPos().flush();
		
		this.content().set("defaultState", this.defaultState);
		
		if (!this.singleState)
		{
			this.hoveredState.setVisible(false);
			this.clickedState.setVisible(false);

			this.hoveredState.dim().mod().center(false).resetPos().flush();
			this.clickedState.dim().mod().center(false).resetPos().flush();

			this.clickedState.events().set("clickEvent", new MouseEventGuiFocus(new MouseInput(MouseButton.LEFT, ButtonState.PRESS)));

			this.content().set("clickedState", this.clickedState);
			this.content().set("hoveredState", this.hoveredState);
		}

		if (this.disabledState != null)
		{
			this.disabledState.dim().mod().center(false).resetPos().flush();
			
			if (!this.isEnabled())
			{
				this.disabledState.setVisible(true);
			}
			else
			{
				this.disabledState.setVisible(false);
			}
			
			this.content().set("disabledState", this.disabledState);
		}
		
		this.defaultState.setVisible(true);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		if (this.disabledState != null)
		{
			if (!this.isEnabled())
			{
				this.disabledState.setVisible(true);
			}
			else
			{
				this.disabledState.setVisible(false);
			}
		}

		if (!this.singleState)
		{
			if (input.isHovered(this.dim()))
			{
				this.defaultState.setVisible(false);
				this.hoveredState.setVisible(true);
			}
			else
			{
				this.defaultState.setVisible(true);
				this.hoveredState.setVisible(false);
			}
		}
		else
		{
			this.defaultState.setVisible(true);
		}
	}

}
