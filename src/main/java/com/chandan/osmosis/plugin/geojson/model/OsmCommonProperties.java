package com.chandan.osmosis.plugin.geojson.model;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OsmCommonProperties {
	
	@JsonSerialize
	private String osmId;
	@JsonSerialize
	private String name;
	@JsonSerialize
	private Map<String, String> tags;
}
