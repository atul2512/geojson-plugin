package com.chandan.osmosis.plugin.geojson.model;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonDeserialize(using = Feature.FeatureDeserializer.class)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Feature<T extends Geometry> extends Geometry {

	private final T geometry;

	private final CommonProperties<T> properties;

	public Feature(T geometry, CommonProperties<T> properties) {
		super(GeoJsonModelType.FEATURE);
		this.geometry = geometry;
		this.properties = properties;
	}

	public static class FeatureDeserializer extends JsonDeserializer<Feature<Geometry>> {

		@SuppressWarnings("unchecked")
		@Override
		public Feature<Geometry> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException,
				JsonProcessingException {
			Feature<?> feature = null;
			ObjectCodec objectCodec = p.getCodec();
			JsonNode node = objectCodec.readTree(p);
			if (GeoJsonModelType.findByValue(node.get("type").asText()) == GeoJsonModelType.FEATURE) {
				JsonNode geometryJson = node.get("geometry");
				if (geometryJson.isObject()) {
					GeoJsonModelType type = GeoJsonModelType.findByValue(geometryJson.get("type").asText());
					switch (type) {
					case POINT:
						Point point = objectCodec.treeToValue(node.get("geometry"), Point.class);
						if (node.get("properties") != null && !node.get("properties").isNull()) {
							NodeProperties nodeProperties = objectCodec.treeToValue(node.get("properties"),
									NodeProperties.class);
							feature = new Feature<Point>(point, nodeProperties);
						} else {
							feature = new Feature<>(point, null);
						}

						break;
					case LINESTRING:
						LineString lineString = objectCodec.treeToValue(node.get("geometry"), LineString.class);
						if (node.get("properties") != null && !node.get("properties").isNull()) {
							WayProperties wayProperties = objectCodec.treeToValue(node.get("properties"),
									WayProperties.class);
							feature = new Feature<LineString>(lineString, wayProperties);
						} else {
							feature = new Feature<>(lineString, null);
						}
						break;
					case POLYGON:
						
						break;
					default:
						break;
					}
				}
			}
			return (Feature<Geometry>) feature;
		}
	}
}
