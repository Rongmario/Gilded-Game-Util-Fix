package com.gildedgames.util.ui.util.factory;

import java.util.LinkedHashMap;

import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.rect.Rect;
import com.google.common.collect.ImmutableMap;

public interface ContentFactory<T extends Ui>
{

	LinkedHashMap<String, T> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea);
	
	boolean shouldRefreshContent();

}
