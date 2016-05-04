package com.chandan.osmosis.plugin.geojson.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Point extends Geometry {
	
	private Coordinate coordinates;
	
	public Point() {
		super(GeoJsonModelType.POINT);
	}
	
	public Point(Coordinate coordinates) {
		super(GeoJsonModelType.POINT);
		this.coordinates = coordinates;
	}
}
