package com.cargoseller.tests.tools;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import com.cargoseller.tests.browser.Browser;

public class Tools {

	public static String generateRandomNumbers(int countOfNumbers) {
		String numbers = "1234567890";
		Random rand = new Random();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < countOfNumbers; i++) {
			int randIndex = rand.nextInt(numbers.length());
			res.append(numbers.charAt(randIndex));
		}
		return res.toString();
	}
	
	public static String generateRandomLetters(int countOfLetters) {
		String aToZ = "abcdefghijklmnopqrstuvwxyz";
		Random rand = new Random();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < countOfLetters; i++) {
			int randIndex = rand.nextInt(aToZ.length());
			res.append(aToZ.charAt(randIndex));
		}
		return res.toString();
	}

	public static boolean isElementPresent(By by){
        try{
        	Browser.instance.findElement(by);
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }
}
