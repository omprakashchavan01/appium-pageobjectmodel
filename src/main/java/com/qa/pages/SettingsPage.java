package com.qa.pages;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SettingsPage extends BaseTest{
	TestUtils utils = new TestUtils();
	
	@AndroidFindBy (accessibility="test-LOGOUT") 
	@iOSXCUITFindBy (id = "test-LOGOUT")
	private MobileElement logoutBtn;
	
	public LoginPage pressLogoutBtn() {
		click(logoutBtn, "press Logout button");
		return new LoginPage();
	}

}
