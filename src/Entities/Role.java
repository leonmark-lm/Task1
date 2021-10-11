package Entities;

public class Role {
	protected final int MIN_LEVEL = 1;
	
	protected String name;
	protected int level;
	protected boolean isCombinable;
	
	public Role(String name, int level, boolean isCombinable) {
		this.name = name;
		this.level = level;
		this.isCombinable = isCombinable;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) throws NullPointerException{
		if (name != null) {
			this.name = name;
		}else {
			throw new NullPointerException();
		}
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) throws Exception {
		if (MIN_LEVEL <= level) {
			this.level = level;
		}
		else {
			throw new Exception("Некорректное значение уровня роли.");
		}
	}
	
	public boolean isCombinable() {
		return isCombinable;
	}
	public void setCombinable(boolean isCombinable) {
		this.isCombinable = isCombinable;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
	    return object instanceof Role && isCombinable && ((Role)object).level != this.level;
	}
	
	@Override
	public String toString() {
		return "Роль: " + this.name + " " + this.level;
	}
	
	
}
