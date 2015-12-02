package tz.pdb;

import java.util.List;

import tz.pdb.api.DBDriver;
import tz.sys.reflect.ReflectUtil;

public class RefTest {

	public static void main(String[] args) {
		ReflectUtil util = ReflectUtil.implement(DBDriver.class);
		List<DBDriver> dr = util.create();
		for (DBDriver d : dr) {
			System.out.println(d.name());
		}
	}
	
}
