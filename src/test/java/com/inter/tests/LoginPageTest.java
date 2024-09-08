package com.inter.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.inter.base.BaseTest;
import com.inter.constants.AppConstants;

public class LoginPageTest extends BaseTest{
	
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String loginPageTitle = loginPage.getLoginPageTitle();
		
		Assert.assertEquals(loginPageTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}
	

	@Test(priority = 3)
	public void loginPageUrlTest() {
		String loginPageUrl = loginPage.getLoginPageUrl();
		
		Assert.assertTrue(loginPageUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}
	
	
	@Test(priority = 2)
	public void forgotPwdLinkTest() {
		
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Test(priority = 4)
	public void loginTest(){
		accountsPage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertTrue(accountsPage.isLogoutLinkExist());
	}
	
}
