package com.chandan.osmosis.plugin.geojson.converter;

import org.openstreetmap.osmosis.core.domain.v0_6.Way;

import com.chandan.osmosis.plugin.geojson.cache.FeatureLinestringCache;
import com.chandan.osmosis.plugin.geojson.cache.PointCache;
import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.LineString;
import com.chandan.osmosis.plugin.geojson.model.WayProperties;

public class OsmWayToFeatureLineStringConverter extends OsmToFeatureConverter<Way, Feature<LineString>>{
	
	private final PointCache pointCache;
	private final FeatureLinestringCache lineStringCache;

	public OsmWayToFeatureLineStringConverter(PointCache pointCache, FeatureLinestringCache lineStringCache) {
		this.pointCache = pointCache;
		this.lineStringCache = lineStringCache;
	}
	
	@Override
	public Feature<Feature<LineString>> getGeojsonModel(Way t) {
		Feature<LineString> featureLineString = new Feature<LineString>();
		// TODO Auto-generated method stub
		return null;
	}

}
