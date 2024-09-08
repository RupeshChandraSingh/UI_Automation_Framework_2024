package com.inter.pages;

import org.openqa.selenium.WebDriver;

import com.inter.utils.ElementUtil;

public class ViewCartPopUpPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	public ViewCartPopUpPage(WebDriver driver) {

		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

}
