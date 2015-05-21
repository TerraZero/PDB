package tz.pdb.drivers.sqlite.fields;

import tz.pdb.api.base.DBExtendData;

/**
 * 
 * @author terrazero
 * @created May 21, 2015
 * 
 * @file SQLiteExtendData.java
 * @project PDB
 * @identifier tz.pdb.drivers.sqlite.fields
 *
 */
public class SQLiteExtendData implements DBExtendData {

	private boolean loaded;
	private String extend;
	
	public SQLiteExtendData() {
		
	}
	
	public SQLiteExtendData(String extend) {
		this.extend = extend;
	}

	/* 
	 * @see tz.pdb.api.base.DBExtendData#isLoaded()
	 */
	@Override
	public boolean isLoaded() {
		return this.loaded;
	}

	/* 
	 * @see tz.pdb.api.base.DBExtendData#loaded(boolean)
	 */
	@Override
	public DBExtendData loaded(boolean loaded) {
		this.loaded = loaded;
		return this;
	}

	/* 
	 * @see tz.pdb.api.base.DBExtendData#extend()
	 */
	@Override
	public String extend() {
		return this.extend;
	}

	/* 
	 * @see tz.pdb.api.base.DBExtendData#extend(java.lang.String)
	 */
	@Override
	public DBExtendData extend(String extend) {
		this.extend = extend;
		return this;
	}

}
