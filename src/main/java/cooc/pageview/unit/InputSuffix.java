package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

public class InputSuffix extends AbstractUnit{

    private static final String SUFFIX_CSS = "span.el-input__suffix,div.el-select__suffix";
    private static final String ICON_CSS = "i.el-select__icon,i.el-input__icon";

    public InputSuffix(Locator locator, PageView pageView) {
        super(locator,SUFFIX_CSS, pageView);
    }

    public void expandSuffix(Boolean status){
        final Locator icon = this.unitRange.locator(ICON_CSS);
        final String att = icon.getAttribute("class");
        if(status && !att.contains("is-reverse")){
            unitRange.click();
        } else if (!status && att.contains("is-reverse")) {
            unitRange.click();
        }
    }
}
