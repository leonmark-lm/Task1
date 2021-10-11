package common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import DataAccess.UserFileDAO;
import Entities.Role;
import Entities.User;
import Service.IService;
import Service.UserService;

public class ConsoleApp implements IApplication{
	
	
	private final int MIN_PHONES_NUMBERS = 1; 
	private final int MAX_PHONES_NUMBERS = 3;
	private final String STOP_LABEL = "quit";
	
	private UserService userService;
	private Set<Role> roles;
	private Scanner input;
	
	public ConsoleApp(String fileAddress) {
		userService = new UserService(new UserFileDAO(fileAddress));
		
		roles = new LinkedHashSet<Role>();
		
		roles.add(new Role("ADMIN", 2, true));
		roles.add(new Role("SUPER_ADMIN", 3, false));
		roles.add(new Role("USER", 1, true));
		roles.add(new Role("COSTUMER", 1, true));
		roles.add(new Role("PROVIDER", 2, true));
		
		input = new Scanner(System.in);
	}
	
	@Override
	public void start() {
		while (true) {
			System.out.println("�������� ������������ - 1");
			System.out.println("������������� ������������ - 2");
			System.out.println("������� ������������ - 3");
			System.out.println("����������� ������������� - 4");
			System.out.println("����������� ������������ �� email - 5");
			System.out.print("������� ����� �������: ");
			
			String strCommandNum = input.nextLine();
			int commandNum = -1;
			
			if (isDigit(strCommandNum)) {
				commandNum = Integer.parseInt(strCommandNum);
			}
			
			switch(commandNum) {
				case(1):
					createUser();
					break;
				case(2):
					editUser();
					break;
				case(3):
					deleteUser();
					break;
				case(4):
					displayAll();
					break;
				case (5):
					displayUserByEmail();
					break;
				default:
					System.out.println("����� ������� ���.");
					break;
			}
			
			System.out.println();
		}
	}
	
	private void createUser() {
		try {
			
			User user = new User();
			String inputData = null;
			
			System.out.print("������� ���: ");
			inputData = input.nextLine();
			user.setFirstName(inputData);
			System.out.print("������� �������: ");
			inputData = input.nextLine();
			user.setLastName(inputData);
			System.out.print("������� email: ");
			inputData = input.nextLine();
			user.setEmail(inputData);
			
			for (Role role : roles) {
				for (Role rl : roles) {
					System.out.println(rl.toString());
				}
				System.out.print("�������� ���� (" + STOP_LABEL + " - ���������): ");
				
				inputData = input.nextLine();
				
				if (!inputData.equals(STOP_LABEL)) {
					for (Role rl : roles) {
						if (rl.getName().equals(inputData)) {
							user.addRole(rl);
							System.out.println("���� ���������.");
						}
					}
				}else {
					break;
				}
			}
			
			for (int i = 0; i < MAX_PHONES_NUMBERS; i++) {
				System.out.print("������� ����� �������� (" + STOP_LABEL + " - ���������): ");
				inputData = input.nextLine();
				
				if (!inputData.equals(STOP_LABEL)) {
					user.addPhoneNumber(inputData);
				}else if (inputData.equals(STOP_LABEL) && i < MIN_PHONES_NUMBERS) {
					System.out.println("�� �� ��������� ����������� ���-�� �������.");
					i--;
				}
				else {
					break;
				}
			}
			
			System.out.println();
			
			userService.add(user);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	private void editUser() {
		try {
			
			System.out.print("������� email: ");
			User user = userService.getByEmail(input.nextLine());
			
			if (user != null) {
			
				System.out.println(user.toString());
				
				String inputData = null;
				
				System.out.print("������� ����� ���: ");
				inputData = input.nextLine();
				user.setFirstName(inputData);
				System.out.print("������� ����� �������: ");
				inputData = input.nextLine();
				user.setLastName(inputData);
				System.out.print("������� ����� email: ");
				inputData = input.nextLine();
				user.setEmail(inputData);
				
				user.setRoles(new LinkedHashSet<Role>());
				
				for (Role role : roles) {
					for (Role rl : roles) {
						System.out.println(rl.toString());
					}
					System.out.print("�������� ���� (" + STOP_LABEL + " - ���������): ");
					
					inputData = input.nextLine();
					
					if (!inputData.equals(STOP_LABEL)) {
						for (Role rl : roles) {
							if (rl.getName().equals(inputData)) {
								user.addRole(rl);
								System.out.println("���� ���������.");
							}
						}
					}else {
						break;
					}
				}
				
				user.setPhonesNumbers(new LinkedHashSet<String>());
				
				for (int i = 0; i < MAX_PHONES_NUMBERS; i++) {
					System.out.print("������� ����� �������� (" + STOP_LABEL + " - ���������): ");
					inputData = input.nextLine();
					
					if (!inputData.equals(STOP_LABEL)) {
						user.addPhoneNumber(inputData);
					}else if (inputData.equals(STOP_LABEL) && i < MIN_PHONES_NUMBERS) {
						System.out.println("�� �� ��������� ����������� ���-�� �������.");
						i--;
					}
					else {
						break;
					}
				}
				
				System.out.println();
				
				userService.edit(user);
			}else {
				System.out.println("������������ � ������ email ���.");
			}
			
			System.out.println();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	private void deleteUser() {
		System.out.print("������� email: ");
		User user = userService.getByEmail(input.nextLine());
		
		if (user != null) {
			userService.delete(user);
			System.out.println("������������ ������.");
		}else {
			System.out.println("������ ������������ ���.");
		}
		
		
		System.out.println();
		
	}
	
	private void displayAll() {
		System.out.println("-----------Users------------");
		for (User user : userService.getAll()) {
			System.out.println(user.toString());
		}
	}
	
	private void displayUserByEmail() {
		System.out.print("������� email: ");
		User user = userService.getByEmail(input.nextLine());
		if (user != null) {
			System.out.println(user.toString());
			System.out.println();
		}else {
			System.out.println("������ ������������ ���.");
		}
	}
	
	private Role getRoleByName(String name) {
		for (Role role : roles) {
			if (role.getName().equals(name)) {
				return role;
			}
		}
		return null;
	}
	
	
	private static boolean isDigit(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	
	
	
}
