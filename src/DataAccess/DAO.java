package DataAccess;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public interface DAO<T> {

	void insert(T entity);
	void update(T entity);
	void delete(T entity);
	void insert(Collection<T> entities);
	void update(Collection<T> entities);
	void delete(Collection<T> entities);
	Set<T> getAll();
	
	
}
