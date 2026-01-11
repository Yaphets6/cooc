package cooc.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.Cookie;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;
import cooc.pageview.apis.RequestOptionsFactory;
import cooc.pageview.apis.RequestType;
import cooc.pageview.pages.HomeIndexViewPage;
import cooc.pageview.pages.SystemUserViewPage;
import cooc.pageview.unit.MenuBar;
import cooc.report.BaseTest;
import cooc.pageview.unit.Button;
import cooc.pageview.unit.Label;
import cooc.pageview.unit.Tabs;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@BaseTest.LoginUserInfo(userName = "admin",passWord = "123456")

public class DemoTest extends BaseTest{



    @Test(description = "用户管理查询_UI模式",enabled = true)
    public void testUI() throws Exception {
        System.out.println("开始执行");
        HomeIndexViewPage indexViewPage = new HomeIndexViewPage(FIRST_PAGE_WEB,demoWeb);
        indexViewPage.openPage();
        MenuBar menuBar = new MenuBar(indexViewPage);
        Response list = indexViewPage.page.waitForResponse(indexViewPage.getApiPatter(SystemUserViewPage.SEARCH_API),()->{
            menuBar.clickMenuItem("系统管理","用户管理");
        });
        assert JSON.parseObject(list.body()).get("total").equals(2);
        addScreenshot(indexViewPage.page,extentMethod,"选择菜单后截图");
        Thread.sleep(2000);
        SystemUserViewPage userViewPage = new SystemUserViewPage(FIRST_PAGE_WEB,demoWeb);
        Response res = userViewPage.searchUser("ry","15666666666","正常");
        assert JSON.parseObject(res.body()).get("total").equals(1);
        Thread.sleep(1000);
        addScreenshot(userViewPage.page,extentMethod,"查询后截图");
    }


    @Test(description = "用户管理查询_接口模式",enabled = true)
    public void testApi() throws Exception {
        System.out.println("开始执行");
        RequestOptionsFactory.RequestOptionsBuilder builder = RequestOptionsFactory.getBuilder();
        String data = "{\n" +
                "  \"pageSize\": 10,\n" +
                "  \"pageNum\": 1,\n" +
                "  \"orderByColumn\": \"createTime\",\n" +
                "  \"isAsc\": \"desc\",\n" +
                "  \"deptId\": null,\n" +
                "  \"parentId\": null,\n" +
                "  \"loginName\": \"ry\",\n" +
                "  \"phonenumber\": \"15666666666\",\n" +
                "  \"status\": \"0\",\n" +
                "  \"params[beginTime]\": \"\",\n" +
                "  \"params[endTime]\": \"\"\n" +
                "}";
        RequestOptions options = builder.setData(JSONObject.parseObject(data),RequestType.FORM);
        options.setHeader("Cookie",cookies);
        APIResponse res = API_REQUEST_CONTEXT_WEB.post(SystemUserViewPage.SEARCH_API, options);
        assert  JSON.parseObject(res.body()).get("total").equals(1);
        System.out.println(res.text());
    }

}
