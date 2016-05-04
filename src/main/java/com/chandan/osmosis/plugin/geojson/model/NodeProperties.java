package com.chandan.osmosis.plugin.geojson.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class NodeProperties extends CommonProperties<Point> {
	
	private String amenity;
}
