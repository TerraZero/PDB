package tz.io.pdb.entity;

public interface EntityProp<type> {

	public String type();
	
	public String name();
	
	
	
	public Entity set(type set);
	
	public type get();
	
	
	
	public Entity host();
	
	public EntityProp<type> host(Entity host);
	
	
	
	public void loading();
	
	public void preSaving();
	
	public void postSaving();
	
}
