package com.chandan.osmosis.plugin.geojson.model;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LineString extends Geometry {

	private List<Coordinate> coordinates = new ArrayList<Coordinate>();
	
	public LineString() {
		super(GeoJsonModelType.LINESTRING);
	}
	
	public LineString(List<Coordinate> coordinates) {
		super(GeoJsonModelType.LINESTRING);
		this.coordinates = coordinates;
	}

	public void addCoordinate(Coordinate coordinate) {
		coordinates.add(coordinate);
	}
}
