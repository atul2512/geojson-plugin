package com.chandan.osmosis.plugin.geojson.converter;

import org.openstreetmap.osmosis.core.domain.v0_6.Entity;

import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.Geometry;

public abstract class OsmToFeatureConverter<T extends Entity, U extends Geometry> {

	public abstract Feature<U> getGeojsonModel(T t);

	public abstract boolean isValid(Feature<U> feature);

	public abstract void persistGeoJsonModelToCache(long osmId, Feature<U> feature);

	public abstract boolean shouldBeEmitted(Feature<U> feature);
}
