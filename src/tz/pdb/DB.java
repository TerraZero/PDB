package tz.pdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tz.core.logger.Log;
import tz.pdb.api.DBDriver;
import tz.pdb.api.base.DBExtendData;
import tz.pdb.api.base.DBStatement;
import tz.pdb.api.functions.DBExtender;
import tz.pdb.api.statements.DBCreate;
import tz.pdb.api.statements.DBDelete;
import tz.pdb.api.statements.DBInfo;
import tz.pdb.api.statements.DBInsert;
import tz.pdb.api.statements.DBOperation;
import tz.pdb.api.statements.DBQuery;
import tz.pdb.api.statements.DBSelect;
import tz.pdb.api.statements.DBUpdate;

/**
 * 
 * @author terrazero
 * @created Apr 8, 2015
 * 
 * @file SysSQL.java
 * @project SysSQL
 * @identifier TZ.sql
 *
 */
public class DB {
	
	public static final String DEFAULT_DB = "default";
	
	private static Map<String, DB> dbs;
	private static List<DBExtender> extender;
	
	static {
		DB.extender = new ArrayList<DBExtender>();
	}
	
	public static void extend(DBStatement statement) {
		for (DBExtendData data : statement.extend()) {
			if (!data.isLoaded()) {
				data.loaded(true);
				DBExtender extender = DB.extender(data.extend());
				if (extender == null) {
					Log.fatal("DB", "The extender [0] is not defined!", data.extend());
				} else {
					extender.extend(statement.type(), statement, data);
				}
			}
		}
	}
	
	public static DBExtender extender(String extend) {
		for (DBExtender extender : DB.extender) {
			if (extender.name().equals(extend)) {
				return extender;
			}
		}
		return null;
	}
	
	public static void addExtender(DBExtender extender) {
		DB.extender.add(extender);
	}
	
	public static void create(String name, String host, String user, String password, DBDriver driver) {
		if (DB.dbs == null) {
			DB.dbs = new HashMap<String, DB>();
		}
		DB.dbs.put(name, new DB(host, user, password, driver));
	}
	
	public static DB get(String name) {
		try {
			return DB.dbs.get(name);
		} catch (NullPointerException e) {
			Log.fatal("DB", "No connection has been established");
		}
		return null;
	}
	
	public static DBSelect select() {
		return DB.select(DB.DEFAULT_DB);
	}
	
	public static DBSelect select(String db) {
		return DB.get(db).dbDriver().select();
	}
	
	public static DBCreate table() {
		return DB.table(DB.DEFAULT_DB);
	}
	
	public static DBCreate table(String db) {
		return DB.get(db).dbDriver().create();
	}
	
	public static DBInsert insert() {
		return DB.insert(DB.DEFAULT_DB);
	}
	
	public static DBInsert insert(String db) {
		return DB.get(db).dbDriver().insert();
	}
	
	public static DBUpdate update() {
		return DB.update(DB.DEFAULT_DB);
	}
	
	public static DBUpdate update(String db) {
		return DB.get(db).dbDriver().update();
	}
	
	public static DBDelete delete() {
		return DB.delete(DB.DEFAULT_DB);
	}
	
	public static DBDelete delete(String db) {
		return DB.get(db).dbDriver().delete();
	}
	
	public static DBOperation operation() {
		return DB.operation(DB.DEFAULT_DB);
	}

	public static DBOperation operation(String db) {
		return DB.get(db).dbDriver().operation();
	}
	
	public static DBInfo info() {
		return DB.info(DB.DEFAULT_DB);
	}
	
	public static DBInfo info(String db) {
		return DB.get(db).dbDriver().info();
	}
	
	public static DBQuery query() {
		return DB.query(DB.DEFAULT_DB);
	}
	
	public static DBQuery query(String db) {
		return DB.get(db).dbDriver().query();
	}
	
	public static DBDriver driver() {
		return DB.driver(DB.DEFAULT_DB);
	}
	
	public static DBDriver driver(String db) {
		try {
			return DB.get(db).dbDriver();
		} catch (NullPointerException e) {
			Log.fatal("DB", "No connection with the name [0] has been established", db);
		}
		return null;
	}
	
	private String host;
	private String user;
	private String pass;
	private DBDriver driver;
	
	public DB(String host, String user, String pass, DBDriver driver) {
		this.host = host;
		this.user = user;
		this.pass = pass;
		this.driver = driver;
		this.driver.connect(host, user, pass);
	}
	
	public String host() {
		return this.host;
	}
	
	public String user() {
		return this.user;
	}
	
	public String pass() {
		return this.pass;
	}
	
	public DBDriver dbDriver() {
		return this.driver;
	}
	
}
