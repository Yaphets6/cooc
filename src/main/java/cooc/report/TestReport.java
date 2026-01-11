package cooc.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.Page;
import cooc.common.utils.ExtentReportTools;
import cooc.common.utils.FileUtils;

import cooc.common.utils.TestNgTools;
import cooc.common.utils.TimeFormatType;
import org.testng.ITestContext;
import org.testng.annotations.*;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestReport implements Report {

    public ExtentReports report;

    public String reportRoot;

    public ExtentTest extentMethod;
    public ExtentTest extentBeforeClass;
    public ExtentTest extentBeforeTest;
    public ExtentTest extentBeforeGroups;
    public ExtentTest extentBeforeSuit;
    public static final String SUIT_ERROR = "beforeSuit初始化失败";
    public static final String XMl_TEST_ERROR = "beforeTest初始化失败";
    public static final String CLASS_ERROR = "beforeClass初始化失败";
    public static final String METHOD_ERROR = "beforeMethod初始化失败";

    public String reportIndex;

    @BeforeSuite
    public void initReport(ITestContext context){
        this.reportRoot = createReportRoot("AutoTestDemo");
        this.reportIndex = "reportIndex.html";
        String reportPath = reportRoot + FileUtils.getPathSplit() + reportIndex;
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().enableOfflineMode(true);
        spark.config().setDocumentTitle("auto test demo");
        spark.config().setEncoding("UTF-8");
        spark.config().setJs("document.querySelector('.text-content').style.display = 'flex';");
        spark.config().setJs("document.querySelector('.text-content').style.flexWrap = 'wrap';");
        spark.config().setJs("document.querySelector('.text-content').style.flexDirection = 'row';");
        report = new ExtentReports();
        report.attachReporter(spark);
        report.setSystemInfo("OS",System.getProperty("os.name"));
        report.setSystemInfo("Java Version",System.getProperty("java.version"));
        extentBeforeSuit = report.createTest(context.getSuite().getName() + "_BeforeXmlSuit");
    }

    @BeforeMethod
    public void startMethod(Method method){
        extentMethod = report.createTest(TestNgTools.getTestDesc(method));
    }



    @BeforeTest
    public void startTest(ITestContext context){
        extentBeforeTest = report.createTest(context.getCurrentXmlTest().getName()+ "_BeforeXmlTest" );
    }

    @BeforeClass
    public void startClass(ITestContext context){
        extentBeforeClass = report.createTest(context.getClass().getName()+ "_BeforeClass");
    }


    @BeforeGroups
    public void startGroups(ITestContext context){
        extentBeforeGroups = report.createTest(context.getName() + "_BeforeGroups");
    }


    @AfterSuite
    public void saveReport(){
        report.flush();
    }

    public String createReportRoot(String pref){
        String date = new SimpleDateFormat(TimeFormatType.YM).format(new Date());
        return pref +  date;
    }

    public byte[] screenshot(Page activePage){
        return activePage.screenshot(new Page.ScreenshotOptions().setFullPage(true));
    }

    public String screenshot(Page activePage,String prefName){
        ReportScreenshotPathInfo screen_info = initScreenshotPath(prefName);
        activePage.screenshot(new Page.ScreenshotOptions().setFullPage(true).setPath(screen_info.screenshotPath));
        return screen_info.screenshotReportPath;
    }

    @Override
    public void addScreenshot(Page page,ExtentTest extentTest,String prefName){
        String path = screenshot(page,prefName);
        extentTest.createNode(prefName).info(prefName, ExtentReportTools.getScreenshot(path));
    }

    public void saveScreenshot(byte[] screenshot,ExtentTest extentTest,String prefName) {
        final ReportScreenshotPathInfo screenshot_info = initScreenshotPath(prefName);
        try {
            Files.write(screenshot_info.screenshotPath,screenshot);
            extentTest.createNode(prefName).info(prefName,ExtentReportTools.getScreenshot(screenshot_info.screenshotReportPath));
        }catch (IOException ioException){
            extentTest.info("截图保存失败:" + screenshot_info.screenshotReportPath);
        }
    }

    public ReportScreenshotPathInfo initScreenshotPath(String prefName){
        long png_date = new Date().getTime();
        //只能返回到截图这一层，因为生成的报告html和截图是同级了
        String screenshot = "TestScreenshot/"  + prefName + png_date + ".png";
        //生成的截图文件路径需要包含执行报告的根目录，区分不同执行时间
        String path = reportRoot + "/" +  screenshot;
        File screen_png =new File(path);
        return new ReportScreenshotPathInfo(screen_png.toPath(), screenshot);
    }


    @Override
    public ExtentReports getReport() {
        return report;
    }

    public static class ReportScreenshotPathInfo{
        private final Path screenshotPath;
        private final String screenshotReportPath;

        public ReportScreenshotPathInfo(Path screenshotPath, String screenshotReportPath) {
            this.screenshotPath = screenshotPath;
            this.screenshotReportPath = screenshotReportPath;
        }

        public Path getScreenshotPath() {
            return screenshotPath;
        }

        public String getScreenshotReportPath() {
            return screenshotReportPath;
        }
    }

}
