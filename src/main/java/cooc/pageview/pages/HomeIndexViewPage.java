package cooc.pageview.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import cooc.conf.ServerBaseInfo;
import cooc.pageview.BasePageView;

public class HomeIndexViewPage extends BasePageView {

    public static final String PATH = "index";

    private static final String NAV_MENU_CSS = "nav.navbar-default";

    public HomeIndexViewPage(Page page,ServerBaseInfo serverBaseInfo) {
        super(page,serverBaseInfo,PATH);
    }


}
