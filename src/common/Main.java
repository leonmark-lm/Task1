package common;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import DataAccess.DAO;
import DataAccess.UserFileDAO;
import Entities.Role;
import Entities.User;

public class Main {

	private final static String FILE_ADDRESS = "C:\\\\Users\\\\LeonMark\\\\eclipse-workspace\\\\Task1\\\\data.txt";
	public static void main(String[] args) {
		IApplication app = new ConsoleApp(FILE_ADDRESS);
		
		app.start();
		

	}

}
