package tz.io.pdb.entity;

import java.util.Map;

public abstract class Entity {
	
	private String type;
	private int id;
	private Map<String, EntityProp<?>> props;
	
	public Entity(String type, int id) {
		this.type = type;
		this.id = id;
	}

	public String type() {
		return this.type;
	}
	
	public int id() {
		return this.id;
	}
	
	@SuppressWarnings("unchecked")
	public<type> EntityProp<type> get(String name) {
		return (EntityProp<type>)this.props.get(name);
	}
	
	
	
	public abstract Entity save();
	
	public abstract Entity load();
	
}
