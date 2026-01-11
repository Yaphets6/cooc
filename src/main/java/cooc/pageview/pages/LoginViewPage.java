package cooc.pageview.pages;

import com.alibaba.fastjson2.JSONObject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Request;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.Cookie;
import cooc.common.utils.ApiCheckTools;
import cooc.common.utils.CodeImage;
import cooc.conf.ServerBaseInfo;
import cooc.pageview.BasePageView;
import cooc.pageview.apis.RequestContextOptionsFactory;
import cooc.pageview.unit.Button;
import cooc.pageview.unit.Input;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class LoginViewPage extends BasePageView {
    private static final String PATH = "login";
    private static final String LOGIN_CONTENT_CSS = "form#signupForm";
    private static final String CODE_REF_CSS = "img.imgcode";

    private Map<String,String> authToken;
    private static final String USERNAME_PLACEHOLDER = "用户名";
    private static final String PASSWORD_PLACEHOLDER = "密码";
    private static final String CODE_PLACEHOLDER = "验证码";
    private static final String LOGIN_BTN_TEXT = "登录";

    private static final String accountLogin = "login";
    private static final String accountLogOut = "logout";
    private static final String codeRef = "captcha/captchaImage";

    public LoginViewPage(Page page,ServerBaseInfo serverBaseInfo) {
        super(page,serverBaseInfo,PATH);
        this.pageCheckSelector = LOGIN_CONTENT_CSS;
    }

    public void loginUser(String userName, String passWord) throws Exception {
//        fillCode();
        final Locator loginContent = pageBody.locator(LOGIN_CONTENT_CSS);
        Input input = new Input(loginContent,this);
        Button button = new Button(loginContent,this);
        input.fillByPlaceholder(USERNAME_PLACEHOLDER,userName);
//        input.fillByPlaceholder(PASSWORD_PLACEHOLDER,passWord);  //不知道密码
        Thread.sleep(5000);//暂停5杪手动填写验证码，腾讯ocr识别无法调试
        Response res = this.page.waitForResponse(getApiPatter(HomeIndexViewPage.PATH),()-> button.clickBtnByName(LOGIN_BTN_TEXT));
        System.out.println("登录后首页接口:" + new Sring(res.body()));
    }

    public void LogOut(){
        this.page.evaluate("() => localStorage.clear()");
    }


    private void fillCode(){
        final Locator codeRefImg = pageBody.locator(CODE_REF_CSS);
        final String url =  this.baseApiUrl + codeRef + ".*";
        Response response =  this.page.waitForResponse(Pattern.compile(url), codeRefImg::click);
        String image = Base64.getEncoder().encodeToString(response.body());
        String code = CodeImage.preprocessCaptcha(image);
        System.out.println("验证码:" + code);
    }


    private void setLoginAuthToken(Response loginResponse){
        JSONObject res = ApiCheckTools.transformResponseBody(loginResponse);
        Map<String,String> var = new HashMap<>();
        final String token = res.getJSONObject("data").getString(RequestContextOptionsFactory.AUTH_TOKEN);
        final String ref = res.getJSONObject("data").getString(RequestContextOptionsFactory.REFRESH_TOKEN);
        var.put(RequestContextOptionsFactory.AUTH_TOKEN,token);
        var.put(RequestContextOptionsFactory.REFRESH_TOKEN,ref);
        this.authToken = var;
    }

    public Map<String, String> getAuthToken() {
        return authToken;
    }
}
