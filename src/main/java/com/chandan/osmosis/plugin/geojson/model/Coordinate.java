package com.chandan.osmosis.plugin.geojson.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonFormat(shape = Shape.ARRAY)
@AllArgsConstructor
@Getter
public class Coordinate {
	
	@JsonSerialize
	private final double lng;
	@JsonSerialize
	private final double lat;
}
