package tz.io.pdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sqlite.SQLiteConfig.SynchronousMode;

import tz.io.pdb.api.DBAPIDriver;
import tz.io.pdb.api.DBDriver;
import tz.io.pdb.api.base.DBExtendData;
import tz.io.pdb.api.base.DBStatement;
import tz.io.pdb.api.functions.DBExtender;
import tz.io.pdb.api.statements.DBCreate;
import tz.io.pdb.api.statements.DBDelete;
import tz.io.pdb.api.statements.DBInfo;
import tz.io.pdb.api.statements.DBInsert;
import tz.io.pdb.api.statements.DBOperation;
import tz.io.pdb.api.statements.DBQuery;
import tz.io.pdb.api.statements.DBSelect;
import tz.io.pdb.api.statements.DBUpdate;
import tz.sys.Sys;
import tz.sys.reflect.Reflect;
import tz.sys.reflect.ReflectUtil;

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
		ReflectUtil.trigger("DB");
	}
	
	/**
	 * Extend a query with all extendern.
	 * @param statement - the query
	 */
	public static void extend(DBStatement statement) {
		for (DBExtendData data : statement.extend()) {
			if (!data.isLoaded()) {
				data.loaded(true);
				DBExtender extender = DB.extender(data.extend());
				if (extender == null) {
					Sys.error("The extender [0] is not defined!", data.extend());
				} else {
					extender.extend(statement.type(), statement, data);
				}
			}
		}
	}
	
	/**
	 * Get the db extender from name
	 * @param extend - the name of the extender
	 * @return DBExtender
	 */
	public static DBExtender extender(String extend) {
		for (DBExtender extender : DB.extender) {
			if (extender.name().equals(extend)) {
				return extender;
			}
		}
		return null;
	}
	
	/**
	 * Add an db extender
	 * @param extender - the extender
	 */
	public static void addExtender(DBExtender extender) {
		DB.extender.add(extender);
	}
	
	/**
	 * Create a db definition
	 * @param name - the name of the db definition
	 * @param host - the host of db
	 * @param user - the user to login on db
	 * @param password - the password for the user
	 * @param driver - the driver for this connection
	 */
	public static void create(String name, String host, String user, String password, DBDriver driver) {
		if (DB.dbs == null) {
			DB.dbs = new HashMap<String, DB>();
		}
		DB.dbs.put(name, new DB(host, user, password, driver));
	}
	
	/**
	 * Create a db definition
	 * @param name - the name of the db definition
	 * @param host - the host of db
	 * @param user - the user to login on db
	 * @param password - the password for the user
	 * @param driver - the driver name for this connection
	 */
	public static void create(String name, String host, String user, String password, String driver) {
		ReflectUtil util = ReflectUtil.annotation(DBAPIDriver.class);
		for (Reflect r : util.reflects()) {
			DBAPIDriver a = r.annotation(DBAPIDriver.class);
			if (a.name().equals(driver)) {
				DBDriver d = r.instantiate().getReflect();
				DB.create(name, host, user, password, d);
				return;
			}
		}
		Sys.warn("DB driver [0] not found!", driver);
	}
	
	/**
	 * Get a db definition.
	 * @param name - name of the definition
	 * @return DB
	 */
	public static DB get(String name) {
		try {
			return DB.dbs.get(name);
		} catch (NullPointerException e) {
			Sys.error("No connection has been established!");
		}
		return null;
	}
	
	/**
	 * Use for:
	 * 	- Select Query
	 * @return DBSelect
	 */
	public static DBSelect select() {
		return DB.select(DB.DEFAULT_DB);
	}
	
	/**
	 * Use for:
	 * 	- Select Query
	 * @param db - db name
	 * @return DBSelect
	 */
	public static DBSelect select(String db) {
		return DB.get(db).dbDriver().select();
	}
	
	/**
	 * Use for:
	 * 	- Table Create Query
	 * @return DBCreate
	 */
	public static DBCreate table() {
		return DB.table(DB.DEFAULT_DB);
	}
	
	/**
	 * Use for:
	 * 	- Table Create Query
	 * @param db - db name
	 * @return DBCreate
	 */
	public static DBCreate table(String db) {
		return DB.get(db).dbDriver().create();
	}
	
	/**
	 * Use for:
	 * 	 - Insert Query
	 * @return DBInsert
	 */
	public static DBInsert insert() {
		return DB.insert(DB.DEFAULT_DB);
	}
	
	/**
	 * Use for:
	 * 	- Insert Query
	 * @param db - db name
	 * @return DBInsert
	 */
	public static DBInsert insert(String db) {
		return DB.get(db).dbDriver().insert();
	}
	
	/**
	 * Use for:
	 *  - Update Query
	 * @return DBUpdate
	 */
	public static DBUpdate update() {
		return DB.update(DB.DEFAULT_DB);
	}
	
	/**
	 * Use for:
	 *  - Update Query
	 * @param db - db name
	 * @return DBUpdate
	 */
	public static DBUpdate update(String db) {
		return DB.get(db).dbDriver().update();
	}
	
	/**
	 * Use for:
	 *  - Delete Query
	 * @return DBDelete
	 */
	public static DBDelete delete() {
		return DB.delete(DB.DEFAULT_DB);
	}
	
	/**
	 * Use for:
	 *  - Delete Query
	 * @param db - db name
	 * @return DBDelete
	 */
	public static DBDelete delete(String db) {
		return DB.get(db).dbDriver().delete();
	}
	
	/**
	 * Use for:
	 * 	- Operation Query
	 * 		- DROP TABLE
	 * 		- TRUNCATE TABLE
	 * @return DBOperation
	 */
	public static DBOperation operation() {
		return DB.operation(DB.DEFAULT_DB);
	}
	
	/**
	 * <strong>Not Executable Query!</strong>
	 * Use for:
	 * 	- Operation Query
	 * 		- DROP TABLE
	 * 		- TRUNCATE TABLE
	 * @param db - db name
	 * @return DBOperation
	 */
	public static DBOperation operation(String db) {
		return DB.get(db).dbDriver().operation();
	}
	
	/**
	 * <strong>Not Executable Query!</strong>
	 * Use for:
	 * 	- Info Query
	 * 		- Get Tables Name
	 * 		- Get Autoincrement State
	 * @return DBInfo
	 */
	public static DBInfo info() {
		return DB.info(DB.DEFAULT_DB);
	}
	
	/**
	 * <strong>Not Executable Query!</strong>
	 * Use for:
	 * 	- Info Query
	 * 		- Get Tables Name
	 * 		- Get Autoincrement State
	 * @param db - db name
	 * @return DBInfo
	 */
	public static DBInfo info(String db) {
		return DB.get(db).dbDriver().info();
	}
	
	/**
	 * Use for:
	 * 	- All Querys without controlling.
	 *  - Only when you know SQL!
	 * @return DBQuery
	 */
	public static DBQuery query() {
		return DB.query(DB.DEFAULT_DB);
	}
	
	/**
	 * Use for:
	 * 	- All Querys without controlling.
	 *  - Only when you know SQL!
	 * @param db - db name
	 * @return DBQuery
	 */
	public static DBQuery query(String db) {
		return DB.get(db).dbDriver().query();
	}
	
	/**
	 * Get the default loaded driver.
	 * @return DBDriver
	 */
	public static DBDriver driver() {
		return DB.driver(DB.DEFAULT_DB);
	}
	
	/**
	 * Get the loaded driver of db.
	 * @param db - db name
	 * @return DBDriver
	 */
	public static DBDriver driver(String db) {
		try {
			return DB.get(db).dbDriver();
		} catch (NullPointerException e) {
			Sys.error("No connection with the name [0] has been established!", db);
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
