package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

public class Label extends AbstractUnit{

    private static final String LABEL_CONTENT_CSS = "div.el-form-item:has(>label.el-form-item__label)";


    public Label(Locator unitRange, PageView pageView) {
        super(unitRange,LABEL_CONTENT_CSS, pageView);
    }


    public void fillLabelByName(String labelName, String value) throws Exception {
        Input input = getInputByLabelName(labelName);
        input.fillByPlaceholder("请输入" + labelName,value);
        Thread.sleep(1000);
    }

    public void fillSingleInputLabelByName(String labelName, String value){
        Input input = getInputByLabelName(labelName);
        Locator var = input.getByIndex(0);
        if(var!=null){
            var.scrollIntoViewIfNeeded();
            var.fill(value);
        }
    }

    public void fillDropDownLabelByName(String labelName,String optName) throws InterruptedException {
        expendLabel(labelName);
        SelectDropDown selectDropDown = new SelectDropDown(pageView.getPageBody(),pageView);
        selectDropDown.selectOpt(optName);
        Thread.sleep(1000);
    }

    public void fillDropDownsLabelByName(String labelName,String[] optsName) throws InterruptedException {
        expendLabel(labelName);
        SelectDropDown selectDropDown = new SelectDropDown(pageView.getPageBody(),pageView);
        selectDropDown.selectOpts(optsName);
        Thread.sleep(1000);
    }

    private void expendLabel(String labelName) throws InterruptedException {
        Locator label = this.getUnitByText(labelName);
        System.out.println("找到的label:" + label.textContent() );
        Input input = new Input(label,pageView);
        InputSuffix inputSuffix = new InputSuffix(input.unitRange,pageView);
        inputSuffix.expandSuffix(true);
        Thread.sleep(1000);
    }

    public Input getInputByLabelName(String labelName){
        Locator label = this.getUnitByText(labelName);
        return new Input(label,pageView);
    }


}
