package com.chandan.osmosis.plugin.geojson.model;

import lombok.Getter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Getter
public class Point extends Geometry {
	
	@JsonSerialize
	private Coordinate coordinates;
	
	public Point(Coordinate coordinates) {
		super(GeoJsonModelType.POINT);
		this.coordinates = coordinates;
	}
}
