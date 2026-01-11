package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public abstract class AbstractUnit {

    final Locator unitRange;
    final String unitSelector;

    final PageView pageView;



    public AbstractUnit(Locator unitRange, String unitSelector, PageView pageView) {
        this.unitRange = unitRange;
        this.unitSelector = unitSelector;
        this.pageView = pageView;
    }

    public String getUnitSelector() {
        return unitSelector;
    }

    public Locator getUnitRange() {
        return unitRange;
    }


    public List<Locator> all() {
        return unitRange.locator(unitSelector).all();
    }


    public Locator getByIndex(int index) {
        return all().get(index);
    }


    public int count() {
        return this.unitRange.locator(unitSelector).count();
    }

    public Locator getUnitByAttribute(String attributeName, String attributeValue){
        List<Locator> all = this.unitRange.locator(unitSelector).all();
        return filterByAttribute(all,attributeName,attributeValue);
    }

    private Locator filterByAttribute(List<Locator> src,String attributeName,String attributeValue){
        if(src!=null && src.size() > 0){
            if(src.size() == 1){
                return src.get(0);
            }
            for (int i = 0; i < src.size(); i++) {
                final Locator var = src.get(i);
                final String attribute = var.getAttribute(attributeName);
                if(attribute!=null && attribute.equals(attributeValue)){
                    var.scrollIntoViewIfNeeded();
                    return var;
                }
            }
            throw new RuntimeException("未找到属性为【" + attributeName + "】值为【" + attributeValue + "】的元素");
        }
        return null ;
    }

    public  Locator getUnitByText(String unitName){
        Locator.FilterOptions options = new Locator.FilterOptions();
        options.setHasText(Pattern.compile(String.format(".*%s.*",unitName)));
        List<Locator> all = this.unitRange.locator(unitSelector).filter(options).all();
//        System.out.println( "元素数量" + s.count());
        return this.filterByText(all,unitName);
    }


    public Locator filterByText(List<Locator> src,String text){
        if(src!=null && src.size() > 0){
            if(src.size() == 1){
                return src.get(0);
            }
            for (int i = 0; i < src.size(); i++) {
                final Locator var = src.get(i);
                final String[] strings = var.textContent().split(" ");
                if(Arrays.stream(strings).anyMatch(item -> item.equals(text))){
                    var.scrollIntoViewIfNeeded();
                    return var;
                }
            }
            throw new RuntimeException("未找到text为【" + text + "】的元素");
        }
        return null;
    }


    public boolean checkInnerText(Locator locator,String checkText){
        if(locator!=null){
            return checkText(locator,checkText);
        }
        return false;
    }

    private boolean checkText(Locator locator,String checkText){
        List<String> texts = locator.allInnerTexts();
        if(!texts.isEmpty()){
            return texts.stream().anyMatch(var->var.equals(checkText));
        }
        return false;
    }



}
