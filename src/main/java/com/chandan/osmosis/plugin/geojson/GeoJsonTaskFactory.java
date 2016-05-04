package com.chandan.osmosis.plugin.geojson;

import org.openstreetmap.osmosis.core.pipeline.common.TaskConfiguration;
import org.openstreetmap.osmosis.core.pipeline.common.TaskManager;
import org.openstreetmap.osmosis.core.pipeline.common.TaskManagerFactory;
import org.openstreetmap.osmosis.core.pipeline.v0_6.SinkManager;

public class GeoJsonTaskFactory extends TaskManagerFactory {

	private static final String GEO_JSON_FILE_ARG = "geojsonFile";
	private static final String DIRECTORY_FOR_CACHE = "directoryForCache";

	@Override
	protected TaskManager createTaskManagerImpl(TaskConfiguration taskConfig) {
		String geoJsonFile = getStringArgument(taskConfig, GEO_JSON_FILE_ARG);
		String directoryForCache = getStringArgument(taskConfig, DIRECTORY_FOR_CACHE);
		GeoJsonSink geoJsonSink = new GeoJsonSink(geoJsonFile, directoryForCache);
		return new SinkManager(taskConfig.getId(), geoJsonSink, taskConfig.getPipeArgs());
	}
}
