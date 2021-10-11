package Service;

import java.util.Collection;

public interface IService<T> {
	
	void add(T entity);
	void edit(T entity);
	void delete(T entity);
	void add(Collection<T> entities);
	void edit(Collection<T> entities);
	void delete(Collection<T> entities);
	Collection<T> getAll();
}
