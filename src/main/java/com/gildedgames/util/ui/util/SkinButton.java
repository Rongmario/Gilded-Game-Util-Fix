package com.gildedgames.util.ui.util;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.MinecraftAssetLocation;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.AssetLocation;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.graphics.Sprite.UV;
import com.gildedgames.util.ui.input.InputProvider;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

public class SkinButton extends GuiFrame
{
	private final GameProfile profile;

	public SkinButton(GameProfile profile, int x, int y)
	{
		super(Dim2D.build().area(8, 8).pos(x, y).flush());
		this.profile = profile;
	}

	public SkinButton(GameProfile profile)
	{
		super(Dim2D.build().area(8, 8).flush());
		this.profile = profile;
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		Minecraft minecraft = Minecraft.getMinecraft();
		Map map = minecraft.getSkinManager().loadSkinFromCache(this.profile);
		ResourceLocation location;

		if (map.containsKey(Type.SKIN))
		{
			location = minecraft.getSkinManager().loadSkin((MinecraftProfileTexture) map.get(Type.SKIN), Type.SKIN);
		}
		else
		{
			location = SkinManager.DEFAULT_SKIN;
		}

		AssetLocation asset = new MinecraftAssetLocation(location);
		Sprite sprite = Sprite.createWithArea(asset, UV.build().min(8f, 8f).max(16, 16f).flush(), 64, 64);
		this.content().set("head", GuiFactory.texture(sprite));
	}

}
