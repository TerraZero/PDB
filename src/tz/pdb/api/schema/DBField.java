package tz.pdb.api.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DBField {

	public String name() default "";

	public String type();
	
	public String size() default "medium";
	
	public boolean ident() default false;
	
}
