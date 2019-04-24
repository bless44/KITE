package org.webrtc.kite.wpt.steps;

import io.cosmosoftware.kite.exception.KiteTestException;
import io.cosmosoftware.kite.report.Reporter;
import io.cosmosoftware.kite.report.Status;
import io.cosmosoftware.kite.steps.TestStep;
import org.openqa.selenium.WebDriver;
import org.webrtc.kite.wpt.Result;
import org.webrtc.kite.wpt.pages.WPTTestPage;

import java.util.Arrays;
import java.util.List;

public class RunOneTestStep extends TestStep {
  private final String url;
  private final WPTTestPage wptTestPage;
  
  
  public RunOneTestStep(WebDriver webDriver, String url) {
    super(webDriver);
    this.url = url;
    wptTestPage = new WPTTestPage(webDriver, logger, url);
  }
  
  @Override
  public String stepDescription() {
    List<String> brokenDownUrl = Arrays.asList(this.url.split("/"));
    return "Run and get result: " + brokenDownUrl.get(brokenDownUrl.size() -1);
  }
  
  @Override
  public void step() throws KiteTestException {
    this.report.addParam("test-url", url);
    wptTestPage.runTest();
    wptTestPage.fillResultReport();
    Result result = wptTestPage.getResultReport();
    if (result.isBroken()) {
      throw new KiteTestException("No sub tests was found, possibly because connection problem", Status.BROKEN);
    }
    Reporter.getInstance().textAttachment(this.report, "Test result",result.toString(), "json");
    if (result.failed()) {
      this.report.setStatus(Status.FAILED);
      this.report.setIgnore(true);
    }
  }
  
  public Result getTestResult() {
    return this.wptTestPage.getResultReport();
  }
}
