package com.chandan.osmosis.plugin.geojson.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Geometry {
	
	private final GeoJsonModelType type;
}
