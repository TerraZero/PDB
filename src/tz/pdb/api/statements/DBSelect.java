package tz.pdb.api.statements;

import java.util.Map;

import tz.pdb.api.base.DBQuerieing;
import tz.pdb.api.fields.DBCondition;
import tz.pdb.api.fields.DBField;
import tz.pdb.api.fields.DBJoin;
import tz.pdb.api.fields.DBOrder;

/**
 * 
 * @author terrazero
 * @created Apr 8, 2015
 * 
 * @file SysSelect.java
 * @project SysSQL
 * @identifier TZ.sql.api
 *
 */
public interface DBSelect extends DBQuerieing {
	
	public static final String TYPE = "select";
	
	public default String type() {
		return DBSelect.TYPE;
	}
	
	public DBSelect selectAll();
	
	public DBSelect selectAll(String function);
	
	public boolean isSelectAll();
	
	public String selectAllFunction();

	public default DBSelect fields(String table, String... fields) {
		for (String field : fields) {
			this.field(table, field, field, null);
		}
		return this;
	}
	
	public default DBSelect field(String table, String field) {
		return this.field(table, field, field, null);
	}
	
	public default DBSelect field(String table, String field, String alias) {
		return this.field(table, field, alias, null);
	}
	
	public DBSelect field(String table, String field, String alias, String function);
	
	public DBSelect fields(DBField... fields);
	
	public DBSelect from(String table, String alias);
	
	public default DBJoin join(String type, String table, String alias, String one, String two) {
		return this.join(type, table, alias, one, two, DBCondition.EQUAL);
	}
	
	public DBJoin join(String type, String table, String alias, String one, String two, String equal);
	
	public default DBCondition where(String one, String two) {
		return this.where(one, two, DBCondition.EQUAL);
	}
	
	public DBCondition where(String one, String two, String equal);
	
	public default DBOrder order(String field) {
		return this.order(field, "ASC");
	}
	
	public DBOrder order(String field, String direction);
	
	public default DBSelect limit(int length) {
		return this.limit(0, length);
	}
	
	public DBSelect limit(int offset, int length);
	
	
	
	public boolean hasTable(String table);
	
	public String table(String table);
	
	public Map<String, String> tables();
	
	public Map<String, DBField> fields();
	
	public boolean hasField(String alias);
	
}
