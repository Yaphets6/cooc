package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

public class Button extends AbstractUnit {

    private static final String BTN_TEXT_CSS = "button";
    public static final String A_BTN_TEXT_CSS = "a.btn:has(i)";


    public Button(Locator locator, PageView pageView) {
        super(locator,BTN_TEXT_CSS, pageView);
    }
    public Button(Locator locator,String unitSelect, PageView pageView) {
        super(locator,unitSelect, pageView);
    }

    public void clickBtnByName(String btnName){
        final Locator locator = getUnitByText(btnName);
        if(locator!=null){
            locator.scrollIntoViewIfNeeded();
            locator.click();
        }
    }

}