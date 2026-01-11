package cooc.pageview;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;

import java.util.regex.Pattern;

public interface PageView {
    boolean openPage();
    Response reloadPage();
    void closePage();

    Page getCurrentPage();

    boolean defListenerApiCheck(String[] apiPath);

    String getViewTitle();

    Locator getPageBody();

    Pattern getApiPatter(String api);

}
