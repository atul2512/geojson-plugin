package com.chandan.osmosis.plugin.geojson.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Getter
@Setter
public class LineString extends Geometry {

	@JsonSerialize
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
