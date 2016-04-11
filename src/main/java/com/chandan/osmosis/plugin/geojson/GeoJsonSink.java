package com.chandan.osmosis.plugin.geojson;

import java.util.Map;

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.EntityType;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;

import com.chandan.osmosis.plugin.geojson.converter.OsmNodeToFeaturePointConverter;

public class GeoJsonSink implements Sink {

	private OsmNodeToFeaturePointConverter nodeToFeaturePointConverter = new OsmNodeToFeaturePointConverter();
	
	@Override
	public void initialize(Map<String, Object> metaData) {
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
			System.out.println(bound.toString());
			break;
		case Node:
			Node node = (Node) entity;
			System.out.println(node.toString());
			break;
		case Way:
			Way way = (Way) entity;
			System.out.println(way.toString());
			break;
		case Relation:
			Relation relation = (Relation) entity;
			System.out.println(relation.toString());
			break;
		}
	}
}
