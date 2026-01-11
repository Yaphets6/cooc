package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

public class Select extends AbstractUnit{

    private static final String SELECT_VALUE_CSS = "li:has(option)";
    public Select(Locator unitRange, PageView pageView) {
        super(unitRange, SELECT_VALUE_CSS, pageView);
    }

    public void selectOption(String name,String option){
        Locator select = getUnitByText(name);
        if(select!=null){
           select.scrollIntoViewIfNeeded();
           Locator s = select.locator("select");
           s.selectOption(option);
        }
    }
}
