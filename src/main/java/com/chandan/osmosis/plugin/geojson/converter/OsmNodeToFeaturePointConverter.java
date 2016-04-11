package com.chandan.osmosis.plugin.geojson.converter;

import org.openstreetmap.osmosis.core.domain.v0_6.Node;

import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.Point;

public class OsmNodeToFeaturePointConverter extends OsmToFeatureConverter<Node, Point> {

	@Override
	public Feature<Point> getGeojsonModel(Node t) {
		return null;
	}
}
