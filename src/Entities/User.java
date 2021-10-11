package Entities;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class User {
	
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private Set<Role> roles;
	private Set<String> phonesNumbers;

	public User() {
		roles = new LinkedHashSet<Role>();
		phonesNumbers = new LinkedHashSet<String>();
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) throws Exception {
		if (0 <= id) {
			this.id = id;
		}else {
			throw new Exception("Невалидный идентификатор!");
		}
		
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws NullPointerException {
		if (firstName != null) {
			this.firstName = firstName;
		}else {
			throw new NullPointerException("Нулевая строка!");
		}
		
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws NullPointerException {
		if (lastName != null) {
			this.lastName = lastName;
		}else {
			throw new NullPointerException("Нулевая строка!");
		}
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws Exception, NullPointerException {
		if (email != null) {
			
			if (email.matches("^[A-Za-z0-9_-]+@[A-Za-z]+\\.[A-Za-z]{2,6}$")){
				this.email = email;
			}else {
				throw new Exception("Невалидный email!");
			}
		}else {
			throw new NullPointerException("Нулевая строка!");
		}
		
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		if (roles != null) {
			this.roles = roles;
		}else {
			throw new NullPointerException("Нулевое значение списка ролей!");
		}
	}

	public void addRole(Role role) throws NullPointerException, Exception {
		if (role != null) {
			if (role.isCombinable()) {
				for (Role rl : roles) {
					if (!rl.isCombinable()) {
						throw new Exception("Нельзя добавить роль, т.к пользователь уже имеет несочитаемую с другими роль.");
					}
					if (role.getLevel() == rl.getLevel()) {
						throw new Exception("Нельзя добавить это роль, т.к пользователь уже имеет роль с таким уровнем.");
					}
				}
				this.roles.add(role);
			}else if (!role.isCombinable() && roles.size() == 0) {
				this.roles.add(role);
			}else {
				throw new Exception("Нельзя добавить эту роль, т.к она некомбинирующеяся.");
			}
			
		}else {
			throw new NullPointerException("Нулевое значение роли!");
		}
	
	}
	
	
	public Set<String> getPhonesNumbers() {
		return phonesNumbers;
	}
	
	public void setPhonesNumbers(Set<String> phonesNumbers) {
		if (phonesNumbers != null) {
			this.phonesNumbers = phonesNumbers;
		}else {
			throw new NullPointerException("Нулевое значение списка телефонов!");
		}
	}
	
	public void addPhoneNumber(String phoneNumber) throws Exception, NullPointerException{
		
		if (phoneNumber != null) {
			if (phoneNumber.matches("^375\s[0-9]{9}$")){
				this.phonesNumbers.add(phoneNumber);
			}else {
				throw new Exception("Невалидный номер телефона!");
			}
		}else {
			throw new NullPointerException("Нулевая строка!");
		}
	
	}
	
	@Override
	public int hashCode() {
		return email.hashCode();
	}
	
	@Override
    public boolean equals(Object object) {
        return object instanceof User && (this.id == ((User)object).id || this.email.equals(((User)object).email));
    }   

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(
				"\nПользователь:"
						+ "\n Имя: " + this.firstName
						+ "\n Фамилия: " + this.lastName
						+ "\n Email: " + this.email
				);
		str.append("\n Роли:");
		
		for (Role role : this.roles) {
			str.append("\n  " + role.toString());
		}
		
		str.append("\n Номера телефонов:");
		
		for (String phoneNumber : this.phonesNumbers) {
			str.append("\n  " + phoneNumber);
		}
		
		return str.toString();
	}
	
	
}
