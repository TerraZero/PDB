package tz.pdb.drivers.sqlite.fields;

import tz.core.logger.Log;
import tz.pdb.api.fields.DBRow;
import tz.pdb.api.functions.DBPlaceholder;

public class SQLiteRow implements DBRow {
	
	private String[] cols;
	private String[] values;
	
	public SQLiteRow(String... values) {
		this.values = values;
	}

	@Override
	public String built() {
		String string = "(";
		for (String value : this.values) {
			string += DBPlaceholder.renderValue(value, Log.ident("DB", "Driver", "SQLite", "Row")) + ", ";
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
