package tz.pdb.api.statments;

/**
 * 
 * @author terrazero
 * @created May 11, 2015
 * 
 * @file DBField.java
 * @project PDB
 * @identifier tz.pdb.api.statments
 *
 */
public interface DBField {

	public String name();
	
	public String type();
	
	public int size();
	
	public String[] additionals();
	
}
