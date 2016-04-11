package com.chandan.osmosis.plugin.geojson.cache;

import java.io.File;

import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.Point;
import com.chandan.osmosis.plugin.geojson.model.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sleepycat.bind.tuple.LongBinding;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.OperationStatus;


public class FeaturePointCache implements GeojsonCache<Feature<Point>>{

	private final Database featurePointDb;
	
	public FeaturePointCache(String cacheFile) {
		EnvironmentConfig enConfig = new EnvironmentConfig();
		enConfig.setAllowCreate(true);
		Environment dbEnvironment = new Environment(new File(cacheFile), enConfig);
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		featurePointDb = dbEnvironment.openDatabase(null, "featurePointDb", dbConfig);
	}
	
	@Override
	public Feature<Point> get(long key) {
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();
		LongBinding.longToEntry(key, keyEntry);
		OperationStatus status = featurePointDb.get(null, keyEntry, dataEntry, null);
		if (status == OperationStatus.SUCCESS) {
			byte[] data = dataEntry.getData();
			if (data == null) {
				try {
					return Utils.<Feature<Point>>jsonDecode(data);
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return null;
	}

	@Override
	public void put(long key, Feature<Point> t) {
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();
		LongBinding.longToEntry(key, keyEntry);
		try {
			StringBinding.stringToEntry(Utils.jsonEncode(t), dataEntry);
			featurePointDb.put(null, keyEntry, dataEntry);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace(System.err);
		}		
	}
}
