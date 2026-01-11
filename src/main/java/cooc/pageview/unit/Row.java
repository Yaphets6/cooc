package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

public class Row extends AbstractUnit{

    private static final String ROW_CSS = "div.el-row";


    public Row(Locator unitRange, PageView pageView) {
        super(unitRange, ROW_CSS, pageView);
    }
}
