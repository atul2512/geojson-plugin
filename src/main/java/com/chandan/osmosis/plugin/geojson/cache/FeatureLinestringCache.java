package com.chandan.osmosis.plugin.geojson.cache;

import com.chandan.osmosis.plugin.geojson.common.Utils;
import com.chandan.osmosis.plugin.geojson.model.Feature;
import com.chandan.osmosis.plugin.geojson.model.LineString;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sleepycat.bind.tuple.LongBinding;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.OperationStatus;

public class FeatureLinestringCache implements Cache<Feature<LineString>> {

	private Database featureLinestringCacheDb;
	private Environment dbEnv;
	
	public FeatureLinestringCache(Environment dbEnv) {
		this.dbEnv = dbEnv;
	}
	
	public void init() {
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		featureLinestringCacheDb = this.dbEnv.openDatabase(null, "featureLinestringCacheDb", dbConfig);
	}
	
	@Override
	public Feature<LineString> get(long key) {
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();
		LongBinding.longToEntry(key, keyEntry);
		OperationStatus status = featureLinestringCacheDb.get(null, keyEntry, dataEntry, null);
		if (status == OperationStatus.SUCCESS) {
			byte[] data = dataEntry.getData();
			if (data != null) {
				try {
					return Utils.<Feature<LineString>>jsonDecode(data, new TypeReference<Feature<LineString>>() {});
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return null;
	}

	@Override
	public void put(long key, Feature<LineString> t) {
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();
		LongBinding.longToEntry(key, keyEntry);
		try {
			StringBinding.stringToEntry(Utils.jsonEncode(t), dataEntry);
			featureLinestringCacheDb.put(null, keyEntry, dataEntry);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace(System.err);
		}		
	}
}
