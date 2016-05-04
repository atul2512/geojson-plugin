package com.chandan.osmosis.plugin.geojson.converter;

import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.TagCollection;

import com.chandan.osmosis.plugin.geojson.cache.PointCache;
import com.chandan.osmosis.plugin.geojson.common.Utils;
import com.chandan.osmosis.plugin.geojson.model.Coordinate;
import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.NodeProperties;
import com.chandan.osmosis.plugin.geojson.model.Point;

public class OsmNodeToFeaturePointConverter extends OsmToFeatureConverter<Node, Point> {

	private final PointCache pointCache;

	public OsmNodeToFeaturePointConverter(PointCache pointCache) {
		this.pointCache = pointCache;
	}

	@Override
	public Feature<Point> getGeojsonModel(Node t) {
		pointCache.put(t.getId(), new Point(new Coordinate(t.getLongitude(), t.getLatitude())));
		NodeProperties properties = getNodeProperties(t);
		if (properties != null) {
			Feature<Point> featurePoint = new Feature<Point>(new Point(
					new Coordinate(t.getLongitude(), t.getLatitude())), properties);
			return featurePoint;
		}
		return null;
	}

	private NodeProperties getNodeProperties(Node t) {
		if ((t.getTags() != null && t.getTags().size() > 0)) {
			NodeProperties nodeProperties = new NodeProperties();
			Utils.populateCommonProperties(t, nodeProperties);
			if (!Utils.hasCommonProperty(nodeProperties)) {
				return null;
			}
			String amenity = ((TagCollection) t.getTags()).buildMap().get("amenity");
			nodeProperties.setAmenity(amenity);
		}
		return null;
	}
}
