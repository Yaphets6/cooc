package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

public class MenuBar extends AbstractUnit{

    private static final String MENU_BAR_CSS = "ul.nav";
    private static final String MENU_ITEM_CSS = "li:has(a)";
    private static final String NAV_MENU_CSS = "nav.navbar-default";

    private Locator menu;


    public MenuBar(PageView pageView){
        this(init(pageView), pageView);
    }

    private MenuBar(Locator unitRange,PageView pageView) {
        super(unitRange, MENU_BAR_CSS, pageView);
        this.menu = unitRange;
    }

    private static Locator init(PageView pageView){
        return pageView.getPageBody().locator(NAV_MENU_CSS);
    }

    public void clickMenuItem(String... menuItems){
        if(menuItems.length>0){
            for (String var:menuItems
                 ) {
                MenuItem menuItem = new MenuItem(this.menu,this.pageView);
                this.menu = menuItem.clickMenuItem(var);
                try {
                    Thread.sleep(100);
                }catch (Exception e){

                }
            }
        }
    }


    public static class MenuItem extends AbstractUnit {

        public MenuItem(Locator unitRange, PageView pageView) {
            super(unitRange, MENU_ITEM_CSS, pageView);
        }

        public Locator clickMenuItem(String name){
            final Locator menu = getUnitByText(name);
            if(menu!=null){
                menu.scrollIntoViewIfNeeded();
                final String active = menu.getAttribute("class");
                if(active == null || !active.contains("active") || !active.contains("selected")){
                    menu.click();
                }
            }
            return menu;//返回当前菜单元素，缩小子菜单范围
        }
    }
}
