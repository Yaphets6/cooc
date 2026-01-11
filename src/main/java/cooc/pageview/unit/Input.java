package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

import java.util.List;

public class Input extends AbstractUnit {

    private static final String INPUT_CONTENT_CSS = "div.col:has( input)";
    public static final String INPUT_LI_CSS = "li:has(input):visible";

    public Input(Locator locator, PageView pageView) {
        super(locator,INPUT_CONTENT_CSS, pageView);
    }
    public Input(Locator locator,String unitSelect,PageView pageView) {
        super(locator,unitSelect, pageView);
    }

    public void fillByPlaceholder(String placeholder,String value) throws Exception {
        final Locator input = getInputByPlaceholder(placeholder);
        fill(input,value);
    }

    public void fillByAttribute(String attrName,String attrValue,String value){
        final Locator input = getUnitByAttribute(attrName,attrValue);
        fill(input,value);
    }

    public void fillByLiText(String text,String value) throws Exception {
        final Locator input = getUnitByText(text);
        fill(input,value);
    }

    public Locator getInputByPlaceholder(String placeholder) throws Exception {
        final String selector = String.format("input[placeholder='%s']", placeholder);
        final Locator input = this.unitRange.locator(selector);
        if(input!=null) {
            return input;
        }
        throw new Exception("未找到输入框:" + placeholder);
    }

    private void fill(Locator input,String value){
        if(input!=null){
            String tagName = input.evaluate("element => element.tagName").toString();
            input.scrollIntoViewIfNeeded();
            if(!tagName.equals("INPUT")){
                input = input.locator("input:visible");
            }
            input.clear();
            input.fill(value);
        }
    }



}
