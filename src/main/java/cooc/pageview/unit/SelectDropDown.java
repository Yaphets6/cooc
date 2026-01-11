package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

import java.util.List;

public class SelectDropDown extends AbstractUnit{

    private static final String SELECT_DROP_DOWN_CSS = "div.el-select__popper:has( li.el-select-dropdown__item):visible";
    private static final String SELECT_DROP_DOWN_ITEM_CSS = "li.el-select-dropdown__item";

    public SelectDropDown(Locator unitRange, PageView pageView) {
        super(unitRange, SELECT_DROP_DOWN_CSS, pageView);
    }

    public void selectOpt(String opt) throws InterruptedException {
        Locator select = getVisible(opt);
        if(select!=null){
            System.out.println("返回的下拉框:" + select.innerText());
            Item var = new Item(select,this.pageView);
            Locator item = var.getUnitByText(opt);
            if(item!=null){
                item.scrollIntoViewIfNeeded();
                item.click();
            }
        }
    }

    public void selectOpts(String[] opts) throws InterruptedException {
        for (String item:opts
             ) {
            selectOpt(item);
        }
    }

    public List<Locator> getAllItems() throws InterruptedException {
        final Locator locator = getOnlyVisible();
        return new Item(locator,this.pageView).all();
    }

    private Locator getVisible(String opt) throws InterruptedException {
        return getOnlyVisible(opt);
    }

    private Locator getOnlyVisible(String... itemValue) throws InterruptedException {
        Locator result = null;
        final int count = this.count();
        System.out.println("可见的下拉框个数:" + count);
        for (int i = 0; i < count; i++) {
            Locator select =  this.getByIndex(i);
            if(select!=null && itemValue!=null && itemValue.length > 0){
                String att = select.getAttribute("aria-hidden");
                if(att.equals("false") && select.innerText().contains(itemValue[0])){
                    result = select;
                }else {
                    return findByFlag(itemValue[0],select);
                }
            }else if(count==1){
                result = select;
            }
        }
        return result;
    }

    private Locator findByFlag(String flag,Locator opt) throws InterruptedException {
        Locator item =  getUnitByText(flag);
        int times = 0;
        if(item!=null){
            return opt;
        }else if(opt!=null) {
            while (times<=300){
                opt.hover();
                this.pageView.getCurrentPage().mouse().wheel(0,100);
                Thread.sleep(30);
                item = getUnitByText(flag);
                times++;
                System.out.println("滑动查找下拉选项:" + times);
                if(item!=null){
                    //找到后再向下滑动60，防止选项不可见
                    System.out.println("找到元素");
                    this.pageView.getCurrentPage().mouse().wheel(0,60);
                    return opt;
                }
            }
        }
        return null;
    }

    private static class Item extends AbstractUnit{
        public Item(Locator unitRange,PageView pageView) {
            super(unitRange, SELECT_DROP_DOWN_ITEM_CSS, pageView);
        }
    }

}
