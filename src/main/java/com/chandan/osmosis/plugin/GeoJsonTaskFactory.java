package com.chandan.osmosis.plugin;

import org.openstreetmap.osmosis.core.pipeline.common.TaskConfiguration;
import org.openstreetmap.osmosis.core.pipeline.common.TaskManager;
import org.openstreetmap.osmosis.core.pipeline.common.TaskManagerFactory;
import org.openstreetmap.osmosis.core.pipeline.v0_6.SinkManager;

public class GeoJsonTaskFactory extends TaskManagerFactory {

	@Override
	protected TaskManager createTaskManagerImpl(TaskConfiguration taskConfig) {
		GeoJsonSink geoJsonSink = new GeoJsonSink();
		return new SinkManager(taskConfig.getId(), geoJsonSink, taskConfig.getPipeArgs());
	}
}
