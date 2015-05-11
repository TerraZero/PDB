package tz.pdb.api.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DBEntity {

	public String name();
	
	public String table();
	
	/**
	 * The methode that will be call when create the entity 
	 * @return methode
	 */
	public String process() default "";
	
}
