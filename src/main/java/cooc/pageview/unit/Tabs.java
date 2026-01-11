package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

public class Tabs extends AbstractUnit{

    private static final String TAB_CSS = "div.el-tabs__item";

    public Tabs(Locator locator, PageView pageView) {
        super(locator,TAB_CSS, pageView);
    }

    public void selectTabsByName(String tabName){
        final Locator tab =  this.getUnitByText(tabName);
        if(tab!=null && !tab.getAttribute("class").contains("is-active")){
            final String att = tab.getAttribute("class");
            if(!att.contains("is-active")){
                tab.click();
            }
            tab.click();
        }
    }

}
