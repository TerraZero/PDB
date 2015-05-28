package tz.pdb;

import java.util.Map;
import java.util.Map.Entry;

import tz.core.logger.Log;

/**
 * 
 * @author terrazero
 * @created May 11, 2015
 * 
 * @file SQLitePlaceholder.java
 * @project PDB
 * @identifier tz.pdb.drivers.sqlite
 *
 */
public class SQLPlaceholder {
	
	public static SQLPlaceholderExecuter built(String subject, String key, String value) {
		return new SQLPlaceholderExecuter(subject).execute(key, value);
	}

	public static String generic(String subject, Map<String, String> placeholders, String ident) {
		for(Entry<String, String> entry : placeholders.entrySet()) {
			String value = null;
			char prefix = entry.getKey().charAt(0);
			
			switch (prefix) {
				case '!' :
					value = entry.getValue();
					break;
				case ':' :
					value = "'" + entry.getValue() + "'";
					break;
				case '#' :
					try {
						int test = Integer.parseInt(entry.getValue());
						value = test + "";
					} catch (NumberFormatException e) {
						Log.warning(ident + Log.IDENT_SEPERATOR + "SQLPlaceholder", "Value [0] can not be converted into integer.", entry.getValue());
					}
					break;
				default :
					Log.warning(ident + Log.IDENT_SEPERATOR + "SQLPlaceholder", "Placeholder [0] have an unknown prefix [1].", entry.getKey(), prefix + "");
					break;
			}
			if (value != null) {
				subject = subject.replaceAll(entry.getKey(), value);
			}
		}
		return subject;
	}
	
	public static String renderValue(String value, String ident) {
		return SQLPlaceholder.renderValue(value, ident, false);
	}
	
	public static String renderValue(String value, String ident, boolean strict) {
		char prefix = value.charAt(0);
		
		switch (prefix) {
			case '!' :
				return value.substring(1);
			case ':' :
				return "'" + value.substring(1) + "'";
			case '#' :
				try {
					int test = Integer.parseInt(value.substring(1));
					return test + "";
				} catch (NumberFormatException e) {
					Log.warning(ident + Log.IDENT_SEPERATOR + "SQLPlaceholder", "Value [0] can not be converted into integer.", value);
				}
			default : 
				if (!strict) {
					return value;
				}
				break;
		}
		Log.warning(ident + Log.IDENT_SEPERATOR + "SQLPlaceholder", "Value [0] have an unknown prefix [1].", value, prefix + "");
		return null;
	}
	
	public static String maskValue(String value) {
		return ":" + value;
	}
	
	public static String maskValue(int value) {
		return "#" + value;
	}
	
	public static String toValue(String[] values) {
		String s = "";
		for (String value : values) {
			s += ", '" + value + "'";
		}
		return "(" + s.substring(2) + ")";
	}
	
	public static String toValue(int[] values) {
		String s = "";
		for (int value : values) {
			s += ", " + value;
		}
		return "(" + s.substring(2) + ")";
	}
	
}
