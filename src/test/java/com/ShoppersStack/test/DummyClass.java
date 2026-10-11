package com.ShoppersStack.test;

import org.testng.annotations.Test;

import com.ShoppersStack.base.BaseClass;

public class DummyClass extends BaseClass {
	@Test
	public void dummyTest() {
		String title = driver.getTitle();
		assert title.equals("ShoppersStack") : "Test Failed _ Title is not Match"	;
		System.out.println("Test pass and the Title is matching");
		System.out.println(title);
	}

}