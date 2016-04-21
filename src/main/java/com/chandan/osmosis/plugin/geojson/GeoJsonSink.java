package com.chandan.osmosis.plugin.geojson;

import java.io.File;
import java.util.Map;

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.EntityType;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.TagCollection;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;

import com.chandan.osmosis.plugin.geojson.cache.FeatureLinestringCache;
import com.chandan.osmosis.plugin.geojson.cache.PointCache;
import com.chandan.osmosis.plugin.geojson.common.Utils;
import com.chandan.osmosis.plugin.geojson.converter.OsmNodeToFeaturePointConverter;
import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.Point;
import com.fasterxml.jackson.core.JsonProcessingException;

public class GeoJsonSink implements Sink {

	private PointCache pointCache;
	private FeatureLinestringCache lineStringCache;
	private OsmNodeToFeaturePointConverter osmNodeToFeaturePointConverter;
	
	
	@Override
	public void initialize(Map<String, Object> metaData) {
		new File("/opt/osm/temp").delete();
		new File("/opt/osm/temp").mkdirs();
		pointCache = new PointCache("/opt/osm/temp");
		lineStringCache = new FeatureLinestringCache("/opt/osm/temp");
		osmNodeToFeaturePointConverter = new OsmNodeToFeaturePointConverter(pointCache);
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
		System.out.println("Process entity type : ");
		switch (entityType) {
		case Bound:
			Bound bound = (Bound) entity;
			//System.out.println(bound.toString());
			break;
		case Node:
			Node node = (Node) entity;
			Feature<Point> featurePoint = osmNodeToFeaturePointConverter.getGeojsonModel(node);
			try {
				System.out.println(Utils.jsonEncode(featurePoint));
			}
			catch (JsonProcessingException e) {
				e.printStackTrace(System.err);
			}
			break;
		case Way:
			Way way = (Way) entity;
			break;
		case Relation:
			Relation relation = (Relation) entity;
			//System.out.println(relation.toString());
			break;
		}
	}
}
