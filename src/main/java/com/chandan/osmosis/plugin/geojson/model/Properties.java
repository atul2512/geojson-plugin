package com.chandan.osmosis.plugin.geojson.model;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Properties {
	
	@JsonSerialize
	private long osmId;
	@JsonSerialize
	private String name;
	@JsonSerialize
	private Long startOsmNodeId;
	@JsonSerialize
	private Long endOsmNodeId;
	@JsonSerialize
	private String highWay;
	@JsonSerialize()
	private Map<String, String> tags;
}
