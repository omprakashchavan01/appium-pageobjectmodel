package com.qa.pages;

import com.qa.MenuPage;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductsPage extends MenuPage {
	TestUtils utils = new TestUtils();
	
	@AndroidFindBy (xpath = "//android.widget.ScrollView[@content-desc=\"test-PRODUCTS\"]/preceding-sibling::android.view.ViewGroup/android.widget.TextView") 
	@iOSXCUITFindBy (xpath ="//XCUIElementTypeOther[@name=\"test-Toggle\"]/parent::*[1]/preceding-sibling::*[1]")
	private MobileElement productTitleTxt;
	
	@AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]") 
	@iOSXCUITFindBy (xpath = "(//XCUIElementTypeStaticText[@name=\"test-Item title\"])[1]")
	private MobileElement SLBTitle;
	
	@AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]") 
	@iOSXCUITFindBy (xpath = "(//XCUIElementTypeStaticText[@name=\"test-Price\"])[1]")
	private MobileElement SLBPrice;
	
public String getTitle() {
	String title = getText(productTitleTxt, "product page title is - ");
	return title;
}

public String getSLBTitle() {
	String title = getText(SLBTitle, "title is - ");
	return title;
}

public String getSLBPrice() {
	String price = getText(SLBPrice, "price is - ");
	return price;
}

public ProductDetailsPage pressSLBTitle() {
	click(SLBTitle, "press SLB tile link");
	return new ProductDetailsPage();
}

}
