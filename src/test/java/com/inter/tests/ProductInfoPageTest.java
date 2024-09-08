package com.inter.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.inter.base.BaseTest;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void accPageSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}

	@DataProvider
	public Object[][] getProductTestData() {

		return new Object[][] { { "MacBook Pro", "MacBook Pro", 4 }, { "iMac", "iMac", 3 },
				{ "Apple", "Apple Cinema 30\"", 6 }, { "Samsung", "Samsung Galaxy Tab 10.1", 7 } };
	}

	@Test(dataProvider = "getProductTestData")
	public void productImageCountTest(String searchKey, String productName, int count) {

		searchPage = accountsPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		int actualImageCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(false, null);

		Assert.assertEquals(actualImageCount, count);

	}

	@Test
	public void productInfoTest() {
		searchPage = accountsPage.performSearch("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		Map<String, String> proDuctInfo = productInfoPage.getProductInfo();
		softAssert.assertEquals(proDuctInfo.get("Brand"), "Apple");
		softAssert.assertEquals(proDuctInfo.get("Product Code"), "Product 18");
		Assert.assertEquals(proDuctInfo.get("productprice"), "$2,000.00");
		softAssert.assertAll();
	}

	
	@DataProvider
	public Object[][] getCartTestData() {
		return new Object[][] {
			{"Macbook", "MacBook Pro", 1},
			{"iMac", "iMac", 2},
		};
	}
	
	@Test(dataProvider = "getCartTestData")
	public void addToCartTest(String searchKey, String productName, int quantity) {
		searchPage = accountsPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		productInfoPage.enterQuantity(quantity);
		String actCartMesg = productInfoPage.addProductToCart();
		Map<String, String> proDuctInfo = productInfoPage.getProductInfo();
		
		softAssert.assertTrue(actCartMesg.indexOf("Success")>=0);
		softAssert.assertTrue(actCartMesg.indexOf(productName)>=0);
		softAssert.assertEquals(actCartMesg, "Success: You have added "+productName+" to your shopping cart!");
	}
}
