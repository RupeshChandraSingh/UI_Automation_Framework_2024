package com.inter.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.inter.base.BaseTest;
import com.inter.constants.AppConstants;
import com.inter.pages.SearchPage;

public class AccountPageTest extends BaseTest {

	@BeforeClass
	public void accPageSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}

	@Test
	public void accountPageTitleTest() {
		String accountsPageTitle = accountsPage.getAccPageTitle();

		Assert.assertEquals(accountsPageTitle, "My Account");
	}

	@Test
	public void loginPageUrlTest() {
		String accountPageUrl = accountsPage.getAccPageURL();

		Assert.assertTrue(accountPageUrl.contains("route=account/account"));
	}

	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accountsPage.isLogoutLinkExist());
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String> accountsPageHeaders = accountsPage.getAccountsPageHeadersList();
		for(String s: accountsPageHeaders) {
			System.out.println(s);
		}
		Assert.assertEquals(accountsPageHeaders.size(),AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}
	
	@Test
	public void accPageHeadersValueTest() {
		List<String> accountsPageHeaders = accountsPage.getAccountsPageHeadersList();
		
		Assert.assertEquals(accountsPageHeaders,AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
	}
	
	@DataProvider
	public Object[][] getDataForSearch() {
	
		return new Object[][] {
			{"MacBook"},
			{"iMac"},
			{"Apple"},
			{"Samsung"}
		};
	}
	
	@Test(dataProvider = "getDataForSearch" )
	public void searchProductCountTest(String searchKey) {
		searchPage = accountsPage.performSearch("Macbook");
		Assert.assertTrue(searchPage.getSearchProductsCount()>0);
	}
	
	
	@DataProvider
	public Object[][] getProductTestData() {
	
		return new Object[][] {
			{"MacBook Pro","MacBook Pro"},
			{"iMac","iMac"},
			{"Apple","Apple Cinema 30\""},
			{"Samsung","Samsung Galaxy Tab 10.1"}
		};
	}
		
	@Test(dataProvider = "getProductTestData" )
	public void searchProductTest(String searchKey, String productName) {
		searchPage = accountsPage.performSearch(searchKey);
		if (searchPage.getSearchProductsCount() > 0) {
			productInfoPage = searchPage.selectProduct(searchKey);
			String actProductHeader = productInfoPage.getProductHeaderValue();
			Assert.assertEquals(actProductHeader, productName);
		}
	}
	
	
}
