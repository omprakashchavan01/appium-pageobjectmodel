package com.qa.pages;

import com.qa.BaseTest;
import com.qa.MenuPage;
import com.qa.utils.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductDetailsPage extends MenuPage {
	TestUtils utils = new TestUtils();
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]\n" + 
			"") 
	@iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[1]")
	private MobileElement SLBTitle;
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]"
			+ "") 
	@iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[2]")
	private MobileElement SLBTxt;
	
//	@AndroidFindBy (accessibility = "test-Price") private MobileElement SLBPrice;
	
	@AndroidFindBy (accessibility = "test-BACK TO PRODUCTS") 
	@iOSXCUITFindBy (id = "test-BACK TO PRODUCTS")
	private MobileElement backToProductsBtn;
	
	@iOSXCUITFindBy (id = "test-ADD TO CART") private MobileElement addToCartBtn;

public String getSLBTitle() {
	String title = getText(SLBTitle);
	utils.log().info("title is - " + title);
	return title;
}

public String getSLBTxt() {
	String txt = getText(SLBTxt);
	utils.log().info("txt is - " + txt);
	return txt;
}

	/*
	 * public String getSLBPrice() { String price = getText(SLBPrice);
	 * utils.log("price is - " + price); return price; }
	 */

public String scrollToSLBPriceAndGetSLBPrice() {
	return getText(scrollToElement());
}

public void scrollPage() {
	iOSScrollToElement();
}

public Boolean isAddToCartBtnDisplayed() {
	return addToCartBtn.isDisplayed();
}

public ProductsPage pressBackToProductsBtn() {
	utils.log().info("navigate back to products page");
	click(backToProductsBtn);
	return new ProductsPage();
}

}
