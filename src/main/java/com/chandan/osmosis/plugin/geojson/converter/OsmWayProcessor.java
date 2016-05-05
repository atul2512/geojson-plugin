package com.chandan.osmosis.plugin.geojson.converter;

import com.chandan.osmosis.plugin.geojson.cache.FeatureLinestringCache;
import com.chandan.osmosis.plugin.geojson.cache.FeaturePointCache;
import com.chandan.osmosis.plugin.geojson.common.Utils;
import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.LineString;
import com.chandan.osmosis.plugin.geojson.model.WayProperties;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

import java.io.OutputStreamWriter;
import java.text.MessageFormat;

/**
 * Created by chandan on 5/5/16.
 */
public class OsmWayProcessor extends OsmEntityProcessor<Way> {

    private final FeaturePointCache featurePointCache;

    private final FeatureLinestringCache featureLinestringCache;

    private final OutputStreamWriter writer;

    private final OsmWayToFeatureLineStringConverter osmWayToFeatureLineStringConverter;

    private final OsmWayToFeaturePolygonConverter osmWayToFeaturePolygonConverter;

    public OsmWayProcessor(FeaturePointCache featurePointCache,
                           FeatureLinestringCache featureLinestringCache,
                           OutputStreamWriter writer) {
        this.featurePointCache = featurePointCache;
        this.featureLinestringCache = featureLinestringCache;
        this.writer = writer;
        this.osmWayToFeatureLineStringConverter = new OsmWayToFeatureLineStringConverter(featurePointCache, featureLinestringCache);
        this.osmWayToFeaturePolygonConverter = new OsmWayToFeaturePolygonConverter(featurePointCache, featureLinestringCache);
    }

    @Override
    public void process(Way way) {
        Feature<LineString> lineStringFeature = osmWayToFeatureLineStringConverter.getGeojsonModel(way);
        if (((WayProperties)lineStringFeature.getProperties()).getHighWay() != null) {
            try {
                String json = Utils.jsonEncode(lineStringFeature);
                writer.write(json + "\n");
            } catch (Exception e) {
                throw new RuntimeException(MessageFormat.format("Exception in json encoding for way id {0}",
                        new Object[]{way.getId()}), e);
            }
        } else {

        }
    }
}
