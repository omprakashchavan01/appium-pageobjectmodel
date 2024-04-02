package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.InputStream;
import java.lang.reflect.Method;

public class ProductTests extends BaseTest{
	LoginPage loginPage;
	ProductsPage productsPage;
	SettingsPage settingsPage;
	ProductDetailsPage productDetailsPage;
	JSONObject loginUsers;
	TestUtils utils = new TestUtils();
	
	  @BeforeClass
	  public void beforeClass() throws Exception {
			InputStream datais = null;
		  try {
			  String dataFileName = "data/loginUsers.json";
			  datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
			  JSONTokener tokener = new JSONTokener(datais);
			  loginUsers = new JSONObject(tokener);
		  } catch(Exception e) {
			  e.printStackTrace();
			  throw e;
		  } finally {
			  if(datais != null) {
				  datais.close();
			  }
		  }
		  /*
		  To ensure each test starts with a fresh app state, closeApp() and launchApp() have been moved to the
		  @BeforeMethod section.
		  // closeApp();
		  // launchApp();
		  */
	  }

	  @AfterClass
	  public void afterClass() {
	  }
	  
	  @BeforeMethod
	  public void beforeMethod(Method m) {
		  closeApp();
		  launchApp();

		  utils.log().info("\n" + "****** starting test:" + m.getName() + "******" + "\n");

		/*
		The following commented code was moved to individual test methods to solve the problem:
		java.lang.NullPointerException ... because "com.qa.reports.ExtentReport.getTest()" returns null.

		We also moved closeApp() and launchApp() to @BeforeMethod to give clean app state to each test method.

		Explanation:

		The error occurs because the method getTest() in ExtentReport is called before createTest() has a chance to run.
		In TestNG, the @BeforeMethod code runs before the onTestStart() listener method, where the createTest() of
		Extent Report is supposed to be called. This leads to a situation where ExtentReport has not been properly set up yet,
		resulting in a null value.

		Therefore, when the login code is executed, it tries to use this uninitialized Extent Report, leading to the
		null pointer exception when calling the getTest() method. This happens during UI actions, such as click().

		// loginPage = new LoginPage();
		// productsPage = loginPage.login(loginUsers.getJSONObject("validUser").getString("username"),
		//              loginUsers.getJSONObject("validUser").getString("password"));
		*/
	  }

	  @AfterMethod
	  public void afterMethod() {
		/*
		The code below is no longer needed because closeApp() and launchApp() have been moved to @BeforeMethod:
		// settingsPage = productsPage.pressSettingsBtn();
		// loginPage = settingsPage.pressLogoutBtn();
		*/
	  }
	  
	  @Test
	  public void validateProductOnProductsPage() {
		  loginPage = new LoginPage();
		  productsPage = loginPage.login(loginUsers.getJSONObject("validUser").getString("username"),
				  loginUsers.getJSONObject("validUser").getString("password"));

		  SoftAssert sa = new SoftAssert();
		  
		  String SLBTitle = productsPage.getSLBTitle();
		  sa.assertEquals(SLBTitle, getStrings().get("products_page_slb_title"));
		  
		  String SLBPrice = productsPage.getSLBPrice();
		  sa.assertEquals(SLBPrice, getStrings().get("products_page_slb_price"));
		  
		  sa.assertAll();
	  }
	  
	  @Test
	  public void validateProductOnProductDetailsPage() {
		  loginPage = new LoginPage();
		  productsPage = loginPage.login(loginUsers.getJSONObject("validUser").getString("username"),
				  loginUsers.getJSONObject("validUser").getString("password"));

		  SoftAssert sa = new SoftAssert();
		  
		  productDetailsPage = productsPage.pressSLBTitle();
		  		  
		  String SLBTitle = productDetailsPage.getSLBTitle();
		  sa.assertEquals(SLBTitle, getStrings().get("product_details_page_slb_title"));
		  		  
		  if(getPlatform().equalsIgnoreCase("Android")) {
			  String SLBPrice = productDetailsPage.scrollToSLBPriceAndGetSLBPrice();
			  sa.assertEquals(SLBPrice, getStrings().get("product_details_page_slb_price"));  
		  }
		  if(getPlatform().equalsIgnoreCase("iOS")) {			  
			  String SLBTxt = productDetailsPage.getSLBTxt();
			  sa.assertEquals(SLBTxt, getStrings().get("product_details_page_slb_txt"));
			  
			  productDetailsPage.scrollPage();
			  sa.assertTrue(productDetailsPage.isAddToCartBtnDisplayed());  
		  }		  		  
//		  productsPage = productDetailsPage.pressBackToProductsBtn(); // -> Commented as this is causing stale element exception for the Settings icon
		  
		  sa.assertAll();
	  }
}
