package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

public class CheckBox extends AbstractUnit{
    private static final String CHECK_BOX = "div:has(>div.el-switch input[type=\"checkbox\"]),label.el-checkbox:has( input[type=\"checkbox\"])";
    private static final String CHECK_BOX_INPUT = "input[type=\"checkbox\"]";

    public CheckBox(Locator unitRange, PageView pageView) {
        super(unitRange, CHECK_BOX, pageView);
    }

    public Locator getCheckBoxInputByName(String name){
       Locator checkbox = getUnitByText(name);
       if(checkbox!=null && checkbox.isVisible()){
           return checkbox.locator(CHECK_BOX_INPUT);
       }
       return null;
    }

    public Locator getCheckBoxByName(String name){
        return getUnitByText(name);
    }

    public void setCheckBox(String flagName,boolean flag)  {
        final Locator checkbox = getCheckBoxInputByName(flagName);
        if(checkbox!=null){
           checkbox.scrollIntoViewIfNeeded();
            if(flag){
                checkbox.check();
            }else {
                checkbox.uncheck();
            }
        }
    }

    public void setCheckBoxLabel(String label,boolean flag){
        final Locator checkbox = getCheckBoxByName(label);
        if(checkbox!=null){
            setStatus(checkbox,flag);
        }
    }

    public void setCheckBoxStatus(Locator checkBox,boolean flag){
        setStatus(checkBox,flag);
    }


    public void setCheckBoxSwitch(String flagName,boolean flag){
        final Locator checkbox = getCheckBoxByName(flagName);
        if(checkbox!=null){
            Locator var = checkbox.locator("div.el-switch");
            setStatus(var,flag);
        }
    }

    private void setStatus(Locator checkBox,boolean flag){
        String check = checkBox.getAttribute("class");
        checkBox.scrollIntoViewIfNeeded();
        if(flag && !check.contains("checked")){
            checkBox.click();
        }else if(!flag && check.contains("checked")){
            checkBox.click();
        }
    }


}
