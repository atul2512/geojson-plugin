package com.chandan.osmosis.plugin.geojson.converter;

import org.openstreetmap.osmosis.core.domain.v0_6.Entity;

/**
 * Created by chandan on 5/5/16.
 */
public abstract class OsmEntityProcessor<T extends Entity> {

    public abstract void process(T t);
}
