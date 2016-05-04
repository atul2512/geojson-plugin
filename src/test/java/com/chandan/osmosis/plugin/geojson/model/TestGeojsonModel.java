package com.chandan.osmosis.plugin.geojson.model;


import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.chandan.osmosis.plugin.geojson.common.Utils;
import com.fasterxml.jackson.core.type.TypeReference;

public class TestGeojsonModel {

	@Test
	public void testCoordinateSerde() throws Exception {
		Coordinate ser = new Coordinate(1.2, 2.3);
		String json = Utils.jsonEncode(ser);
		Coordinate de = Utils.jsonDecode(json.getBytes(), new TypeReference<Coordinate>() {});
		Assert.assertEquals(ser, de);
	}
	
	@Test
	public void testPointSerde() throws Exception {
		Point ser = new Point(new Coordinate(1.2, 2.3));
		String json = Utils.jsonEncode(ser);
		Point de = Utils.jsonDecode(json.getBytes(), new TypeReference<Point>() {});
		Assert.assertEquals(ser, de);
	}
	
	@Test
	public void testFeatureLinestringSerde() throws Exception {
		LineString lineString = new LineString(Arrays.asList(new Coordinate(1.2, 2.3), new Coordinate(2.3, 2.5)));
		WayProperties wayProperties = new WayProperties();
		wayProperties.setStartNodeId(2);
		wayProperties.setEndNodeId(3);
		wayProperties.setBuilding("appartment");
		wayProperties.setHighWay("trunk");
		wayProperties.setOsmId(1);
		wayProperties.setName("way");
		Feature<LineString> ser = new Feature<LineString>(lineString, wayProperties);
		String json = Utils.jsonEncode(ser);
		Feature<LineString> de = Utils.jsonDecode(json.getBytes(), new TypeReference<Feature<LineString>>() {});
		Assert.assertEquals(ser, de);
	}
	
	@Test
	public void testFeaturePointSerde() throws Exception {
		Point point = new Point(new Coordinate(77.87, 12.78));
		NodeProperties nodeProperties = new NodeProperties();
		nodeProperties.setOsmId(1);
		nodeProperties.setName("way");
		nodeProperties.setAmenity("restaurant");
		Feature<Point> ser = new Feature<Point>(point, nodeProperties);
		String json = Utils.jsonEncode(ser);
		System.out.println(json);
		Feature<Point> de = Utils.jsonDecode(json.getBytes(), new TypeReference<Feature<Point>>() {});
		System.out.println(de);
		Assert.assertEquals(ser, de);
	}
}
