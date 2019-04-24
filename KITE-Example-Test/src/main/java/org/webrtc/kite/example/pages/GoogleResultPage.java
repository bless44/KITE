package org.webrtc.kite.example.pages;

import io.cosmosoftware.kite.exception.KiteInteractionException;
import io.cosmosoftware.kite.pages.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class GoogleResultPage extends BasePage {
  
  @FindBy(className="LC20lb")
  WebElement result;
  
  public GoogleResultPage(WebDriver webDriver, Logger logger) {
    super(webDriver, logger);
  }
  
  public void openFirstResult() throws KiteInteractionException {
    waitUntilVisibilityOf(result, 10);
    result.click();
  }
  
  public String getTitle() {
    return webDriver.getTitle();
  }
}
