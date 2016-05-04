package com.chandan.osmosis.plugin.geojson.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by chandan on 5/4/16.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Polygon extends Geometry {

    private List<List<Coordinate>> coordinates;

    public Polygon() {
        super(GeoJsonModelType.POLYGON);
    }
}
