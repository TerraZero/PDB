package tz.pdb.drivers.sqlite.statements;

import tz.core.logger.Log;
import tz.pdb.SQLPlaceholder;
import tz.pdb.api.statments.DBRow;

public class SQLiteRow implements DBRow {
	
	private String[] cols;
	private String[] values;
	
	public SQLiteRow(String... values) {
		this.values = values;
	}

	@Override
	public String create() {
		String string = "(";
		for (String value : this.values) {
			string += SQLPlaceholder.renderValue(value, Log.ident("DB", "Driver", "SQLite", "Row")) + ", ";
		}
		return string.substring(0, string.length() - 2) + ")";
	}

	@Override
	public DBRow setHead(String[] cols) {
		this.cols = cols;
		return this;
	}

	@Override
	public String[] values() {
		return this.values;
	}

	@Override
	public String value(String col) {
		for (int i = 0; i < this.cols.length; i++) {
			if (this.cols[i].equals(col)) return this.values[i];
		}
		return null;
	}

}
