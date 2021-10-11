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
			System.out.println("Добавить пользователя - 1");
			System.out.println("Редактировать пользователя - 2");
			System.out.println("Удалить пользователя - 3");
			System.out.println("Просмотреть пользователей - 4");
			System.out.println("Просмотреть пользователя по email - 5");
			System.out.print("Введите номер команды: ");
			
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
					System.out.println("Такой команды нет.");
					break;
			}
			
			System.out.println();
		}
	}
	
	private void createUser() {
		try {
			
			User user = new User();
			String inputData = null;
			
			System.out.print("Введите имя: ");
			inputData = input.nextLine();
			user.setFirstName(inputData);
			System.out.print("Введите фамилию: ");
			inputData = input.nextLine();
			user.setLastName(inputData);
			System.out.print("Введите email: ");
			inputData = input.nextLine();
			user.setEmail(inputData);
			
			for (Role role : roles) {
				for (Role rl : roles) {
					System.out.println(rl.toString());
				}
				System.out.print("Выберите роль (" + STOP_LABEL + " - завершить): ");
				
				inputData = input.nextLine();
				
				if (!inputData.equals(STOP_LABEL)) {
					for (Role rl : roles) {
						if (rl.getName().equals(inputData)) {
							user.addRole(rl);
							System.out.println("Роль добавлена.");
						}
					}
				}else {
					break;
				}
			}
			
			for (int i = 0; i < MAX_PHONES_NUMBERS; i++) {
				System.out.print("Введите номер телефона (" + STOP_LABEL + " - завершить): ");
				inputData = input.nextLine();
				
				if (!inputData.equals(STOP_LABEL)) {
					user.addPhoneNumber(inputData);
				}else if (inputData.equals(STOP_LABEL) && i < MIN_PHONES_NUMBERS) {
					System.out.println("Вы не заполнили минимальное кол-во номеров.");
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
			
			System.out.print("Введите email: ");
			User user = userService.getByEmail(input.nextLine());
			
			if (user != null) {
			
				System.out.println(user.toString());
				
				String inputData = null;
				
				System.out.print("Введите новое имя: ");
				inputData = input.nextLine();
				user.setFirstName(inputData);
				System.out.print("Введите новую фамилию: ");
				inputData = input.nextLine();
				user.setLastName(inputData);
				System.out.print("Введите новый email: ");
				inputData = input.nextLine();
				user.setEmail(inputData);
				
				user.setRoles(new LinkedHashSet<Role>());
				
				for (Role role : roles) {
					for (Role rl : roles) {
						System.out.println(rl.toString());
					}
					System.out.print("Выберите роль (" + STOP_LABEL + " - завершить): ");
					
					inputData = input.nextLine();
					
					if (!inputData.equals(STOP_LABEL)) {
						for (Role rl : roles) {
							if (rl.getName().equals(inputData)) {
								user.addRole(rl);
								System.out.println("Роль добавлена.");
							}
						}
					}else {
						break;
					}
				}
				
				user.setPhonesNumbers(new LinkedHashSet<String>());
				
				for (int i = 0; i < MAX_PHONES_NUMBERS; i++) {
					System.out.print("Введите номер телефона (" + STOP_LABEL + " - завершить): ");
					inputData = input.nextLine();
					
					if (!inputData.equals(STOP_LABEL)) {
						user.addPhoneNumber(inputData);
					}else if (inputData.equals(STOP_LABEL) && i < MIN_PHONES_NUMBERS) {
						System.out.println("Вы не заполнили минимальное кол-во номеров.");
						i--;
					}
					else {
						break;
					}
				}
				
				System.out.println();
				
				userService.edit(user);
			}else {
				System.out.println("Пользователя с данным email нет.");
			}
			
			System.out.println();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	private void deleteUser() {
		System.out.print("Введите email: ");
		User user = userService.getByEmail(input.nextLine());
		
		if (user != null) {
			userService.delete(user);
			System.out.println("Пользователь удален.");
		}else {
			System.out.println("Такого пользователя нет.");
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
		System.out.print("Введите email: ");
		User user = userService.getByEmail(input.nextLine());
		if (user != null) {
			System.out.println(user.toString());
			System.out.println();
		}else {
			System.out.println("Такого пользователя нет.");
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
