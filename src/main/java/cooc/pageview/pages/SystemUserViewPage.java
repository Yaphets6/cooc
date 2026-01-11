package cooc.pageview.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import cooc.conf.ServerBaseInfo;
import cooc.conf.ServerBaseInfoImpl;
import cooc.conf.WebServerConf;
import cooc.pageview.BasePageView;
import cooc.pageview.unit.*;

import java.util.regex.Pattern;

public class SystemUserViewPage extends BasePageView {
    private static final String PATH = "system/user";

    private static final String SEARCH_CSS = "div.search-collapse:visible:has(div.select-list)";
    public static final String SEARCH_API = "system/user/list";


    public SystemUserViewPage(Page page, ServerBaseInfo serverBaseInfo) {
        super(page,serverBaseInfo,PATH);
        this.pageBody = getIframeBody(PATH);
    }


    public Response searchUser(String userName, String phone, String status){
        Response result = null;
        try {
            result = listUser(userName,phone,status);
        } catch (Exception e) {
            System.out.println("查询异常"+e);
        }
        return result;
    }

    private Response listUser(String userName,String phone,String status) throws Exception {
        Locator search = pageBody.locator(SEARCH_CSS);
        Input input = new Input(search,Input.INPUT_LI_CSS,this);
        input.fillByLiText("登录名称",userName);
        input.fillByLiText("手机号码",phone);
        Select select = new Select(search,this);
        select.selectOption("用户状态",status);
        Response response = this.page.waitForResponse(getApiPatter(SEARCH_API),()->{
            Button s = new Button(search,Button.A_BTN_TEXT_CSS,this);
            s.clickBtnByName("搜索");
        });
        return response;
    }


}
