package tz.pdb.test;

import tz.pdb.api.schema.DBEntity;
import tz.pdb.api.schema.DBField;

@DBEntity(name = "Test", table = "test_table")
public class TestEntity {

	@DBField(type = "id")
	public int id;
	
	@DBField(type = "VARCHAR")
	public String type;
	
	@DBField(type = "VARCHAR", size = "255")
	public String value;
	
}
