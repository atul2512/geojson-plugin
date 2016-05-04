package com.chandan.osmosis.plugin.geojson;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.commons.io.FileUtils;
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.EntityType;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;

import com.chandan.osmosis.plugin.geojson.cache.FeatureLinestringCache;
import com.chandan.osmosis.plugin.geojson.cache.PointCache;
import com.chandan.osmosis.plugin.geojson.common.Utils;
import com.chandan.osmosis.plugin.geojson.converter.OsmNodeToFeaturePointConverter;
import com.chandan.osmosis.plugin.geojson.converter.OsmWayToFeatureLineStringConverter;
import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.LineString;
import com.chandan.osmosis.plugin.geojson.model.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

public class GeoJsonSink implements Sink {

	private PointCache pointCache;
	private FeatureLinestringCache lineStringCache;
	private OsmNodeToFeaturePointConverter osmNodeToFeaturePointConverter;
	private OsmWayToFeatureLineStringConverter osmWayToFeatureLineStringConverter;
	private Environment dbEnv;
	
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
		pointCache = new PointCache(dbEnv);
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
		case Bound:
			Bound bound = (Bound) entity;
			break;
		case Node:
			Node node = (Node) entity;
			Feature<Point> featurePoint = osmNodeToFeaturePointConverter.getGeojsonModel(node);
			try {
				System.out.println(Utils.jsonEncode(featurePoint));
			}
			catch (JsonProcessingException e) {
				e.printStackTrace(System.err);
				throw new RuntimeException(e);
			}
			break;
		case Way:
			Way way = (Way) entity;
			Feature<LineString> featureLineString = osmWayToFeatureLineStringConverter.getGeojsonModel(way);
			try {
				System.out.println(Utils.jsonEncode(featureLineString));
			} catch (JsonProcessingException e) {
				e.printStackTrace(System.err);
				throw new RuntimeException(e);
			}
			break;
		case Relation:
			Relation relation = (Relation) entity;
			//System.out.println(relation.toString());
			break;
		}
	}
}
