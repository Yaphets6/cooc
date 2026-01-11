package cooc.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;

public interface Report {

    ExtentReports getReport();

    void addScreenshot(Page page, ExtentTest test,String pref);

}
