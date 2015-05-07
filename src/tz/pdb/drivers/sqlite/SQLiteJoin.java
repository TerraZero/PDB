package tz.pdb.drivers.sqlite;

import tz.pdb.api.statments.DBJoin;

/**
 * 
 * @author terrazero
 * @created May 7, 2015
 * 
 * @file SQLiteJoin.java
 * @project PDB
 * @identifier tz.pdb.drivers.sqlite
 *
 */
public class SQLiteJoin extends SQLiteCondition implements DBJoin {

	public SQLiteJoin(String one, String two, String equal, String operation) {
		super(one, two, equal, operation);
	}

}
