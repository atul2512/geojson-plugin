package com.chandan.osmosis.plugin.geojson.converter;

import com.chandan.osmosis.plugin.geojson.cache.FeatureLinestringCache;
import com.chandan.osmosis.plugin.geojson.cache.FeaturePointCache;
import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.Polygon;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

/**
 * Created by chandan on 5/4/16.
 */
public class OsmWayToFeaturePolygonConverter extends OsmToFeatureConverter<Way, Polygon>{

    private final FeaturePointCache pointCache;

    private final FeatureLinestringCache lineStringCache;

    @Override
    public boolean isValid(Feature<Polygon> feature) {
        return false;
    }

    @Override
    public void persistGeoJsonModelToCache(long osmId, Feature<Polygon> feature) {

    }

    @Override
    public boolean shouldBeEmitted(Feature<Polygon> feature) {
        return false;
    }

    public OsmWayToFeaturePolygonConverter(FeaturePointCache pointCache, FeatureLinestringCache lineStringCache) {
        this.pointCache = pointCache;
        this.lineStringCache = lineStringCache;
    }

    @Override
    public Feature<Polygon> getGeojsonModel(Way t) {
        return null;
    }
}
