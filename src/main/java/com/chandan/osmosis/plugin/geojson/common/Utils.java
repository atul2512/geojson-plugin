package com.chandan.osmosis.plugin.geojson.common;

import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.TagCollection;

import com.chandan.osmosis.plugin.geojson.model.CommonProperties;
import com.chandan.osmosis.plugin.geojson.model.Geometry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static String jsonEncode(Object o) throws JsonProcessingException {
		if (o == null)
			return "";
		return objectMapper.writeValueAsString(o);
	}

	@SuppressWarnings("unchecked")
	public static <T> T jsonDecode(byte[] data, TypeReference<T> t) throws Exception {

		if (data == null) {
			return null;
		}
		return (T) objectMapper.readValue(data, t);
	}

	public static <T extends Entity, U extends Geometry> void populateCommonProperties(T t,
			CommonProperties<U> properties) {
		if ((t.getTags() != null && t.getTags().size() > 0)) {
			properties.setOsmId(t.getId());
			String name = ((TagCollection) t.getTags()).buildMap().get("name");
			if (name != null && name.length() > 0) {
				properties.setName(name);
			}
		}
	}

	public static <T extends Geometry> boolean hasCommonProperty(CommonProperties<T> properties) {
		if (properties.getOsmId() > 0 || (properties.getName() != null && properties.getName().length() > 0)) {
			return true;
		}
		return false;
	}
}
