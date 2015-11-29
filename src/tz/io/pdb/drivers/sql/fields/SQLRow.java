package tz.io.pdb.drivers.sql.fields;

import tz.io.pdb.api.fields.DBRow;
import tz.io.pdb.api.functions.DBPlaceholder;

public class SQLRow implements DBRow {
	
	private String[] cols;
	private String[] values;
	
	public SQLRow(String... values) {
		this.values = values;
	}

	@Override
	public String built() {
		String string = "(";
		for (String value : this.values) {
			string += DBPlaceholder.renderValue(value) + ", ";
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
