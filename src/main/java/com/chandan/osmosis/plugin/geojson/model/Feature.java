package com.chandan.osmosis.plugin.geojson.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Setter
@Getter
public class Feature<T extends Geometry> extends Geometry {

	@JsonSerialize
	private T geometry;
	@JsonSerialize
	private Properties properties;
	
	public Feature() {
		super(GeoJsonModelType.FEATURE);
	}
}
