package Service;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import DataAccess.DAO;
import Entities.User;

public class UserService implements IService<User> {
	
	private DAO<User> dataAccess;
	
	public UserService(DAO<User> dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public void add(User entity) {
		User lastUser = getLastUser(getAll());
		
		int id = lastUser != null ? lastUser.getId() + 1 : 0;
		
		try {
			entity.setId(id);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		dataAccess.insert(entity);
	}

	@Override
	public void edit(User entity) {
		dataAccess.update(entity);
		
	}

	@Override
	public void delete(User entity) {
		dataAccess.delete(entity);
	}
	
	@Override
	public void add(Collection<User> entities) {
		User lastUser = getLastUser(getAll());
		
		int id = lastUser != null ? lastUser.getId() + 1 : 0;
		
		for (User user : entities) {
			try {
				user.setId(id);
			} catch (Exception e) {
				throw new RuntimeException();
			}
			id++;
		}
		
		dataAccess.insert(entities);
		
	}

	@Override
	public void edit(Collection<User> entities) {
		dataAccess.update(entities);
		
	}

	@Override
	public void delete(Collection<User> entities) {
		dataAccess.delete(entities);
		
	}
	
	@Override
	public Collection<User> getAll(){
		return dataAccess.getAll();
	}
	
	public User getByEmail(String email) {
		User user = null;
		for (User selectedUser : dataAccess.getAll()) {
			if (selectedUser.getEmail().equals(email)) {
				user = selectedUser;
			}
		}
		return user;
	}
	
	private User getLastUser(Collection<User> users) {
		Iterator iterator = users.iterator();
		User user = null;
		
		while (iterator.hasNext()) {
			user = (User)iterator.next();
		}
		
		return user;
	}

	
	
	
}
