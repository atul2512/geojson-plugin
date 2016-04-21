package com.chandan.osmosis.plugin.geojson.converter;

import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.TagCollection;

import com.chandan.osmosis.plugin.geojson.cache.PointCache;
import com.chandan.osmosis.plugin.geojson.model.Coordinate;
import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.Properties;
import com.chandan.osmosis.plugin.geojson.model.Point;

public class OsmNodeToFeaturePointConverter extends OsmToFeatureConverter<Node, Point> {

	private final PointCache pointCache;

	public OsmNodeToFeaturePointConverter(PointCache pointCache) {
		this.pointCache = pointCache;
	}

	@Override
	public Feature<Point> getGeojsonModel(Node t) {
		pointCache.put(t.getId(), new Point(new Coordinate(t.getLongitude(), t.getLatitude())));
		Feature<Point> featurePoint = new Feature<Point>();
		featurePoint.setGeometry(new Point(new Coordinate(t.getLongitude(), t.getLatitude())));
		Properties properties = getCommonProperties(t);
		if (properties != null) {
			featurePoint.setProperties(getCommonProperties(t));
			return featurePoint;
		}
		return null;
	}

	public Properties getCommonProperties(Node t) {
		if ((t.getTags() != null && t.getTags().size() > 0)) {
			Properties properties = new Properties();
			properties.setOsmId(t.getId());
			String name = ((TagCollection) t.getTags()).buildMap().get("name");
			if (name != null && name.length() > 0) {
				properties.setName(name);
				return properties;
			}
		}
		return null;
	}
}
