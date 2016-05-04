package com.chandan.osmosis.plugin.geojson;

import org.junit.Test;
import org.openstreetmap.osmosis.core.Osmosis;

/**
 * Created by chandan on 5/4/16.
 */
public class TestGeoJsonWriterPlugin {

    @Test
    public void testGeoJsonWriter() {
        String osmXmlPath = "/Users/chandan/Downloads/map.osm";
        Osmosis.run(new String[]{
        		"-plugin",
        		"com.chandan.osmosis.plugin.geojson.GeoJsonPluginLoader",
                "--read-xml",
                osmXmlPath,
                "--geojson-plugin",
                "geojsonFile=filepath",
                "directoryForCache=directory"
        });
    }
}
