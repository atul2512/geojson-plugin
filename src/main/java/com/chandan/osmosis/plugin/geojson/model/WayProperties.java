package com.chandan.osmosis.plugin.geojson.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class WayProperties extends OsmCommonProperties {

	@JsonSerialize
	private long startNodeId;
	@JsonSerialize
	private long endNodeId;
}
