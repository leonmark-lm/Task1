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
			throw new Exception("���������� �������������!");
		}
		
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws NullPointerException {
		if (firstName != null) {
			this.firstName = firstName;
		}else {
			throw new NullPointerException("������� ������!");
		}
		
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws NullPointerException {
		if (lastName != null) {
			this.lastName = lastName;
		}else {
			throw new NullPointerException("������� ������!");
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
				throw new Exception("���������� email!");
			}
		}else {
			throw new NullPointerException("������� ������!");
		}
		
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		if (roles != null) {
			this.roles = roles;
		}else {
			throw new NullPointerException("������� �������� ������ �����!");
		}
	}

	public void addRole(Role role) throws NullPointerException, Exception {
		if (role != null) {
			if (role.isCombinable()) {
				for (Role rl : roles) {
					if (!rl.isCombinable()) {
						throw new Exception("������ �������� ����, �.� ������������ ��� ����� ������������ � ������� ����.");
					}
					if (role.getLevel() == rl.getLevel()) {
						throw new Exception("������ �������� ��� ����, �.� ������������ ��� ����� ���� � ����� �������.");
					}
				}
				this.roles.add(role);
			}else if (!role.isCombinable() && roles.size() == 0) {
				this.roles.add(role);
			}else {
				throw new Exception("������ �������� ��� ����, �.� ��� �����������������.");
			}
			
		}else {
			throw new NullPointerException("������� �������� ����!");
		}
	
	}
	
	
	public Set<String> getPhonesNumbers() {
		return phonesNumbers;
	}
	
	public void setPhonesNumbers(Set<String> phonesNumbers) {
		if (phonesNumbers != null) {
			this.phonesNumbers = phonesNumbers;
		}else {
			throw new NullPointerException("������� �������� ������ ���������!");
		}
	}
	
	public void addPhoneNumber(String phoneNumber) throws Exception, NullPointerException{
		
		if (phoneNumber != null) {
			if (phoneNumber.matches("^375\s[0-9]{9}$")){
				this.phonesNumbers.add(phoneNumber);
			}else {
				throw new Exception("���������� ����� ��������!");
			}
		}else {
			throw new NullPointerException("������� ������!");
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
				"\n������������:"
						+ "\n ���: " + this.firstName
						+ "\n �������: " + this.lastName
						+ "\n Email: " + this.email
				);
		str.append("\n ����:");
		
		for (Role role : this.roles) {
			str.append("\n  " + role.toString());
		}
		
		str.append("\n ������ ���������:");
		
		for (String phoneNumber : this.phonesNumbers) {
			str.append("\n  " + phoneNumber);
		}
		
		return str.toString();
	}
	
	
}
