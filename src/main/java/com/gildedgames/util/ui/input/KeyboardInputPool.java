package com.gildedgames.util.ui.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Iterators;

public class KeyboardInputPool implements Iterable<KeyboardInput>
{
	
	protected final KeyboardInput[] events;

	public KeyboardInputPool(KeyboardInput... events)
	{
		this.events = events;
	}
	
	public int size()
	{
		return this.events.length;
	}
	
	public KeyboardInput get(int index)
	{
		if (index >= this.events.length)
		{
			 return null;
		}
		
		return this.events[index];
	}
	
	public boolean contains(Object o)
	{
		return indexOf(o) >= 0;
	}
	
	public boolean containsAll(Collection<?> c)
	{
		return Arrays.asList(this.events).containsAll(c);
	}
	
    public int indexOf(Object o)
    {
        return ArrayUtils.indexOf(this.events, o);
    }
	
	public KeyboardInputPool getFrom(int key)
	{
		List<KeyboardInput> events = new ArrayList<KeyboardInput>();
		
		for (KeyboardInput event : this)
		{
			if (event != null && event.getKey() == key)
			{
				events.add(event);
			}
		}
		
		return new KeyboardInputPool(events.toArray(new KeyboardInput[events.size()]));
	}
	
	public KeyboardInputPool getFrom(char character)
	{
		List<KeyboardInput> events = new ArrayList<KeyboardInput>();
		
		for (KeyboardInput event : this)
		{
			if (event != null && (char)event.getKey() == character)
			{
				events.add(event);
			}
		}
		
		return new KeyboardInputPool(events.toArray(new KeyboardInput[events.size()]));
	}
	
	public KeyboardInputPool getFrom(ButtonState state)
	{
		List<KeyboardInput> events = new ArrayList<KeyboardInput>();
		
		for (KeyboardInput event : this)
		{
			if (event != null && event.getState() == state)
			{
				events.add(event);
			}
		}
		
		return new KeyboardInputPool(events.toArray(new KeyboardInput[events.size()]));
	}
	
	public boolean has(int key)
	{
		for (KeyboardInput event : this)
		{
			if (event != null && event.getKey() == key)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean has(char character)
	{
		for (KeyboardInput event : this)
		{
			if (event != null && (char)event.getKey() == character)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean has(ButtonState state)
	{
		for (KeyboardInput event : this)
		{
			if (event != null && event.getState() == state)
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Iterator<KeyboardInput> iterator()
	{
		return Iterators.forArray(this.events);
	}
	
}
