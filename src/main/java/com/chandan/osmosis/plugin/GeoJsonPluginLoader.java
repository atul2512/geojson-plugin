package com.chandan.osmosis.plugin;

import java.util.HashMap;
import java.util.Map;

import org.openstreetmap.osmosis.core.pipeline.common.TaskManagerFactory;
import org.openstreetmap.osmosis.core.plugin.PluginLoader;

public class GeoJsonPluginLoader implements PluginLoader {

	@Override
	public Map<String, TaskManagerFactory> loadTaskFactories() {
		Map<String, TaskManagerFactory> map = new HashMap<String, TaskManagerFactory>();
		map.put("geojson-plugin", new GeoJsonTaskFactory());
		return map;
	}

}
