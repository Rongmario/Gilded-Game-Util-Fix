package com.gildedgames.util.io_manager.factory;

import com.gildedgames.util.io_manager.io.IO;

public interface IOBridge
{

	byte[] getBytes();

	Class<?> getSerializedClass(String key);

	void setSerializedClass(String key, Class<?> classToWrite);

	void setBoolean(String key, boolean value);

	void setByte(String key, byte value);

	void setShort(String key, short value);

	void setInteger(String key, int value);

	void setLong(String key, long value);

	void setFloat(String key, float value);

	void setDouble(String key, double value);

	void setString(String key, String value);

	void setByteArray(String key, byte[] value);

	void setIO(String key, IO<IOBridge, IOBridge> io);

	byte getByte(String key);

	short getShort(String key);

	int getInteger(String key);

	long getLong(String key);

	float getFloat(String key);

	double getDouble(String key);

	String getString(String key);

	byte[] getByteArray(String key);

	boolean getBoolean(String key);

	<T extends IO<IOBridge, IOBridge>> T getIO(String key);

}
