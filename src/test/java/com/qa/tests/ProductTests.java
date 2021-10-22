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
		  closeApp();
		  launchApp();
	  }

	  @AfterClass
	  public void afterClass() {
	  }
	  
	  @BeforeMethod
	  public void beforeMethod(Method m) {
		  utils.log().info("\n" + "****** starting test:" + m.getName() + "******" + "\n");
		  loginPage = new LoginPage();		  
		  productsPage = loginPage.login(loginUsers.getJSONObject("validUser").getString("username"), 
				  loginUsers.getJSONObject("validUser").getString("password"));
	  }

	  @AfterMethod
	  public void afterMethod() {
		  settingsPage = productsPage.pressSettingsBtn();
		  loginPage = settingsPage.pressLogoutBtn();
	  }
	  
	  @Test
	  public void validateProductOnProductsPage() {
		  SoftAssert sa = new SoftAssert();
		  
		  String SLBTitle = productsPage.getSLBTitle();
		  sa.assertEquals(SLBTitle, getStrings().get("products_page_slb_title"));
		  
		  String SLBPrice = productsPage.getSLBPrice();
		  sa.assertEquals(SLBPrice, getStrings().get("products_page_slb_price"));
		  
		  sa.assertAll();
	  }
	  
	  @Test
	  public void validateProductOnProductDetailsPage() {
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
