package com.gildedgames.util.core.gui.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.gildedgames.util.core.gui.util.events.MinecraftHoveredDesc;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButtonItemStack;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftItemStackRender;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.util.events.slots.SlotStackFactory;
import com.gildedgames.util.ui.util.factory.ContentFactory;
import com.gildedgames.util.ui.util.factory.Factory;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

import cpw.mods.fml.common.registry.GameData;

public class ItemStackButtonFactory<T> implements ContentFactory<Ui>
{

	public static enum StackTypes
	{

		BLOCKS
		{
			@Override
			List<ItemStack> createStacks()
			{
				List<ItemStack> blockStacks = new ArrayList<ItemStack>();

				for (final Block block : GameData.getBlockRegistry().typeSafeIterable())//TODO: Make sure this gets all blocks
				{
					if (block == null)
					{
						continue;
					}

					final Item item = Item.getItemFromBlock(block);

					if (item == null)
					{
						continue;
					}

					List<ItemStack> subBlocks = new ArrayList<ItemStack>();

					block.getSubBlocks(item, item.getCreativeTab(), subBlocks);

					for (final ItemStack stack : subBlocks)
					{
						if (stack.getItem() instanceof ItemBlock && ((ItemBlock) stack.getItem()).blockInstance == Blocks.air)
						{
							continue;
						}

						blockStacks.add(stack);
					}
				}

				return blockStacks;
			}
		},
		ITEMS
		{
			@Override
			List<ItemStack> createStacks()
			{
				return null;
			}
		},
		ALL
		{
			@Override
			List<ItemStack> createStacks()
			{
				List<ItemStack> stacks = new ArrayList<ItemStack>();

				stacks.addAll(ITEMS.createStacks());
				stacks.addAll(BLOCKS.createStacks());

				return stacks;
			}
		};

		abstract List<ItemStack> createStacks();

	}

	private StackTypes stackTypes;
	
	private Function<ItemStack, T> dataFactory;

	public ItemStackButtonFactory(StackTypes stackTypes, Function<ItemStack, T> dataFactory)
	{
		this.stackTypes = stackTypes;
		this.dataFactory = dataFactory;
	}

	@Override
	public LinkedHashMap<String, Ui> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea)
	{
		LinkedHashMap<String, Ui> buttons = new LinkedHashMap<String, Ui>();

		for (ItemStack stack : this.stackTypes.createStacks())
		{
			final MinecraftButtonItemStack button = new MinecraftButtonItemStack(stack);
			
			button.events().set("description", new MinecraftHoveredDesc(GuiFactory.text(stack.getDisplayName(), Color.WHITE)));

			button.events().set("draggableBehavior", new SlotStackFactory<T>(new Function<T, GuiFrame>()
			{

				@Override
				public GuiFrame apply(T input)
				{
					return new MinecraftItemStackRender(button.getItemStack());
				}

			},
			new Factory<T>()
			{

				@Override
				public T create()
				{
					return ItemStackButtonFactory.this.dataFactory.apply(button.getItemStack());
				}
				
			}));

			buttons.put(stack.getUnlocalizedName(), button);
		}

		return buttons;
	}
	
	@Override
	public boolean shouldRefreshContent()
	{
		return false;
	}

}
