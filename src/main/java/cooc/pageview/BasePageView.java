package cooc.pageview;


import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;

import cooc.common.utils.ApiCheckTools;
import cooc.conf.ServerBaseInfo;
import cooc.report.Report;

import java.util.regex.Pattern;

public abstract class BasePageView implements PageView {

    public static final String BODY_ID = "body";

    public final  Page page;
    public final String pagePath;

    public String pageCheckSelector;

    public Locator pageBody;

    public String[] listenerApis;

    public final String baseWebUrl;
    public final String baseApiUrl;
    public final String baseUrl;

    private final ServerBaseInfo serverBaseInfo;

    public ExtentTest extentTest;

    public Report report;




    public BasePageView(Page page,ServerBaseInfo serverBaseInfo, String pagePath) {
        this.serverBaseInfo = serverBaseInfo;
        this.baseWebUrl = serverBaseInfo.getBaseWebUrl();
        this.baseApiUrl = serverBaseInfo.getBaseApiUrl();
        this.baseUrl = serverBaseInfo.getBaseUrl();
        this.page = page;
        this.pagePath = pagePath;
        this.pageBody = page.locator(BODY_ID);
        init();
    }

    public void setPageCheckSelector(String pageCheckSelector) {
        this.pageCheckSelector = pageCheckSelector;
    }

    public void setListenerApis(String[] listenerApis) {
        this.listenerApis = listenerApis;
    }

    protected void init(){
        this.page.setDefaultTimeout(30000);
        this.page.setViewportSize(1920,1080);

    }




    @Override
    public boolean openPage() {
        final String pageWebUrl = this.baseWebUrl + this.pagePath;
        System.out.println("打开页面:" + pageWebUrl);
        this.page.navigate(pageWebUrl);
        if(this.pageCheckSelector!=null){
           return page.waitForSelector(this.pageCheckSelector).isVisible();
        }
        return defListenerApiCheck(this.listenerApis);
    }

    @Override
    public Response reloadPage() {
        return this.page.reload();
    }

    @Override
    public void closePage() {
        this.page.close();
    }

    @Override
    public Page getCurrentPage() {
        return this.page;
    }

    @Override
    public boolean defListenerApiCheck(String[] apisPath) {
        //监听打开页面默认调用接口
        return checkApis(apisPath);
    }

    @Override
    public String getViewTitle() {
        return page.title();
    }

    public String getPagePath() {
        return pagePath;
    }

    public Page getPage() {
        return page;
    }

    public Locator getIframeBody(String iframeSrc) {
        Locator locator = this.page.frameByUrl(getApiPatter(iframeSrc)).locator(BODY_ID);
        return locator;
    }

    @Override
    public Locator getPageBody() {
        return this.pageBody;
    }

    @Override
    public Pattern getApiPatter(String api) {
        return Pattern.compile(this.baseApiUrl + api + ".*");
    }

    private Response apiListener(String apiPath){
        return page.waitForResponse(getApiPatter(apiPath), this::openPage);
    }



    private boolean checkApi(Response apiRes){
        return ApiCheckTools.apiResponseCheck(apiRes);
    }

    private boolean checkApis(String[] listenerApis){
        if(listenerApis!=null){
            for (String var :listenerApis
            ) {
                final Response res = apiListener(var);
                boolean result = checkApi(res);
                if(!result){
                    System.out.println("接口【" +var+ "】检查失败:" + ApiCheckTools.transformResponseBody(res));
                    return false;
                }
            }
        }
        return true;
    }
}
