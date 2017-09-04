package com.cargoseller.tests.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Credentials {
	static Properties prop = new Properties();

	public static String getPassword(String username) {
		InputStream input = null;
		try {
			input = new FileInputStream("Credentials.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String password = "";

		if (input != null) {
			try {
				prop.load(input);
				password = prop.getProperty(username);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return password;
	}
}
