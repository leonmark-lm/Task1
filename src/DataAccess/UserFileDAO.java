package DataAccess;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import Entities.Role;
import Entities.User;

public class UserFileDAO implements DAO<User>{

	private final String SEPARATOR = "/"; 
	private final String STOP_MARKER = "-";
	
	private String fileAddress;
	
	public UserFileDAO(String fileAddress) {
		
		this.fileAddress = fileAddress;
	}
	
	@Override
	public void insert(User entity) {
		Set<User> users = getAll();
		users.add(entity);
		writeUsers(users, false);
	}

	@Override
	public void update(User entity) {
		Set<User> users = getAll();
		Set<User> newUsers = new LinkedHashSet();
		
		for (User user : users) {
			
			if (user.getId() == entity.getId()) {
				newUsers.add(entity);
			}else {
				newUsers.add(user);
			}
		}
		
		writeUsers(newUsers, false);
	}
	
	@Override
	public void delete(User entity) {
		Set<User> users = (Set<User>) getAll();
		users.remove(entity);
		
		writeUsers(users, false);
		
	}
	

	@Override
	public void insert(Collection<User> entities) {
		Set<User> users = getAll();
		users.addAll(entities);
		writeUsers(users, false);
	}

	@Override
	public void update(Collection<User> entities) {
		Set<User> users = getAll();
		
		for (User user : users) {
			for (User updatedUser : entities) {
				if (user.getId() == updatedUser.getId()) {
					
					user = updatedUser;
					break;
				}
				
			}
		}
		
		writeUsers(users, false);
		
	}

	@Override
	public void delete(Collection<User> entities) {
		Set<User> users = getAll();
		
		users.removeAll(entities);
		
		writeUsers(users, false);
		
	}
	
	private void writeUsers(Set<User> users, boolean append) {
		
		try (DataOutputStream output = new DataOutputStream(new FileOutputStream(fileAddress, append))){
			
			for (User user : users) {
				output.writeBytes(SEPARATOR);
					
				output.writeBytes(String.valueOf(user.getId()));
				output.writeBytes(SEPARATOR);
				output.writeBytes(user.getFirstName());
				output.writeBytes(SEPARATOR);
				output.writeBytes(user.getLastName());
				output.writeBytes(SEPARATOR);
				output.writeBytes(user.getEmail());
				output.writeBytes(SEPARATOR);
				
				for (Role role : user.getRoles()) {
					output.writeBytes(role.getName());
					output.writeBytes(SEPARATOR);
					output.writeBytes(String.valueOf(role.getLevel()));
					output.writeBytes(SEPARATOR);
					output.writeBytes(String.valueOf(role.isCombinable()));
					output.writeBytes(SEPARATOR);
				}
				
				output.writeBytes(STOP_MARKER);
				output.writeBytes(SEPARATOR);
				
				for (String phoneNumber : user.getPhonesNumbers()) {
					output.writeBytes(phoneNumber);
					output.writeBytes(SEPARATOR);
				}
				
				output.writeBytes(STOP_MARKER);
				
				
				
			}
			
			output.close();
			
			
		} catch (IOException e) {
			new RuntimeException();
		}
	}
	
	private String readUsers() {
		String content = null;
		try (DataInputStream input = new DataInputStream(new FileInputStream(fileAddress))){
			content = input.readLine();
			
			input.close();
		}catch (Exception e) {
			throw new RuntimeException();
		}
		
		return content;
	}
	

	
	@Override
	public Set<User> getAll(){

		Set<User> users = null;
		
		users = new LinkedHashSet<User>();
        User user = null;
        
        String usersData = readUsers();
        
        if (usersData != null) {
	        String[] items = usersData.split(SEPARATOR);
	        
			for (int i = 1; i < items.length; i++) {
				
				try {
					user = new User();
					
					user.setId(Integer.parseInt(items[i]));
					
					user.setFirstName(items[i + 1]);
				
					user.setLastName(items[i + 2]);
					
					user.setEmail(items[i + 3]);
					
					i = i + 4;
					
					for (int j = i; j < items.length; j+=3) {
						if (!items[j].equals(STOP_MARKER)) {
							user.addRole(new Role(items[j], Integer.parseInt(items[j + 1]), Boolean.getBoolean(items[j + 2])));
						}else {
							i = j + 1;
							break;
						}
					}
					
					for (int j = i; j < items.length; j++) {
						if (!items[j].equals(STOP_MARKER)) {
							user.addPhoneNumber(items[j]);
						}else {
							i = j;
							break;
						}
					}
					
					users.add(user);
				}
				catch(Exception ex){
					throw new RuntimeException();
				}
			}
        }
        

		return users;
		
	}

}
