package com.chandan.osmosis.plugin.geojson;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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
	private OsmNodeToFeaturePointConverter osmNodeToFeaturePointConverter;
	private OsmWayToFeatureLineStringConverter osmWayToFeatureLineStringConverter;
	private Environment dbEnv;
	private final String geoJsonFile;
	private final String directoryForCache;

	public GeoJsonSink(String geoJsonFile, String directoryForCache) {
		this.geoJsonFile = geoJsonFile;
		this.directoryForCache = directoryForCache;
	}

	@Override
	public void initialize(Map<String, Object> metaData) {
		try {
			FileUtils.deleteDirectory(new File("/opt/osm/temp"));
		}
		catch (IOException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
		new File("/opt/osm/temp").mkdirs();
		EnvironmentConfig enConfig = new EnvironmentConfig();
		enConfig.setAllowCreate(true);
		this.dbEnv = new Environment(new File("/opt/osm/temp"), enConfig);
		pointCache = new FeaturePointCache(dbEnv);
		pointCache.init();
		lineStringCache = new FeatureLinestringCache(dbEnv);
		lineStringCache.init();
		osmNodeToFeaturePointConverter = new OsmNodeToFeaturePointConverter(pointCache);
		osmWayToFeatureLineStringConverter = new OsmWayToFeatureLineStringConverter(pointCache, lineStringCache);
		System.out.println("GeoJsonPlugin initialised");
	}

	@Override
	public void complete() {
		System.out.println("GeoJsonPlugin complete");
	}

	@Override
	public void release() {
		System.out.println("GeoJsonPlugin released");
	}

	@Override
	public void process(EntityContainer entityContainer) {
		Entity entity = entityContainer.getEntity();
		EntityType entityType = entity.getType();
		switch (entityType) {
		case Node:
			Node node = (Node) entity;
			Feature<Point> featurePoint = osmNodeToFeaturePointConverter.getGeojsonModel(node);
			osmNodeToFeaturePointConverter.persistGeoJsonModelToCache(node.getId(), featurePoint);
			if (osmNodeToFeaturePointConverter.isValid(featurePoint)) {
				try {
					System.out.println(Utils.jsonEncode(featurePoint));
				} catch (JsonProcessingException e) {
					e.printStackTrace(System.err);
					throw new RuntimeException(e);
				}
			}
			break;
		case Way:
			Way way = (Way) entity;
			Feature<LineString> featureLineString = osmWayToFeatureLineStringConverter.getGeojsonModel(way);
			osmWayToFeatureLineStringConverter.persistGeoJsonModelToCache(way.getId(), featureLineString);
			if (osmWayToFeatureLineStringConverter.isValid(featureLineString)) {
				try {
					System.out.println(Utils.jsonEncode(featureLineString));
				} catch (JsonProcessingException e) {
					e.printStackTrace(System.err);
					throw new RuntimeException(e);
				}
			}
			break;
		case Relation:
			Relation relation = (Relation) entity;
			break;
		}
	}
}
