package com.chandan.osmosis.plugin.geojson.cache;

import com.chandan.osmosis.plugin.geojson.common.Utils;
import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sleepycat.bind.tuple.LongBinding;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.OperationStatus;


public class FeaturePointCache implements Cache<Feature<Point>>{

	private Environment dbEnv;
	private Database pointCacheDb;
	
	public FeaturePointCache(Environment dbEnv) {
		this.dbEnv = dbEnv;
	}

	@Override
	public void init() {
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		pointCacheDb = dbEnv.openDatabase(null, "pointCacheDb", dbConfig);
	}	
	
	@Override
	public Feature<Point> get(long key) {
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();
		LongBinding.longToEntry(key, keyEntry);
		OperationStatus status = pointCacheDb.get(null, keyEntry, dataEntry, null);
		if (status == OperationStatus.SUCCESS) {
			byte[] data = dataEntry.getData();
			if (data != null) {
				try {
					return Utils.<Feature<Point>>jsonDecode(data, new TypeReference<Feature<Point>>() {});
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
			pointCacheDb.put(null, keyEntry, dataEntry);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace(System.err);
		}		
	}

}
