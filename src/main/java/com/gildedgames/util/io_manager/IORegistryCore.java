package com.gildedgames.util.io_manager;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.ISerializeBehaviour;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;

import java.util.List;

public class IORegistryCore implements IORegistry
{

	private final List<IOManager> managers;

	protected IORegistryCore(List<IOManager> managers)
	{
		this.managers = managers;
	}

	@Override
	public void registerClass(Class<?> classToSerialize, int classID)
	{
		IOCore.io().defaultManager.getRegistry().registerClass(classToSerialize, classID);
	}

	@Override
	public void registerBehavior(Class<?> classToSerialize, ISerializeBehaviour<?> serializeBehaviour)
	{
		IOCore.io().defaultManager.getRegistry().registerBehavior(classToSerialize, serializeBehaviour);
	}

	@Override
	public <T> T create(Class<T> registeredClass, IConstructor... classConstructors)
	{
		IORegistry registry = IOCore.io().getManager(registeredClass).getRegistry();

		if (registry != null)
		{
			return registry.create(registeredClass, classConstructors);
		}

		return null;
	}

	@Override
	public Object create(String registryID, int registeredClassID)
	{
		IORegistry registry = IOCore.io().getManager(registryID).getRegistry();

		if (registry != null)
		{
			return registry.create(registryID, registeredClassID);
		}

		return null;
	}

	@Override
	public Object create(String registryID, int registeredClassID, IConstructor... classConstructors)
	{
		IORegistry registry = IOCore.io().getManager(registryID).getRegistry();

		if (registry != null)
		{
			return registry.create(registryID, registeredClassID, classConstructors);
		}

		return null;
	}

	@Override
	public Class<?> getClass(String registryID, int registeredClassID)
	{
		IORegistry registry = IOCore.io().getManager(registryID).getRegistry();

		if (registry != null)
		{
			return registry.getClass(registryID, registeredClassID);
		}

		return null;
	}

	@Override
	public int getID(Class<?> registeredClass)
	{
		IORegistry registry = IOCore.io().getManager(registeredClass).getRegistry();

		if (registry != null)
		{
			return registry.getID(registeredClass);
		}

		return -1;
	}

	@Override
	public int getID(Object objectOfRegisteredClass)
	{
		return this.getID(objectOfRegisteredClass.getClass());
	}

	@Override
	public boolean isClassRegistered(Class<?> clazz)
	{
		for (IOManager manager : this.managers)
		{
			IORegistry registry = manager.getRegistry();

			if (registry.isClassRegistered(clazz))
			{
				return true;
			}
		}

		return false;
	}

}
