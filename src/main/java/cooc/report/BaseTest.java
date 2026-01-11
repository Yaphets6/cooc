package cooc.report;


import com.aventstack.extentreports.Status;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Cookie;
import com.microsoft.playwright.options.RequestOptions;
import cooc.common.explorer.ExplorerFactory;
import cooc.common.utils.ExtentReportTools;
import cooc.common.utils.FileUtils;
import cooc.common.utils.TestNgTools;
import cooc.common.utils.TimeFormatType;
import cooc.conf.*;
import cooc.pageview.apis.RequestOptionsFactory;
import cooc.pageview.apis.RequestType;
import cooc.pageview.pages.LoginViewPage;
import org.testng.ITestResult;
import org.testng.annotations.*;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class BaseTest  extends TestReport {

    protected  ExplorerFactory.BrowserContextObj BROWSER_WEB ;
    protected  BrowserContext BROWSER_CONTEXT_WEB;
    protected Page FIRST_PAGE_WEB;
    protected LoginUserInfo LOGIN_USER_INFO_WEB;

    public APIRequestContext API_REQUEST_CONTEXT_WEB;

    public String cookies;
    protected boolean setUpFlag = false;

    protected WebServerConf demoWeb = new WebServerConf(Args.SERVER_HOST.getValue());
    private final Path workDataDir = Paths.get(Args.WORK_TEMP.getValue() + FileUtils.getPathSplit() + new SimpleDateFormat(TimeFormatType.YM).format(new Date()));
    protected PlayWrightDefLaunchContextOption ovpLaunch = PlayWrightDefLaunchContextOption.builder(demoWeb,workDataDir);


    protected ExplorerFactory.InitExplorerOption initExplorerOption = new ExplorerFactory.InitExplorerOption(ExplorerFactory.ExplorerType.CHROME,ovpLaunch);




    @BeforeSuite
    public void initBrowser() throws Exception {
        try {
            BROWSER_WEB = ExplorerFactory.getExplorer(initExplorerOption);
            BROWSER_CONTEXT_WEB = BROWSER_WEB.getBrowserContext();
            FIRST_PAGE_WEB = BROWSER_CONTEXT_WEB.pages().get(0);
            setInfo();
            setUpFlag = true;
        }catch (Exception e){
            System.out.println(SUIT_ERROR + e);
            extentBeforeSuit.log(Status.FAIL,e);
            setUpFlag = false;
            ExplorerFactory.close(BROWSER_WEB);
            report.flush();
        }

    }


    private void setInfo() throws Exception {
        LoginUserInfo var = getClass().getAnnotation(LoginUserInfo.class);
        if(var!=null){
            this.LOGIN_USER_INFO_WEB = var;
        }else {
            throw new Exception("获取登录账号信息失败");
        }
    }

    private void setCookies(){
        StringBuilder cookies = new StringBuilder();
        List<Cookie> all = FIRST_PAGE_WEB.context().cookies();
        all.forEach(cookie -> cookies.append(cookie.name).append("=").append(cookie.value).append(";"));
        this.cookies = cookies.toString();
    }


    @AfterSuite
    public void closeSuit(){
        ExplorerFactory.close(BROWSER_WEB);
    }


    @BeforeClass
    public void LoginUser(){
        if(!setUpFlag){
            extentBeforeTest.log(Status.SKIP,SUIT_ERROR);
            extentBeforeClass.log(Status.SKIP,SUIT_ERROR);
        }else {
            System.out.println(this.getClass().getCanonicalName() + " 执行开始");
            LoginViewPage loginViewPage = new LoginViewPage(FIRST_PAGE_WEB, demoWeb);
            try {
                loginViewPage.openPage();
                addScreenshot(loginViewPage.page, extentBeforeClass, "登录前截图");
                loginViewPage.loginUser(LOGIN_USER_INFO_WEB.userName(), LOGIN_USER_INFO_WEB.passWord());
                API_REQUEST_CONTEXT_WEB = ExplorerFactory.getRequestObj(null, BROWSER_WEB);
                assert loginViewPage.page.url().contains("index");
                Thread.sleep(3000);
                addScreenshot(loginViewPage.page, extentBeforeClass, "登录成功截图");
                System.out.println("登录成功");
                setCookies();
                setUpFlag = true;
            } catch (Exception e) {
                System.out.println(CLASS_ERROR);
                extentBeforeTest.log(Status.FAIL, e);
                extentBeforeClass.log(Status.FAIL, e);
                addScreenshot(loginViewPage.page, extentBeforeClass, "登录异常截图");
                setUpFlag = false;
                ExplorerFactory.close(BROWSER_WEB);
                report.flush();
            }
        }
    }


//    @AfterClass
//    public void AfterClass(){
//        if(!setUpFlag){
//            ExplorerFactory.close(BROWSER_OVP);
//        }
//    }

    @BeforeMethod
    public void BeforeTestPng(Method method){
        if(!setUpFlag){
            extentMethod.log(Status.SKIP,CLASS_ERROR);
        }else {
            Page activePage = ExplorerFactory.getActivePage(BROWSER_CONTEXT_WEB);
            final String bef_screen = TestNgTools.getTestDesc(method) + "_Before";
            String path = screenshot(activePage, bef_screen);
            extentMethod.createNode(bef_screen).info(bef_screen, ExtentReportTools.getScreenshot(path));
        }
    }

    @AfterMethod
    public void afterMethod(ITestResult result){
        if(!setUpFlag){
            extentMethod.log(Status.SKIP,CLASS_ERROR);
        }
        else if(result.getStatus() == ITestResult.FAILURE){
            System.out.println(buildResultError(result,16));
            extentMethod.log(Status.FAIL,result.getThrowable());
            Page activePage = ExplorerFactory.getActivePage(BROWSER_CONTEXT_WEB);
            String des = result.getMethod().getDescription() + "_测试失败截图_" + Status.FAIL;
            addScreenshot(activePage,extentMethod,des);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentMethod.log(Status.PASS,"Test Pass");
        }else {
            extentMethod.log(Status.SKIP," Test Skip");
        }
    }


    protected String buildResultError(ITestResult result,int length){
        StringBuilder error = new StringBuilder().append(result.getThrowable().toString());
        StackTraceElement[] vars = result.getThrowable().getStackTrace();
        for (int i = 0; i < length; i++) {
            error.append("\n\t").append(vars[i]);
        }
        error.append("\n\t").append("... Removed ").append(vars.length - length).append(" stack frames");
        return String.valueOf(error);
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface LoginUserInfo{
        String userName();
        String passWord();
    }






}
