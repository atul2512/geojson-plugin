package com.chandan.osmosis.plugin.geojson.converter;

import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.TagCollection;

import com.chandan.osmosis.plugin.geojson.cache.FeaturePointCache;
import com.chandan.osmosis.plugin.geojson.common.Utils;
import com.chandan.osmosis.plugin.geojson.model.Coordinate;
import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.NodeProperties;
import com.chandan.osmosis.plugin.geojson.model.Point;

public class OsmNodeToFeaturePointConverter extends OsmToFeatureConverter<Node, Point> {

	private final FeaturePointCache featurePointCache;

	public OsmNodeToFeaturePointConverter(FeaturePointCache featurePointCache) {
		this.featurePointCache = featurePointCache;
	}

	@Override
	public Feature<Point> getGeojsonModel(Node node) {
		NodeProperties properties = getNodeProperties(node);
		return new Feature<Point>(new Point(
					new Coordinate(node.getLongitude(), node.getLatitude())), properties);
	}

	@Override
	public boolean isValid(Feature<Point> feature) {
		if (feature.getProperties() != null) {
			return true;
		}
		return false;
	}

	@Override
	public void persistGeoJsonModelToCache(long osmId, Feature<Point> pointFeature) {
		featurePointCache.put(osmId, pointFeature);
	}

	@Override
	public boolean shouldBeEmitted(Feature<Point> pointFeature) {
		if (pointFeature.getProperties() != null) {
			return true;
		}
		return false;
	}

	private NodeProperties getNodeProperties(Node t) {
		if ((t.getTags() != null && t.getTags().size() > 0)) {
			NodeProperties nodeProperties = new NodeProperties();
			Utils.populateCommonProperties(t, nodeProperties);
			if (!Utils.hasCommonProperty(nodeProperties)) {
				return null;
			}
			String amenity = ((TagCollection) t.getTags()).buildMap().get("amenity");
			String highWay = ((TagCollection) t.getTags()).buildMap().get("highway");
			nodeProperties.setAmenity(amenity);
			nodeProperties.setHighWay(highWay);
			return nodeProperties;
		}
		return null;
	}
}
