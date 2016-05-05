package com.chandan.osmosis.plugin.geojson;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openstreetmap.osmosis.core.Osmosis;

import java.io.File;

/**
 * Created by chandan on 5/4/16.
 */
public class TestGeoJsonWriterPlugin {

    @Test
    public void testGeoJsonWriter() {
        String osmXmlPath = TestGeoJsonWriterPlugin.class.getClassLoader().getResource("map.osm").getPath();
        String rootPath = osmXmlPath.substring(0, osmXmlPath.lastIndexOf('/'));
        String directoryForCache = rootPath + "/cache";
        String geoJsonFile = rootPath + "/map.json";

        Osmosis.run(new String[]{
        		"-plugin",
        		"com.chandan.osmosis.plugin.geojson.GeoJsonPluginLoader",
                "--read-xml",
                osmXmlPath,
                "--geojson-plugin",
                "geojsonFile=" + geoJsonFile,
                "directoryForCache=" + directoryForCache
        });
    }
}
