package com.chandan.osmosis.plugin.geojson.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public abstract class Geometry {
	
	private GeoJsonModelType type;
}
