package com.chandan.osmosis.plugin.geojson.cache;

import com.chandan.osmosis.plugin.geojson.model.Geometry;

public interface Cache<T extends Geometry> {
	
	public void init();
	
	public T get(long key);
	
	public void put(long key, T t);
}
