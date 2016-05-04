package com.chandan.osmosis.plugin.geojson.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum GeoJsonModelType {

	POINT("Point"),LINESTRING("LineString"),POLYGON("Polygon"),GEOMETRY_COLLECTION("GeometryCollection"), FEATURE("Feature"),
    MULTI_POLYGON("MultiPolygon");
	
	private final String name;
	
	@JsonValue
	public String getName() {
		return name;
	}
	
	public static GeoJsonModelType findByValue(String name) {
		for (GeoJsonModelType type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		return null;
	}
}
