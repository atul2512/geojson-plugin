package com.chandan.osmosis.plugin.geojson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.chandan.osmosis.plugin.geojson.converter.OsmNodeProcessor;
import com.chandan.osmosis.plugin.geojson.converter.OsmWayProcessor;
import org.apache.commons.io.FileUtils;
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.EntityType;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;

import com.chandan.osmosis.plugin.geojson.cache.FeatureLinestringCache;
import com.chandan.osmosis.plugin.geojson.cache.FeaturePointCache;
import com.chandan.osmosis.plugin.geojson.common.Utils;
import com.chandan.osmosis.plugin.geojson.converter.OsmNodeToFeaturePointConverter;
import com.chandan.osmosis.plugin.geojson.converter.OsmWayToFeatureLineStringConverter;
import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.LineString;
import com.chandan.osmosis.plugin.geojson.model.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import org.openstreetmap.osmosis.core.util.TileCalculator;

public class GeoJsonSink implements Sink {

	private FeaturePointCache pointCache;
	private FeatureLinestringCache lineStringCache;
	private Environment dbEnv;
	private final String geoJsonFile;
	private final String directoryForCache;
	private OutputStreamWriter writer;
	private OsmNodeProcessor osmNodeProcessor;
	private OsmWayProcessor osmWayProcessor;

	public GeoJsonSink(String geoJsonFile, String directoryForCache) {
		this.geoJsonFile = geoJsonFile;
		this.directoryForCache = directoryForCache;
	}

	@Override
	public void initialize(Map<String, Object> metaData) {
		try {
			FileUtils.deleteDirectory(new File(directoryForCache));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		new File(directoryForCache).mkdirs();
		try {
			writer = new OutputStreamWriter(new FileOutputStream(geoJsonFile));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		EnvironmentConfig enConfig = new EnvironmentConfig();
		enConfig.setAllowCreate(true);
		this.dbEnv = new Environment(new File(directoryForCache), enConfig);
		pointCache = new FeaturePointCache(dbEnv);
		pointCache.init();
		lineStringCache = new FeatureLinestringCache(dbEnv);
		lineStringCache.init();
		this.osmNodeProcessor = new OsmNodeProcessor(pointCache, writer);
		this.osmWayProcessor = new OsmWayProcessor(pointCache, lineStringCache, writer);
		System.out.println("GeoJsonPlugin initialised");
	}

	@Override
	public void complete() {
		System.out.println("GeoJsonPlugin complete");
	}

	@Override
	public void release() {
		try {
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("GeoJsonPlugin released");
	}

	@Override
	public void process(EntityContainer entityContainer) {
		Entity entity = entityContainer.getEntity();
		EntityType entityType = entity.getType();
		switch (entityType) {
		case Node:
			Node node = (Node) entity;
			osmNodeProcessor.process(node);
			break;
		case Way:
			Way way = (Way) entity;
			osmWayProcessor.process(way);
			break;
		case Relation:
			Relation relation = (Relation) entity;
			break;
		}
	}
}
