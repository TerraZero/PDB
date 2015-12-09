package tz.io.pdb.entity;

public class EntityReferenceProp<entity extends Entity> implements EntityProp<entity> {

	@Override
	public String type() {
		return "reference";
	}

	@Override
	public String name() {
		return null;
	}

	@Override
	public Entity host() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loading() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSaving() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSaving() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entity set(entity set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public entity get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityProp<entity> host(Entity host) {
		// TODO Auto-generated method stub
		return null;
	}

}
