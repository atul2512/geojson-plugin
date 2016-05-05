package com.chandan.osmosis.plugin.geojson.converter;

import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.Map;
import com.chandan.osmosis.plugin.geojson.cache.FeaturePointCache;
import com.chandan.osmosis.plugin.geojson.common.Utils;
import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.Point;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;

/**
 * Created by chandan on 5/5/16.
 */
public class OsmNodeProcessor extends OsmEntityProcessor<Node> {

    private final FeaturePointCache featurePointCache;

    private final OutputStreamWriter writer;

    private final OsmNodeToFeaturePointConverter osmNodeToFeaturePointConverter;

    public OsmNodeProcessor(FeaturePointCache featurePointCache,
                            OutputStreamWriter writer) {
        this.featurePointCache = featurePointCache;
        this.writer = writer;
        this.osmNodeToFeaturePointConverter = new OsmNodeToFeaturePointConverter(featurePointCache);
    }

    @Override
    public void process(Node node) {
        Feature<Point> pointFeature = osmNodeToFeaturePointConverter.getGeojsonModel(node);
        if (pointFeature.getProperties() != null) {
            try {
                String json = Utils.jsonEncode(pointFeature);
                writer.write(json + "\n");
            } catch (Exception e) {
                throw new RuntimeException(MessageFormat.format("Exception in json encoding for node id {0}",
                        new Object[]{node.getId()}), e);
            }
        }
    }
}
