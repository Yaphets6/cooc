package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

public class Dialog extends AbstractUnit{
    private static final String DIALOG_CSS = "div[role=\"dialog\"]";
    private static final String DIALOG_HEADER_CSS = "[class*=\"header\"]";
    private static final String DIALOG_BODY_CSS = "[class*=\"content\"],[class*=\"body\"]";
    private static final String DIALOG_FOOTER_CSS = "[class*=\"footer\"],[class*=\"btn\"]";

    public Dialog(Locator unitRange, PageView pageView) {
        super(unitRange, DIALOG_CSS, pageView);
    }

    public void closeDialog(String title){
        getUnitByText(title).locator(DIALOG_HEADER_CSS).locator("button").click();
    }

    public void clickFooterBtn(String title,String btn){
        Button button = new Button(getUnitByText(title).locator(DIALOG_FOOTER_CSS),this.pageView);
        button.clickBtnByName(btn);
    }



}
